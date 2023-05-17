package com.zl.testhelper

import DatePicker
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*


import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch


@Preview
@Composable
fun TimeSelect() {
    Column {
        SelectTime()
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectTime() {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    var selectNoon1 by remember { mutableStateOf("上午") }
    var selectHour1 by remember { mutableStateOf(0) }
    var selectMinute1 by remember { mutableStateOf(0) }
    var selectNoon2 by remember { mutableStateOf("上午") }
    var selectHour2 by remember { mutableStateOf(0) }
    var selectMinute2 by remember { mutableStateOf(0) }

    var selectedIdx by remember { mutableStateOf(0) }

//    var selectNoonTemp : String = ""
//    var selectHourTemp : Int = 0

//    var selectMinuteTemp : Int = 0
//    var selectNoonTemp by remember { mutableStateOf("上午") }
//    var selectHourTemp by remember { mutableStateOf(0) }
//    var selectMinuteTemp by remember { mutableStateOf(0) }
    var enabled1 by remember { mutableStateOf(true) }
    var enabled2 by remember { mutableStateOf(true) }
    val enabledText = listOf("禁用", "启用")
    ModalBottomSheetLayout(
        sheetContent = {
            Box(Modifier.defaultMinSize(minHeight = 1.dp))
            DatePicker { selected, noon, hour, minute ->
                run {
                    val items = listOf("上午", "下午")
//                    selectNoonTemp = items[noon]
//                    selectHourTemp = hour
//                    selectMinuteTemp = minute
                    if (selectedIdx == 0) {
                        selectNoon1 = items[noon]
                        selectHour1 = hour
                        selectMinute1 = minute
                    } else {
                        selectNoon2 = items[noon]
                        selectHour2 = hour
                        selectMinute2 = minute
                    }
                }
                scope.launch {
                    sheetState.hide()        // 隐藏
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 1.dp),
        sheetShape = MaterialTheme.shapes.large,
        sheetState = sheetState,
    ) {
        Column {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Column(
                    modifier = Modifier
                        .height(30.dp)
                        .fillMaxWidth()
                ) {

                }
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row {
                        RadioButton(
                            selected = enabled1,
                            onClick = ({ enabled1 = !enabled1 }),
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.Black,               //选中的颜色
                                unselectedColor = Color.Black,              //未选中的颜色
                                disabledColor = Color.Gray                  //禁用的颜色
                            )
                        )
                        Text(
                            text = enabledText[enabled1.compareTo(false)],
                            fontSize = 30.sp,
                        )// !enabled
                    }
                    Row(Modifier.height(40.dp)) {

                        Text(
                            text = "时间:",
                            fontSize = 30.sp
                        )
                    }
                    Box(
                        Modifier
                            .height(70.dp)
                            .fillMaxWidth()
                    )
                    {

                        Text(
                            modifier = Modifier.align(Alignment.BottomStart),
                            text = "$selectNoon1 $selectHour1 时 $selectMinute1 分",
                            fontSize = 30.sp,
                        )
                        Button(
                            modifier = Modifier.align(Alignment.BottomEnd),
                            onClick = {
                                selectedIdx = 0
                                scope.launch {
                                    sheetState.show()    // 显示
                                }
//                                selectNoon1 = selectNoonTemp
//                                selectHour1 = selectHourTemp
//                                selectMinute1 = selectMinuteTemp

                            }) {
                            Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                            Text(text = "修改")
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Column(
                    modifier = Modifier
                        .height(30.dp)
                        .fillMaxWidth()
                ) {

                }
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row {
                        RadioButton(
                            selected = enabled2,
                            onClick = ({ enabled2 = !enabled2 }),
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.Black,               //选中的颜色
                                unselectedColor = Color.Black,              //未选中的颜色
                                disabledColor = Color.Gray                  //禁用的颜色
                            )
                        )
                        Text(
                            text = enabledText[enabled2.compareTo(false)],
                            fontSize = 30.sp,
                        )// !enabled
                    }
                    Row(Modifier.height(40.dp)) {

                        Text(
                            text = "时间:",
                            fontSize = 30.sp
                        )
                    }
                    Box(
                        Modifier
                            .height(70.dp)
                            .fillMaxWidth()
                    )
                    {

                        Text(
                            modifier = Modifier.align(Alignment.BottomStart),
                            text = "$selectNoon2 $selectHour2 时 $selectMinute2 分",
                            fontSize = 30.sp,
                        )
                        Button(
                            modifier = Modifier.align(Alignment.BottomEnd),
                            onClick = {
                                selectedIdx = 1
                                scope.launch {
                                    sheetState.show()    // 显示
                                }
//                                selectNoon2 = selectNoonTemp
//                                selectHour2 = selectHourTemp
//                                selectMinute1 = selectMinuteTemp

                            }) {
                            Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                            Text(text = "修改")
                        }
                    }
                }
            }
        }
    }
    //返回键 处理  ModalBottomSheet 收回
    BackHandler(
        enabled = (sheetState.currentValue == ModalBottomSheetValue.HalfExpanded ||
                sheetState.currentValue == ModalBottomSheetValue.Expanded),
        onBack = {
            scope.launch {
                sheetState.hide()
            }
        }
    )
}
