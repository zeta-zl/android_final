package com.zl.lazydatabase

import android.content.Context
import androidx.room.Room
import com.zl.lazydatabase.PersonDataDao

class StaticObj {
    companion object {
        lateinit var personDataBase : PersonDatabase
        lateinit var personDataDao : PersonDataDao
        var initDatabase : Boolean = false
        var initDatabaseList : List<Person> = listOf()
        fun initPersonDatabase(context : Context) {
            personDataBase = Room.databaseBuilder(
                context, PersonDatabase::class.java, "PersonData"
            ).allowMainThreadQueries()
                .fallbackToDestructiveMigration().build()
            personDataDao = personDataBase.PersonDataDao()
        }
    }
}