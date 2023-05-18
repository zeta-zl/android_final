package com.zl.version10.pages.mainpage

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.zl.testhelper.*
import com.zl.version10.utils.*
import com.zl.version10.utils.MainPageHelpStateManager


@Composable
fun MainPage(navHostController : NavHostController) {
//    val imageList = listOf(
//        R.drawable.baseline_gps_fixed_24,
//        R.drawable.baseline_gps_not_fixed_24,
//        R.drawable.baseline_gps_off_24,
//        R.drawable.baseline_grade_24
//    )

    val context = LocalContext.current

    val isMainHelpEnabled = remember {
        mutableStateOf(
            MainPageHelpStateManager
                .getState(context)
                ?.toBooleanStrictOrNull() != false
        )
    }
//    Column {
    Scaffold(
        content = { padding ->
            val mainHelper = MainHelper()
            if (
                MainPageHelpStateManager
                    .getState(context)
                    ?.toBooleanStrictOrNull() != false
            ) {
                mainHelper.Helper(isHelpShow = isMainHelpEnabled, extraData = null)
                Log.i("zeta", "MainHelper")
            }
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxWidth(),
//                    contentAlignment = Alignment.
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.1F)
                ) {

                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.6F),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .padding(24.dp)
                            .clip(
                                RoundedCornerShape(16.dp)
                            )
                            .background(Color.LightGray)
                    ) {
                        Column(
                            modifier = Modifier
                                .clickable {
                                    navHostController.navigate("PersonList")
                                }
                                .padding(vertical = 24.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                Text(
                                    text = "联系人列表",
                                    style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 36.sp
                                    ),
                                )
                                Divider(
                                    color = Color.Black,
                                    thickness = 1.dp,
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp)
                                )
                                Text(
                                    text = "这是联系人列表的介绍",
                                    style = TextStyle(
                                        fontSize = 20.sp
                                    ),
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))


                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement =
                    Arrangement.Center,
                    horizontalAlignment =
                    Alignment.CenterHorizontally
                ) {
                    ButtonList(context, isMainHelpEnabled)
                }
            }
        }, bottomBar = {
            BottomNavigationBar(navHostController)
        }
    )

//    }
}

@Composable
fun ButtonList(
    context : Context,
    isMainHelpEnabled : MutableState<Boolean>
) {
    val hasPermission = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context as Activity,
                Manifest.permission.SEND_SMS
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    Log.i("zeta", "permission ${hasPermission.value}")
    if (hasPermission.value) {
        Button(
            onClick = {
                TestSend(context)
                MainPageHelpStateManager
                    .saveState(context, false.toString())
            },
            modifier = Modifier
                .padding(end = 8.dp)
                .size(120.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Text("点击以更新今日状态")
        }
    } else {
        Column(
//            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    TestSend(context)
                    MainPageHelpStateManager
                        .saveState(context, false.toString())
                },
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .height(56.dp)
                    .width(200.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Text("点击以更新今日状态")
            }
            Button(
                onClick = {
                    MainPageHelpStateManager
                        .saveState(context, false.toString())
                    showConfirmationDialog(context as Activity) {
//                        hasPermission.value =
                        requestPermissionsAndSendMessagesAndEmails()
                    }
                },
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .height(56.dp)
                    .width(200.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Text("获取权限")
            }

        }

    }

}


@Composable
fun CapsuleSlider(
    images : List<Int>,
    modifier : Modifier = Modifier
) {
    val scrollState = rememberLazyListState()

    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9F)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .clip(RoundedCornerShape(50))
                .background(Color.LightGray)
                .padding(horizontal = 8.dp)
                .fillMaxWidth(0.5F)

        ) {
            LazyRow(
                state = scrollState,
                horizontalArrangement = Arrangement.Center,
                modifier = modifier
            ) {
                items(images) { image : Int ->
                    Image(
                        painter = painterResource(id = image),
                        contentDescription = null,
                        modifier = Modifier
                            .width(60.dp)
                            .height(60.dp)
                            .clip(CircleShape)
                            .border(
                                width = 2.dp,
                                color = Color.White,
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}
