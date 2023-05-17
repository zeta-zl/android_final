package com.zl.testhelper

import android.content.Context
import androidx.room.Room

class StaticObj {
    companion object {
        lateinit var TimeDataBase : TimeDatabase
        lateinit var TimeDataDao : TimeDataDao
        var initTimeDatabaseList : List<SelectTime> = listOf()
        lateinit var personDataBase : PersonDatabase
        lateinit var personDataDao : PersonDataDao
        var initPersonDatabaseList : List<Person> = listOf()
        fun initDatabase(context : Context) {
            TimeDataBase = Room.databaseBuilder(
                context, TimeDatabase::class.java, "TimeData"
            ).allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
            TimeDataDao = TimeDataBase.TimeDataDao()
            personDataBase = Room.databaseBuilder(
                context, PersonDatabase::class.java, "PersonData"
            ).allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
            personDataDao = personDataBase.PersonDataDao()
        }

    }
}