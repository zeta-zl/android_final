package com.zl.timeselect

import android.content.Context
import androidx.room.Room

class StaticObj {
    companion object {
        lateinit var TimeDataBase : TimeDatabase
        lateinit var TimeDataDao : TimeDataDao
        var initDatabase : Boolean = false
        var initDatabaseList : List<SelectTime> = listOf()
        fun initPersonDatabase(context : Context) {
            TimeDataBase = Room.databaseBuilder(
                context, TimeDatabase::class.java, "PersonData"
            ).allowMainThreadQueries()
                .fallbackToDestructiveMigration().build()
            TimeDataDao = TimeDataBase.TimeDataDao()
        }
    }
}