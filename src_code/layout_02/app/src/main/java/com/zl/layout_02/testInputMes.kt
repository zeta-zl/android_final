package com.zl.layout_02

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingInput() {
    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "短信内容:", fontSize = 30.sp)
        TextField(
            modifier = Modifier.
            height(240.dp).
                fillMaxWidth(),
            value = text1, // 文本
            onValueChange = { newText ->
                text1 = newText
            }, // 文本变化事件

        )
        Text(text = "邮件内容:", fontSize = 30.sp)
        TextField(
            modifier = Modifier.
            height(240.dp).
            fillMaxWidth(),
            value = text2, // 文本
            onValueChange = { newText ->
                text2 = newText
            }, // 文本变化事件

        )
    }
}

