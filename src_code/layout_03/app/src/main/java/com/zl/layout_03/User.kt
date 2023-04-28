package com.zl.layout_03


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun User(navHostController : NavHostController) {
    Scaffold(
        content = { padding ->
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(padding)
            ) {
                Column(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.baseline_account_circle_24),
                            contentDescription = "null_person",
                            modifier = Modifier
                                .size(200.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }

                    SettingDetails(navHostController )
                }
            }
        }, bottomBar = {
            Box(
                modifier = Modifier
                    .paddingFromBaseline(bottom = 0.dp)
            ) {
                BottomNavigationBar(navHostController)
            }
        })
}
@Composable
fun SettingDetails(navHostController : NavHostController){
    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxWidth()
    )
    {

        Row(Modifier.height(70.dp)) {
            Text(text = "Person",
                modifier = Modifier
                    .clickable {
                        navHostController.navigate("Person")
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
            Text(text = "Setting",
                modifier = Modifier.clickable {
                    navHostController.navigate("Setting")
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
            Text(text = "About",
                modifier = Modifier.clickable {
                    navHostController.navigate("About")
                })
        }
    }
}
