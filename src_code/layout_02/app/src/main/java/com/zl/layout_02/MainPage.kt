package com.zl.layout_02

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
import androidx.compose.material.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun MainPage(navHostController : NavHostController) {
    val imageList = listOf(
        R.drawable.baseline_gps_fixed_24,
        R.drawable.baseline_gps_not_fixed_24,
        R.drawable.baseline_gps_off_24,
        R.drawable.baseline_grade_24
    )
    Column {
        Scaffold(
            content = { padding ->
                Column(
                    modifier = Modifier
                        .padding(padding)
                ) {
                    CapsuleSlider(imageList, Modifier.clickable {
                        navHostController.navigate("PersonList")
                    })
                    Text(text = "test")
                }
            }, bottomBar = { BottomNavigationBar(navHostController) }
        )

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
