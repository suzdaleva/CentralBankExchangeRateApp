package com.manicpixie.cfttest.data.local


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manicpixie.cfttest.data.local.entity.CurrencyEntity


@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currencies: List<CurrencyEntity>)

    @Query("DELETE FROM currencyentity")
    suspend fun deleteAll()

    @Query("SELECT * FROM currencyentity WHERE name = :key")
    suspend fun getCurrencyByName(key: String): CurrencyEntity
}


