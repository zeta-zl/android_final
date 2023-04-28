package com.zl.layout_03

import DatePicker
import androidx.compose.runtime.Composable

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectTimeList() {
    val SelectTimeData = remember {
        createSelectTimeObj()
    }
    var selectItem = remember {
        mutableStateOf(-1)
    }

    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val enabledText = listOf("禁用", "启用")
    ModalBottomSheetLayout(
        sheetContent = {
            Box(Modifier.defaultMinSize(minHeight = 1.dp))
//            DatePicker(selectTime = SelectTimeData[if (selectItem.value != -1) selectItem.value else 0]) {
//                it->
//                run {
//                    SelectTimeData[if (selectItem.value != -1) selectItem.value else 0].value = it
//                }
            DatePicker(
                SelectTimeData[relu(selectItem.value)].value.noon,
                SelectTimeData[relu(selectItem.value)].value.hour,
                SelectTimeData[relu(selectItem.value)].value.minute
            ) { selected, noon, hour, minute ->
                run {
//                    val items = listOf("上午", "下午")
//                    SelectTimeData[selectItem.value].value.noon = noon
//                    SelectTimeData[selectItem.value].value.hour = hour
//                    SelectTimeData[selectItem.value].value.minute = minute
//                    selectItem.value = -1  //用于通知刷新
                    SelectTimeData[selectItem.value].value = SelectTime(noon, hour, minute)
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
        SelectTimeListDataList(SelectTimeData, selectItem, scope, sheetState)
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectTimeListDataList(
    SelectTimeData : SnapshotStateList<MutableState<SelectTime>>,
    selectItem : MutableState<Int>,
    scope : CoroutineScope,
    sheetState : ModalBottomSheetState
) {


    var temp = remember {
        mutableStateOf(SelectTime(0, 0, 0, enabled = true, important = true))
    }
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "SelectTimeList",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center)//设置标题文本居中显示
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                navigationIcon = {
                    IconButton(onClick = {
                        Toast.makeText(context, "返回", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        SelectTimeData.add(
                            mutableStateOf(
                                SelectTime(
                                    0, 0, 0,
                                    enabled = true, important = true
                                )
                            )
                        )
                        selectItem.value = SelectTimeData.size - 1
                        scope.launch {
                            sheetState.show()        // 显示
                        }
                        temp.value = SelectTime(0, 0, 0, enabled = true, important = true)
                        Toast.makeText(context, "添加", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(Icons.Filled.Add, null)
                    }
                },
                backgroundColor = Color.White,//背景色
                contentColor = Color.Red, //子级内容颜色
            )
        }
    )
    {

        Box(modifier = Modifier.padding(it)) {
            SelectTimeLazyList(SelectTimeData, selectItem, scope, sheetState)
        }
    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectTimeLazyList(
    SelectTimeData : SnapshotStateList<MutableState<SelectTime>>,
    select : MutableState<Int>,
    scope : CoroutineScope,
    sheetState : ModalBottomSheetState
) {
    val scrollState = rememberLazyListState()
    LazyColumn(
        state = scrollState,
        verticalArrangement = Arrangement.Center

    ) {
        itemsIndexed(SelectTimeData) { index, item ->
            Box {
                val showingDialog = remember { mutableStateOf(false) }
//                newSelectTimeDialog("修改", SelectTimeData, index, showingDialog)
                SelectTimeCard(item, index, select)
                Button(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(0.dp, 2.dp, 0.dp, 2.dp)
                        .height(itemsHeight / 3)
//                        .border(5.dp, Color.Blue)
                    ,
                    contentPadding = (PaddingValues(1.dp)),
                    onClick = {
                        select.value = index
                        showingDialog.value = true
//                        SelectTimeData[index] = p
                        scope.launch {
                            sheetState.show()        // 显示
                        }
                    }) {
                    Text(text = "修改")
                }
                Button(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(0.dp, 2.dp, 0.dp, 2.dp)
                        .height(itemsHeight / 3),
                    contentPadding = (PaddingValues(1.dp)),
                    onClick = {
                        SelectTimeData.removeAt(index)
                    }) {
                    Text(text = "删除")
                }
            }
        }
    }

}

@Composable
fun SelectTimeCard(SelectTime : MutableState<SelectTime>, index : Int, select : MutableState<Int>) {

    val enabledText = listOf("禁用", "启用")
    val importanceText = listOf("重要", "重要")
    val noonText = listOf("上午", "下午")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (index == select.value) Color.LightGray else Color.White)
            .clickable {
                select.value = if (select.value != index) index else -1
            }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                RadioButton(
                    selected = SelectTime.value.enabled,
                    onClick = {
                        SelectTime.value =
                            SelectTime(SelectTime.value, _enabled = !SelectTime.value.enabled)
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color.Black,               //选中的颜色
                        unselectedColor = Color.Black,              //未选中的颜色
                        disabledColor = Color.Gray                  //禁用的颜色
                    )
                )
                Text(
                    text = enabledText[SelectTime.value.enabled.compareTo(false)], //!enabled
                    fontSize = 30.sp,
                )
                RadioButton(
                    selected = SelectTime.value.important,
                    onClick = {
                        SelectTime.value =
                            SelectTime(SelectTime.value, _important = !SelectTime.value.important)
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color.Black,               //选中的颜色
                        unselectedColor = Color.Black,              //未选中的颜色
                        disabledColor = Color.Gray                  //禁用的颜色
                    )
                )
                Text(
                    text = importanceText[SelectTime.value.important.compareTo(false)],
                    fontSize = 30.sp,
                )
            }
            Row(Modifier.height(40.dp)) {

                Text(
                    text = "时间:${noonText[SelectTime.value.noon]} ${SelectTime.value.hour} 时 ${SelectTime.value.minute} 分",
                    fontSize = 30.sp
                )
            }

        }
    }
}

fun relu(a : Int) : Int {
    return if (a >= 0) a else 0
}

fun createSelectTimeObj() : SnapshotStateList<MutableState<SelectTime>> {
    val ret : SnapshotStateList<MutableState<SelectTime>> = SnapshotStateList()
    for (i in 1..3) {
        ret.add(mutableStateOf(SelectTime(0, 0, 0, enabled = true, important = true)))
    }
    return ret
}


data class SelectTime(
    var noon : Int = 0,
    var hour : Int = 0,
    var minute : Int = 0,
    var enabled : Boolean = true,
    var important : Boolean = true

) {
    constructor(p : SelectTime) : this() {
        noon = p.noon
        hour = p.hour
        minute = p.minute
        enabled = p.enabled
        important = p.important
    }

    constructor(
        p : SelectTime,
        _noon : Int? = null,
        _hour : Int? = null,
        _minute : Int? = null,
        _enabled : Boolean? = null,
        _important : Boolean? = null
    ) : this() {

        noon = _noon ?: p.noon
        hour = _hour ?: p.hour
        minute = _minute ?: p.minute
        enabled = _enabled ?: p.enabled
        important = _important ?: p.important
    }

}