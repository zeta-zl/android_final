package com.zl.timeselect
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [SelectTime::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DetailTypeConverters::class)
abstract class TimeDatabase : RoomDatabase() {
    abstract fun TimeDataDao() : TimeDataDao
}