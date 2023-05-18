package com.zl.testhelper

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.zl.testhelper.TextStorage.StringStorageManager.getEmailContent
import com.zl.testhelper.TextStorage.StringStorageManager.getMessageContent
import com.zl.testhelper.TextStorage.StringStorageManager.saveEmailContent
import com.zl.testhelper.TextStorage.StringStorageManager.saveMessageContent

@Composable
fun SettingInput(navController : NavHostController) {
    val context = LocalContext.current
    var text1 by remember {
        mutableStateOf(
            getMessageContent(context) ?: ""
        )
    }
    var text2 by remember {
        mutableStateOf(
            getEmailContent(context) ?: ""
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "SettingInput",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center)//设置标题文本居中显示
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    })
                    {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                },
                actions = {
                    IconButton(onClick = {
//                        isHelpShowing.value = !isHelpShowing.value
                    }) {
//                        Text(helpTextList[helpText])
                    }
                },
                backgroundColor = Color.White,//背景色
                contentColor = Color.Red, //子级内容颜色
            )
        }
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.1F)
            ) {
            }
            Text(
                text = "短信内容:",
                fontSize = 30.sp
            )
            TextField(
                modifier = Modifier
                    .height(240.dp)
                    .fillMaxWidth(),
                value = text1,
                onValueChange =
                { newText ->
                    text1 = newText
                    saveMessageContent(context, newText)
                },
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.05F)
            ) {
            }
            Text(text = "邮件内容:", fontSize = 30.sp)
            TextField(
                modifier = Modifier
                    .height(240.dp)
                    .fillMaxWidth(),
                value = text2,
                onValueChange =
                { newText ->
                    text2 = newText
                    saveEmailContent(context, newText)
                },
            )
        }
    }
}

