package com.zl.testhelper

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zl.testhelper.*

@Database(
    entities = [SelectTime::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DetailTypeConverters::class)
abstract class TimeDatabase : RoomDatabase() {
    abstract fun TimeDataDao() : TimeDataDao
}

@Database(
    entities = [Person::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DetailTypeConverters::class)
abstract class PersonDatabase : RoomDatabase() {
    abstract fun PersonDataDao() : PersonDataDao
}