package com.zl.layout_02

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

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