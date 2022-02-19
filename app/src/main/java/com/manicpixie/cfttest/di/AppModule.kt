package com.manicpixie.cfttest.di


import android.app.Application
import androidx.room.Room
import com.manicpixie.cfttest.common.Constants
import com.manicpixie.cfttest.data.local.CurrencyDatabase
import com.manicpixie.cfttest.data.remote.CFTApi
import com.manicpixie.cfttest.data.repository.CFTRepositoryImpl
import com.manicpixie.cfttest.domain.repository.CFTRepository
import com.manicpixie.cfttest.domain.use_case.CheckIfDatabaseIsEmptyUseCase
import com.manicpixie.cfttest.domain.use_case.GetCurrenciesUseCase
import com.manicpixie.cfttest.domain.use_case.GetCurrencyByNameUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideGetCurrenciesUseCase(repository: CFTRepository): GetCurrenciesUseCase {
        return GetCurrenciesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCurrencyByNameUseCase(repository: CFTRepository): GetCurrencyByNameUseCase {
        return GetCurrencyByNameUseCase(repository)
    }


    @Provides
    @Singleton
    fun checkIfDatabaseIsEmptyUseCase(repository: CFTRepository): CheckIfDatabaseIsEmptyUseCase {
        return CheckIfDatabaseIsEmptyUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCFTApi(): CFTApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CFTApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCFTRepository(database: CurrencyDatabase, api: CFTApi): CFTRepository {
        return CFTRepositoryImpl(api, database.dao)
    }

    @Provides
    @Singleton
    fun provideCurrencyDatabase(app: Application): CurrencyDatabase {
        return Room.databaseBuilder(
            app, CurrencyDatabase::class.java, "currency_db"
        ).build()
    }

}