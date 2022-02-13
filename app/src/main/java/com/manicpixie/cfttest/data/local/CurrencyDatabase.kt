package com.manicpixie.cfttest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.manicpixie.cfttest.data.local.entity.CurrencyEntity


@Database(entities = [CurrencyEntity::class], version = 1)
abstract class CurrencyDatabase : RoomDatabase(){
    abstract val dao: CurrencyDao
}