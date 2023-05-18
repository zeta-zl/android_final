package com.zl.version10.pages.user.setting

import DatePicker
import android.util.Log
import androidx.compose.runtime.Composable

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.zl.version10.database.SelectTime
import com.zl.version10.utils.StaticObj
import com.zl.version10.utils.cancelAlarm
import com.zl.version10.utils.setItem
import kotlinx.coroutines.*

private val itemsHeight = 90.dp


@OptIn(ExperimentalMaterialApi::class, DelicateCoroutinesApi::class)
@Composable
fun SelectTimeList(navController : NavHostController) {
    val SelectTimeData = remember {
        createSelectTimeObj(StaticObj.initTimeDatabaseList)
    }
    var selectItem = remember {
        mutableStateOf(-1)
    }

    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val enabledText = listOf("禁用", "启用")
    val context = LocalContext.current
    ModalBottomSheetLayout(
        sheetContent = {
            Box(
                Modifier
                    .defaultMinSize(minHeight = 1.dp)
            )
            if ((0 <= selectItem.value) and (selectItem.value < SelectTimeData.size)) {
                Log.i("zeta", "select $selectItem.value ")
                DatePicker(
                    SelectTimeData[selectItem.value].value.noon,
                    SelectTimeData[selectItem.value].value.hour,
                    SelectTimeData[selectItem.value].value.minute
                ) { selected, noon, hour, minute ->
                    run {
//                    selectItem.value = -1  //用于通知刷新
                        SelectTimeData[selectItem.value].value = SelectTime(
                            SelectTimeData[selectItem.value].value,
                            _noon = noon,
                            _hour = hour,
                            _minute = minute,
                            _id = SelectTimeData[selectItem.value].value.id
                        )
                        GlobalScope.launch(Dispatchers.Default)
                        {
                            runBlocking {
                                coroutineScope {
//                                            val temp = Person(person.value, _id = person.value.id)
                                    StaticObj.TimeDataBase.TimeDataDao()
                                        .update(
                                            SelectTimeData[selectItem.value].value
                                        )

                                    setItem(
                                        context,
                                        SelectTimeData[selectItem.value].value
                                    )
                                }
                                selectItem.value = -1
                            }
                        }
                    }
                    scope.launch {
                        sheetState.hide()        // 隐藏
                    }
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 1.dp),
        sheetShape = MaterialTheme.shapes.large,
        sheetState = sheetState,
    ) {
        SelectTimeListDataList(
            SelectTimeData,
            selectItem,
            scope,
            sheetState,
            navController
        )
    }
}


@OptIn(ExperimentalMaterialApi::class, DelicateCoroutinesApi::class)
@Composable
fun SelectTimeListDataList(
    SelectTimeData : SnapshotStateList<MutableState<SelectTime>>,
    selectItem : MutableState<Int>,
    scope : CoroutineScope,
    sheetState : ModalBottomSheetState,
    navController : NavHostController
) {
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
                        navController.popBackStack()
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
                                    enabled = true, important = true,
                                    id = SelectTime.maxId + 1
                                )
                            )
                        )
                        selectItem.value = SelectTimeData.size - 1
                        GlobalScope.launch(Dispatchers.Default) {
                            coroutineScope {
                                StaticObj.TimeDataBase.TimeDataDao()
                                    .add(SelectTimeData[selectItem.value].value)
                                setItem(
                                    context,
                                    SelectTimeData[selectItem.value].value
                                )
                            }
                        }
                        scope.launch {
                            sheetState.show()        // 显示
                        }
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
            SelectTimeLazyList(
                SelectTimeData,
                selectItem,
                scope,
                sheetState
            )
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
    val context = LocalContext.current
    LazyColumn(
        state = scrollState,
        verticalArrangement = Arrangement.Center
    ) {
        itemsIndexed(SelectTimeData) { index, item ->
            Box {
                val showingDialog = remember {
                    mutableStateOf(false)
                }
                SelectTimeCard(item, index, select)
                Button(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(0.dp, 7.dp, 4.dp, 3.dp)
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
                        .padding(0.dp, 3.dp, 4.dp, 5.dp)
                        .height(itemsHeight / 3),
                    contentPadding = (PaddingValues(1.dp)
                            ),
                    onClick = {
                        runBlocking {
                            coroutineScope {
                                cancelAlarm(context, SelectTimeData[index].value)
                                StaticObj.TimeDataBase.TimeDataDao()
                                    .delete(
                                        SelectTime(
                                            SelectTimeData[index].value,
                                            _id = SelectTimeData[index].value.id
                                        )
                                    )
                            }
                            SelectTimeData.removeAt(index)
                        }
                    }) {
                    Text(text = "删除")
                }
            }
        }
    }

}

@Composable
fun SelectTimeCard(SelectTime : MutableState<SelectTime>, index : Int, select : MutableState<Int>) {
    val context = LocalContext.current
    val enabledText = listOf("禁用", "启用")
    val importanceText = listOf("提示", "发送")
    val noonText = listOf("上午", "下午")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (index == select.value)
                    Color.LightGray
                else
                    Color.White
            )
            .clickable {
                select.value = if (select.value != index) index else -1
            }
            .shadow(
                4.dp,
                RoundedCornerShape(1.dp)
            )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                RadioButton(
                    selected = SelectTime.value.enabled,
                    onClick = {
                        SelectTime.value =
                            SelectTime(
                                SelectTime.value,
                                _enabled = !SelectTime.value.enabled
                            )
                        setItem(context, SelectTime.value)
                        StaticObj.TimeDataBase.TimeDataDao()
                            .update(SelectTime.value)
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
                            SelectTime(
                                SelectTime.value,
                                _important = !SelectTime.value.important
                            )
                        setItem(context, SelectTime.value)
                        StaticObj.TimeDataBase.TimeDataDao()
                            .update(SelectTime.value)
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
                    text = "时间:${noonText[SelectTime.value.noon]}" +
                            "${SelectTime.value.hour} 时 " +
                            "${SelectTime.value.minute} 分",
                    fontSize = 30.sp
                )
            }

        }
    }
}


fun createSelectTimeObj() : SnapshotStateList<MutableState<SelectTime>> {
    val ret : SnapshotStateList<MutableState<SelectTime>> = SnapshotStateList()
    for (i in 1..3) {
        ret.add(
            mutableStateOf(
                SelectTime(
                    0, 0, 0, enabled = true,
                    important = true, id = SelectTime.maxId + 1
                )
            )
        )
        SelectTime.maxId++
    }
    return ret
}


fun createSelectTimeObj(l : List<SelectTime>) :
        SnapshotStateList<MutableState<SelectTime>> {
    val ret : SnapshotStateList<MutableState<SelectTime>> = SnapshotStateList()
    for (i in l) {
        SelectTime.maxId = SelectTime.maxId.coerceAtLeast(i.id)
        ret.add(mutableStateOf(i))
    }
    return ret
}
