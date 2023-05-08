package com.zl.version04


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zl.version04.SelectTimeList
import com.zl.version04.SettingInput

@Composable
fun SettingSettingNavContent() {
    val navHostController = rememberNavController()
    //startDestination:指定默认进入的页面
    NavHost(navController = navHostController, startDestination = "setting") {

        composable("setting") { SettingSettingSetting(navHostController) }
        composable("Time") { SettingSettingTime(navHostController) }
        composable("ChangeMessage") { SettingSettingChangeMessage(navHostController) }
        composable("Off") { SettingSettingOff(navHostController) }
    }
}

@Composable
fun SettingSettingSetting(navController : NavHostController) {
    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxWidth()
    )
    {

        Row(Modifier.height(70.dp)) {
            Text(text = "Time",
                modifier = Modifier
                    .clickable {
                        navController.navigate("Time")
                    })
        }
        Divider(
            //颜色
            color = Color.Black,
            //线的高度
            thickness = 1.dp,
            //距离开始的间距
//                            startIndent = 10.dp
        )
        Row(Modifier.height(70.dp)) {
            Text(text = "ChangeMessage",
                modifier = Modifier.clickable {
                    navController.navigate("ChangeMessage")
                })
        }
        Divider(
            //颜色
            color = Color.Black,
            //线的高度
            thickness = 1.dp,
            //距离开始的间距
//                            startIndent = 10.dp
        )
        Row(Modifier.height(70.dp)) {
            Text(text = "Off",
                modifier = Modifier.clickable {
                    navController.navigate("Off")
                })
        }
    }
}


@Composable
fun SettingSettingTime(navController : NavHostController) {
//    TimeSelect()
    SelectTimeList()
}

@Composable
fun SettingSettingChangeMessage(navController : NavHostController) {
    SettingInput()
}

@Composable
fun SettingSettingOff(navController : NavHostController) {
    throw NotImplementedError()
}
