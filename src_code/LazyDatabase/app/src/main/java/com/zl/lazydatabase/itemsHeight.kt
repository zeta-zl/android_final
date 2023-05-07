package com.zl.lazydatabase
//
//import android.util.Log
//import android.widget.Toast
//import androidx.compose.foundation.*
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.*
//import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.runtime.*
//import androidx.compose.runtime.snapshots.SnapshotStateList
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import kotlinx.coroutines.*
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.forEach
//import kotlinx.coroutines.flow.toList
//import kotlin.concurrent.thread
//import kotlin.random.Random
//
//
//val itemsHeight = 90.dp
//
////@Preview
//@OptIn(DelicateCoroutinesApi::class)
//@Composable
//fun PersonList() {
//
////    val tempState : Flow<List<Person>> = StaticObj.personDataBase.PersonDataDao().getAll()
////    val tempList by tempState.collectAsState(initial = listOf())
////    val tempList = StaticObj.personDataBase.PersonDataDao().getAll()
////    var personData = createPersonObj(StaticObj.initDatabaseList)
//    var personData = createPersonObj()
//
////    val personData = tempState
//    var selectItem = remember {
//        mutableStateOf(-1)
//    }
//
//    var temp = remember {
//        mutableStateOf(Person("", "", ""))
//    }
//    val showingDialog = remember { mutableStateOf(false) }
////    if (personData.size > 0) {
//    newPersonDialog("添加", personData, personData.size-1, showingDialog)
////    }
//    val context = LocalContext.current
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        "PersonList",
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .wrapContentSize(Alignment.Center)//设置标题文本居中显示
//                    )
//                },
//                modifier = Modifier.fillMaxWidth(),
//                navigationIcon = {
//                    IconButton(onClick = {
//                        Toast.makeText(context, "返回", Toast.LENGTH_SHORT).show()
//                    }) {
//                        Icon(Icons.Filled.ArrowBack, null)
//                    }
//                },
//                actions = {
//                    IconButton(onClick = {
//                        showingDialog.value = !showingDialog.value
////                        personData.add(mutableStateOf(Person("", "", "")))
////                        if (personData.size == 0) {
////                            GlobalScope.launch(Dispatchers.Default) {
////                                coroutineScope {
////                                    StaticObj.personDataBase.PersonDataDao().add(Person("", "", ""))
////                                    Log.i("zeta", "add a")
////                                }
////                            }
////                        }
//                        temp.value = Person("", "", "")
//                        Toast.makeText(context, "添加", Toast.LENGTH_SHORT).show()
//                    }) {
//                        Icon(Icons.Filled.Add, null)
//                    }
//                },
//                backgroundColor = Color.White,//背景色
//                contentColor = Color.Red, //子级内容颜色
//            )
//        }
//    )
//    {
////        Text(tempList.toString())
////        personData = remember {
////            createPersonObj(tempList)
////        }
////        Text("del", modifier = Modifier.clickable {
////            GlobalScope.launch(Dispatchers.Default) {
////                coroutineScope {
////                    StaticObj.personDataDao.delete(tempList[tempList.size - 1])
////                }
////            }
////        })
//
//        Box(modifier = Modifier.padding(it)) {
//            PersonLazyList(personData, selectItem)
//        }
//    }
//
//}
//
//
//@OptIn(DelicateCoroutinesApi::class)
//@Composable
//fun PersonLazyList(
//    personData : SnapshotStateList<MutableState<Person>>,
//    select : MutableState<Int>
//) {
//    val scrollState = rememberLazyListState()
//    LazyColumn(
//        state = scrollState,
//        verticalArrangement = Arrangement.Center
//
//    ) {
//        itemsIndexed(personData) { index, item ->
//            Box {
//                val showingDialog = remember { mutableStateOf(false) }
//                newPersonDialog("修改", personData, index, showingDialog)
//                PersonCard(item, index, select)
//                Button(
//                    modifier = Modifier
//                        .align(Alignment.TopEnd)
//                        .padding(0.dp, 2.dp, 0.dp, 2.dp)
//                        .height(itemsHeight / 3)
////                        .border(5.dp, Color.Blue)
//                    ,
//                    contentPadding = (PaddingValues(1.dp)),
//                    onClick = {
//                        showingDialog.value = true
////                        personData[index] = p
//                    }) {
//                    Text(text = "修改")
//                }
//                Button(
//                    modifier = Modifier
//                        .align(Alignment.BottomEnd)
//                        .padding(0.dp, 2.dp, 0.dp, 2.dp)
//                        .height(itemsHeight / 3),
//                    contentPadding = (PaddingValues(1.dp)),
//                    onClick = {
//                        GlobalScope.launch(Dispatchers.Default) {
//                            coroutineScope {
//                                StaticObj.personDataBase.PersonDataDao()
//                                    .delete(personData[index].value)
//                                personData.removeAt(index)
//                            }
//                        }
//                    }) {
//                    Text(text = "删除")
//                }
//            }
//        }
//    }
//
//}
//
//fun iterableTOString(a : List<Any>) : String {
//    val s = StringBuffer()
//    for (i in a) {
//        s.append(i).append('\n')
//    }
//    return s.toString()
//}
//
//@OptIn(DelicateCoroutinesApi::class)
//@Composable
//fun newPersonDialog(
//    title : String,
////    person : MutableState<Person>,
//    personData : SnapshotStateList<MutableState<Person>>,
//    index : Int,
//    showingDialog : MutableState<Boolean>
//) : MutableState<Person> {
//
//
//    val person : MutableState<Person> =
//        try {
//            personData[index]
//        } catch (e : java.lang.Exception) {
//            remember { mutableStateOf(Person("", "", "")) }
//        }
//
//
////    val showingDialog = remember { mutableStateOf(false) }
//    val org = person.value
//
//    if (showingDialog.value) {
//        AlertDialog(
//            onDismissRequest = {
//                person.value = org
//                showingDialog.value = false
//            },
//            title = {
//                Text(title)
//            },
//            buttons = {
//                Row {
//                    TextButton(
//                        onClick = {
//                            Log.i("zeta", org.toString())
//                            Log.i("zeta", person.value.toString())
//                            person.value = org
//                            showingDialog.value = false
//                        },
//                        modifier = Modifier
//                            .padding(16.dp)
//                    ) {
//                        Text("取消")
//                    }
//                    TextButton(
//                        onClick = {
//                            showingDialog.value = false
//                            if (title == "修改") {
//                                GlobalScope.launch(Dispatchers.Default) {
//                                    coroutineScope {
//                                        StaticObj.personDataBase.PersonDataDao()
//                                            .update(person.value)
//                                        Log.i("zeta", "update $person.toString()")
//                                    }
//                                }
//                            } else {
//                                GlobalScope.launch(Dispatchers.Default) {
//                                    coroutineScope {
//                                        StaticObj.personDataBase.PersonDataDao().add(Person(person.value))
//                                        Log.i("zeta", "add $person.toString()")
//                                    }
//                                }
//                            }
//                        },
//                        modifier = Modifier
//                            .padding(16.dp)
//                    ) {
//                        Text("确定")
//                    }
//                }
//            },
//            text = {
//                Column {
//                    Text(text = "姓名:", fontSize = 30.sp)
//                    TextField(
//                        modifier = Modifier
//                            .height(50.dp)
//                            .fillMaxWidth(),
//                        value = person.value.name,
//                        onValueChange = { newText ->
//                            person.value = Person(
//                                newText.trim().replace(Regex("\\s+"), " "),
//                                person.value.telephone, person.value.email, person.value.img
//                            )
//                        }
//                    )
//                    Text(text = "电话号码:", fontSize = 30.sp)
//                    TextField(
//                        modifier = Modifier
//                            .height(50.dp)
//                            .fillMaxWidth(),
//                        value = person.value.telephone,
//                        onValueChange = { newText ->
//                            person.value = Person(
//                                person.value.name,
//                                newText.trim().replace(Regex("\\s+"), " "),
//                                person.value.email, person.value.img
//                            )
//                        }
//                    )
//                    Text(text = "邮箱:", fontSize = 30.sp)
//                    TextField(
//                        modifier = Modifier
//                            .height(50.dp)
//                            .fillMaxWidth(),
//                        value = person.value.email,
//                        onValueChange = { newText ->
//                            person.value = Person(
//                                person.value.name, person.value.telephone,
//                                newText.trim().replace(Regex("\\s+"), " "),
//                                person.value.img
//                            )
//                        }
//                    )
//                }
//                Log.i("zeta", org.toString())
//                Log.i("zeta", person.value.toString())
//            }
//        )
//    }
//    return person
//}
//
//
//@Composable
//fun PersonCard(person : MutableState<Person>, index : Int, select : MutableState<Int>) {
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(if (index == select.value) Color.LightGray else Color.White)
//            .clickable {
//                select.value = if (select.value != index) index else -1
//            }
//    ) {
//        Row() {
//            Image(
//                person.value.img, "img",
//                modifier = Modifier
//                    .height(itemsHeight / 2)
//                    .width(itemsHeight / 2)
//            )
//            Text(text = person.value.name, fontSize = (itemsHeight * 3 / 8).value.sp)
//        }
//        Row() {
//            Text(text = "tel: ${person.value.telephone} ")
//            Text(text = "email: ${person.value.email} ")
//        }
//        Divider(
//            color = Color.Black,
//            thickness = 1.dp,
//        )
//    }
//}
//
//
//fun createPersonObj() : SnapshotStateList<MutableState<Person>> {
//
//    val ret : SnapshotStateList<MutableState<Person>> = SnapshotStateList()
//    for (i in 1..5) {
//        ret.add(mutableStateOf(Person()))
//    }
//    return ret
//}
//
//@Composable
//fun createPersonObj(list : List<Person>) : SnapshotStateList<MutableState<Person>> {
////    StaticObj.initDatabase = true
////    if (StaticObj.initDatabase == false and list.isNotEmpty()) {
////        StaticObj.initDatabase = true
////        StaticObj.initDatabaseList = list
////    } else if (StaticObj.initDatabase) {
////        val ret : SnapshotStateList<MutableState<Person>> = SnapshotStateList()
////        for (i in StaticObj.initDatabaseList) {
////            ret.add(remember {
////                mutableStateOf(i)
////            })
////        }
////        return ret
////    }
//    val ret : SnapshotStateList<MutableState<Person>> = SnapshotStateList()
//    for (i in list) {
//        ret.add(remember {
//            mutableStateOf(i)
//        })
//    }
//    ret.add(remember {
//        mutableStateOf(Person())
//    })
////    Log.i("zeta", ret.size.toString())
//    return ret
//}
