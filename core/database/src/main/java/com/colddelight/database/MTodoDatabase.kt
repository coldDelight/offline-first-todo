package com.colddelight.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.colddelight.database.converter.DateConverter
import com.colddelight.database.dao.MandaDao
import com.colddelight.database.dao.TodoDao
import com.colddelight.database.model.MandaEntity
import com.colddelight.database.model.TodoEntity

@Database(
    entities = [TodoEntity::class, MandaEntity::class],
    version = 2
)
@TypeConverters(DateConverter::class)
abstract class MTodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
    abstract fun mandaDao(): MandaDao
}