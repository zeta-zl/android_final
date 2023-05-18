package com.zl.testhelper


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun SettingSettingNavContent() {
    val navHostController = rememberNavController()
    //startDestination:指定默认进入的页面
    NavHost(navController = navHostController, startDestination = "setting") {

        composable("setting") {
            uploadDataBase()
            Settings(navHostController)
        }
        composable("Time") {
            uploadDataBase()
            SettingTime(navHostController)
        }
        composable("ChangeMessage") {
            uploadDataBase()
            SettingChangeMessage(navHostController)
        }
        composable("Off") {
            uploadDataBase()
        }
    }
}


@Composable
fun Settings(navController : NavHostController) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxWidth()
            .fillMaxHeight(), // 垂直方向上居中
        verticalArrangement = Arrangement.Center // 内容垂直居中
    ) {
        Section(
            title = "Time",
            details = "Set the time",
            onClick = { navController.navigate("Time") }
        )
        Divider(
            color = Color.Black,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Section(
            title = "ChangeMessage",
            details = "Change the message content",
            onClick = { navController.navigate("ChangeMessage") }
        )
        Divider(
            color = Color.Black,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Section(
            title = "Off",
            details = "Turn off all",
            onClick = {
                settingOff(context,navController)
            }
        )
    }
}

@Composable
fun Section(title : String, details : String, onClick : () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .clickable { onClick() }
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = details,
                style = MaterialTheme.typography.body1
            )
        }
    }
}


@Composable
fun SettingTime(navController : NavHostController) {
//    TimeSelect()
    SelectTimeList(navController)
}

@Composable
fun SettingChangeMessage(navController : NavHostController) {
    SettingInput(navController)
}

//@Composable
fun settingOff(context: Context, navController : NavHostController) {
    StaticObj.initTimeDatabaseList.forEach { selectTime ->
        selectTime.enabled = false
        StaticObj.TimeDataBase.TimeDataDao().update(selectTime)
    }
    Toast.makeText(context, "已全部禁用", Toast.LENGTH_SHORT).show()

}
