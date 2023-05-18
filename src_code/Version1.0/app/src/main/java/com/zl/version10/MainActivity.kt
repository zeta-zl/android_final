package com.zl.version10

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.zl.version10.utils.NavContent
import com.zl.version10.utils.StaticObj
import com.zl.version10.utils.registerForRequestPermissionLauncher
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


