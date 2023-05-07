package com.zl.lazydatabase
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zl.lazydatabase.DetailTypeConverters
import com.zl.lazydatabase.Person
import com.zl.lazydatabase.PersonDataDao

@Database(
    entities = [Person::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(DetailTypeConverters::class)
abstract class PersonDatabase : RoomDatabase() {
    abstract fun PersonDataDao() : PersonDataDao
}