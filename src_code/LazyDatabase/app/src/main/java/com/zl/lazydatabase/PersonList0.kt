//package com.zl.lazydatabase
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
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavHostController
//import kotlinx.coroutines.*
//import kotlinx.coroutines.flow.Flow
//
//
////val itemsHeight = 90.dp
//
////@Preview
//@Composable
//fun PersonList(navController : NavHostController) {
//    val tempFlow : Flow<List<Person>> = StaticObj.personDataBase.PersonDataDao().getAll()
//    val tempList by tempFlow.collectAsState(initial = listOf())
//    var personData = createPersonObj(tempList)
//    var selectItem = remember {
//        mutableStateOf(-1)
//    }
//
//    var temp = remember {
//        mutableStateOf(Person("", "", ""))
//    }
//    val showingDialog = remember { mutableStateOf(false) }
//    newPersonDialog("添加", personData, personData.size - 1, showingDialog)
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
//                        personData.add(mutableStateOf(Person("", "", "")))
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
//                            }
//                        }
//                        personData.removeAt(index)
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
//    val person : MutableState<Person> = try {
//        personData[index]
//    } catch (_ : Exception) {
//        remember { mutableStateOf(Person("", "", "")) }
//    }
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
//                            try {
//                                GlobalScope.launch(Dispatchers.Default) {
//                                    coroutineScope {
//                                        StaticObj.personDataBase.PersonDataDao()
//                                            .update(
//                                                Person(
//                                                    person.value,
//                                                    _id = person.value.id
//                                                )
//                                            )
//                                    }
//                                }
//                            } catch (_ : Exception) {
//                                GlobalScope.launch(Dispatchers.Default) {
//                                    coroutineScope {
//                                        StaticObj.personDataBase.PersonDataDao()
//                                            .add(person.value)
//                                    }
//                                }
//                            }
//
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
//                                newText,
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
//
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
//@Composable
//fun createPersonObj(
//    list : List<Person>,
//    org : SnapshotStateList<MutableState<Person>>
//) : SnapshotStateList<MutableState<Person>> {
//
//    if (org.size != 0) {
//        return org
//    }
//    val ret : SnapshotStateList<MutableState<Person>> = SnapshotStateList()
//    for (i in list) {
//        ret.add(remember {
//            mutableStateOf(i)
//        })
//    }
//    return ret
//}
//
//@Composable
//fun createPersonObj(list : List<Person>) : SnapshotStateList<MutableState<Person>> {
//    if (StaticObj.initDatabase){
//
//    }
//    val ret : SnapshotStateList<MutableState<Person>> = SnapshotStateList()
//    for (i in list) {
//        ret.add(remember {
//            mutableStateOf(i)
//        })
//    }
//    return ret
//}
//
//fun createPersonObj() : SnapshotStateList<MutableState<Person>> {
//    val ret : SnapshotStateList<MutableState<Person>> = SnapshotStateList()
//    for (i in 1..5) {
//        ret.add(mutableStateOf(Person()))
//    }
//    return ret
//}
//
//
