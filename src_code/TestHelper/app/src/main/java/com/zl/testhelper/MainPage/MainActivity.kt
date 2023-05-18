package com.zl.testhelper.MainPage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.zl.testhelper.NavContent
import com.zl.testhelper.StaticObj
import com.zl.testhelper.registerForRequestPermissionLauncher
import kotlinx.coroutines.*


class MainActivity : ComponentActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        StaticObj.initDatabase(this)
        GlobalScope.launch(Dispatchers.Default) {
            coroutineScope {
                StaticObj.initTimeDatabaseList = StaticObj.TimeDataBase.TimeDataDao().getAll()
            }
        }
        GlobalScope.launch(Dispatchers.Default) {
            coroutineScope {
                StaticObj.initPersonDatabaseList = StaticObj.personDataBase.PersonDataDao().getAll()
            }
        }
        registerForRequestPermissionLauncher(this)
        setContent {
            NavContent()
        }
    }
}


