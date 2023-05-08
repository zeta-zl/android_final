package com.zl.timeselect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zl.timeselect.StaticObj
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        StaticObj.initPersonDatabase(this)
        GlobalScope.launch(Dispatchers.Default) {
            coroutineScope {
                StaticObj.initDatabaseList = StaticObj.TimeDataBase.TimeDataDao().getAll()
            }
        }
        setContent {

            SettingSettingNavContent()
        }
    }


}

@Composable
fun SettingSettingNavContent() {
    val navHostController = rememberNavController()
    //startDestination:指定默认进入的页面
    NavHost(navController = navHostController, startDestination = "TestTop3") {


        composable("TestTop3") { SelectTimeList() }
    }
}