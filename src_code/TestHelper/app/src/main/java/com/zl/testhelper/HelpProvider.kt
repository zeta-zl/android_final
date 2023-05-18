package com.zl.testhelper

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

interface HelpProvider {
    @Composable
    fun Help(extraData : Any?)

    @Composable
    fun Helper(isHelpShow : MutableState<Boolean>, extraData : Any?) {
        Log.i("zeta", "Helper loaded ${isHelpShow.value}")
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            if (isHelpShow.value) {
                Help(extraData)
            }
        }

    }
}


class PersonListHelper() : HelpProvider {
    @Composable
    override fun Help(extraData : Any?) {
        val customColor = Color.Red
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            if (extraData == 0 || extraData == 1) {
                Spacer(modifier = Modifier.height(90.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.1F)

                ) {

                }
                Column(
                    modifier = Modifier
                        .align(Alignment.End)
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.End)
                            .fillMaxHeight(0.06F)
                            .fillMaxWidth(0.4F)
                            .border(
                                width = 1.dp,
                                color = customColor,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(4.dp)
                    ) {
                        Text(
                            text = "↑这里可以新增联系人",
                            modifier = Modifier.fillMaxSize(),
                            textAlign = TextAlign.End,
                            style = TextStyle(
                                fontSize = 16.sp
                            ),
                            color = customColor
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                if (extraData == 1) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Start)
                    ) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.Start)
                                .fillMaxHeight(0.06F)
                                .fillMaxWidth(0.4F)
                                .border(
                                    width = 1.dp,
                                    color = customColor,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(4.dp)
                        ) {
                            Text(
                                text = "↑点击联系人可以修改",
                                modifier = Modifier.fillMaxSize(),
                                textAlign = TextAlign.Start,
                                style = TextStyle(
                                    fontSize = 16.sp
                                ),
                                color = customColor
                            )
                        }

                    }
                }

            }
        }
    }
}

class MainHelper() : HelpProvider {
    @Composable
    override fun Help(extraData : Any?) {
        val customColor = Color.Black
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.61F)
            ) {

            }
            Column(
                modifier = Modifier
                    .align(Alignment.Start)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .fillMaxHeight(0.15F)
                        .fillMaxWidth(0.25F)
                        .border(
                            width = 1.dp,
                            color = customColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(4.dp)
                ) {
                    Text(
                        text = "→这里更新每日状态",
                        modifier = Modifier.fillMaxSize(),
                        textAlign = TextAlign.Start,
                        style = TextStyle(
                            fontSize = 12.sp
                        ),
                        color = customColor
                    )
                }

            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.1F)
            ) {

            }
            Column(
                modifier = Modifier
                    .align(Alignment.Start)
                    .fillMaxHeight(0.19F)
                    .fillMaxWidth(0.25F)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .border(
                            width = 1.dp,
                            color = customColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(4.dp)
                ) {
                    Text(
                        text = "→先点这里获取权限",
                        modifier = Modifier.fillMaxSize(),
                        textAlign = TextAlign.Start,
                        style = TextStyle(
                            fontSize = 12.sp
                        ),
                        color = customColor
                    )
                }

            }

        }
    }

}

