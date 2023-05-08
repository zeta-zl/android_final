package com.zl.version04

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.zl.version04.SettingSettingNavContent

@Composable
fun SettingPerson() {
    Text(text = "Person")
}

@Composable
fun SettingSetting() {
//    Text(text = "Setting")
    SettingSettingNavContent()
}

@Composable
fun SettingAbout() {
    Text(text = "About")
}