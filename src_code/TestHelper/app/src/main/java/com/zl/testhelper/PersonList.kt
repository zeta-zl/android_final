package com.zl.testhelper

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.*


private val itemsHeight = 90.dp

//@Preview
@Composable
fun PersonList(navController : NavHostController) {
    val personData = remember {
        createPersonObj()
    }
    val selectItem = remember {
        mutableStateOf(-1)
    }

//    var temp = remember {
//        mutableStateOf(Person("", "", ""))
//    }
    val showingDialog = remember { mutableStateOf(false) }
    NewPersonDialog("添加", personData, personData.size - 1, showingDialog)
    val context = LocalContext.current
    val isHelpShowing = remember {
        mutableStateOf(true)
    }
    val helpProvider by remember {
        mutableStateOf(
            PersonListHelper()
        )
    }
    val helpTextList = listOf("帮助", "返回")
    var helpText by remember {
        mutableStateOf(
            1
        )
    }
    helpText = if (isHelpShowing.value) 1 else 0
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "PersonList",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center)//设置标题文本居中显示
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                        Toast.makeText(context, "返回", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        isHelpShowing.value = !isHelpShowing.value
                    }) {
                        Text(helpTextList[helpText])
                    }
                    IconButton(onClick = {
                        showingDialog.value = !showingDialog.value
                        personData
                            .add(mutableStateOf(Person("", "", "", id = Person.maxId + 1)))
//                        Toast.makeText(context, "添加", Toast.LENGTH_SHORT).show()
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
            if (personData.size > 0) {
                PersonLazyList(personData, selectItem)
//                helpProvider.Helper(isHelpShowing,personData.size)
            }
            if (personData.size == 2) {
                ContactsHelpStateManager
                    .saveState(
                        context,
                        false.toString()
                    )
            }
            if (ContactsHelpStateManager
                    .getState(context)
                    ?.toBooleanStrictOrNull() != false
            ) {
                helpProvider.Helper(isHelpShowing, personData.size)
            }
        }
    }

}


@OptIn(DelicateCoroutinesApi::class)
@Composable
fun PersonLazyList(
    personData : SnapshotStateList<MutableState<Person>>,
    select : MutableState<Int>
) {
    val scrollState = rememberLazyListState()
    LazyColumn(
        state = scrollState,
        verticalArrangement = Arrangement.Center

    ) {
        itemsIndexed(personData) { index, item ->
            Box {
                val showingDialog = remember { mutableStateOf(false) }
                NewPersonDialog("修改", personData, index, showingDialog)
                PersonCard(item, index, select)
                if (select.value == index) {
                    Button(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(0.dp, 16.dp, 12.dp, 2.dp)
                            .height(itemsHeight / 3)
//                        .border(5.dp, Color.Blue)
                        ,
                        contentPadding = (PaddingValues(1.dp)),
                        onClick = {
                            showingDialog.value = true
                            Log.i("zeta", "点击修改: ${iterableTOString(personData)}")
//                        personData[index] = p
                        }) {
                        Text(text = "修改")
                    }
                    Button(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(0.dp, 2.dp, 12.dp, 16.dp)
                            .height(itemsHeight / 3),
                        contentPadding = (PaddingValues(1.dp)),
                        onClick = {
                            GlobalScope.launch(Dispatchers.Default) {

                                runBlocking {
                                    Log.i("zeta", "del: ${iterableTOString(personData)}")
                                    Log.i("zeta", "select $index ")
                                    Log.i("zeta", "del ${personData[index]}")
                                    Log.i(
                                        "zeta", "database: ${
                                            StaticObj.personDataBase.PersonDataDao()
                                                .getAll()
                                        }"
                                    )
                                    coroutineScope {
                                        Log.i("zeta", index.toString())
                                        StaticObj.personDataBase.PersonDataDao()
                                            .delete(
                                                Person(
                                                    personData[index].value,
                                                    _id = personData[index].value.id
                                                )
                                            )
                                    }

                                    Log.i("zeta", "del: ${iterableTOString(personData)}")
                                    Log.i("zeta", "select $index ")
                                    Log.i("zeta", "del ${personData[index]}")
                                    Log.i(
                                        "zeta", "database: ${
                                            StaticObj.personDataBase.PersonDataDao()
                                                .getAll()
                                        }"
                                    )
                                    personData.removeAt(index)
                                }
                                Log.i("zeta", "del: ${iterableTOString(personData)}")
                                Log.i("zeta", "select $index ")
                                Log.i(
                                    "zeta", "database: ${
                                        StaticObj.personDataBase.PersonDataDao()
                                            .getAll()
                                    }"
                                )
                            }
                        }) {
                        Text(text = "删除")
                    }
                }
            }
        }
    }

}

fun iterableTOString(a : List<Any>) : String {
    val s = StringBuffer()
    for (i in a) {
        s.append(i).append('\n')
    }
    return s.toString()
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun NewPersonDialog(
    title : String,
//    person : MutableState<Person>,
    personData : SnapshotStateList<MutableState<Person>>,
    index : Int,
    showingDialog : MutableState<Boolean>
) {
//    Log.i("zeta", "newPersonDialog $title $index ")
    if (index < personData.size) {
//        val person : MutableState<Person> = personData[index]
//
////    val showingDialog = remember { mutableStateOf(false) }
//        val org = person.value

        if (showingDialog.value) {
            AlertDialog(
                onDismissRequest = {
//                    person.value = org
                    showingDialog.value = false
                },
                title = {
                    Text(title)
                },
                buttons = {
                    Row {
                        TextButton(
                            onClick = {
//                                Log.i("zeta", org.toString())
                                Log.i("zeta", personData[index].value.toString())
//                                person.value = org
                                showingDialog.value = false
                            },
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text("取消")
                        }
                        TextButton(
                            onClick = {
                                showingDialog.value = false
                                Log.i("zeta", "onClick ${personData[index].value}")
                                if (title == "添加") {

                                    GlobalScope.launch(Dispatchers.Default) {
                                        coroutineScope {
                                            val temp = Person(
                                                personData[index].value,
                                                _id = Person.maxId + 1
                                            )
                                            StaticObj.personDataBase.PersonDataDao()
                                                .add(personData[index].value)
                                        }
                                    }

                                } else {
                                    GlobalScope.launch(Dispatchers.Default) {
                                        coroutineScope {
//                                            val temp = Person(person.value, _id = person.value.id)
                                            StaticObj.personDataBase.PersonDataDao()
                                                .update(personData[index].value)
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text("确定")
                        }
                    }
                },
                text = {
                    val person = personData[index]
                    Column {
                        Text(text = "姓名:", fontSize = 30.sp)
                        TextField(
                            modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth(),
                            value = person.value.name,
                            onValueChange = { newText ->
                                person.value = Person(
                                    newText.trim().replace(Regex("\\s+"), " "),
                                    person.value.telephone, person.value.email, person.value.img,
                                    person.value.id
                                )
                            }
                        )
                        Text(text = "电话号码:", fontSize = 30.sp)
                        TextField(
                            modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth(),
                            value = person.value.telephone,
                            onValueChange = { newText ->
                                person.value = Person(
                                    person.value.name,
                                    newText.trim().replace(Regex("\\s+"), " "),
                                    person.value.email, person.value.img,
                                    person.value.id
                                )
                            }
                        )
                        Text(text = "邮箱:", fontSize = 30.sp)
                        TextField(
                            modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth(),
                            value = person.value.email,
                            onValueChange = { newText ->
                                person.value = Person(
                                    person.value.name, person.value.telephone,
                                    newText.trim().replace(Regex("\\s+"), " "),
                                    person.value.img,
                                    person.value.id
                                )
                            }
                        )
                    }
                }
            )
        }
    }
}


@Composable
fun PersonCard(person : MutableState<Person>, index : Int, select : MutableState<Int>) {
    val cardBackgroundColor =
        if (index == select.value)
            Color.LightGray
        else
            Color.White

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 12.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = cardBackgroundColor)
            .clickable {
                select.value =
                    if (select.value != index)
                        index
                    else
                        -1
            }) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                Image(
                    person.value.img,
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = person.value.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = "tel: ${person.value.telephone}",
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "email: ${person.value.email}",
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Divider(
            color = Color.Black,
            thickness = 1.dp,
//            modifier = Modifier.padding(vertical = 12.dp)
        )
    }
}


fun createPersonObj() : SnapshotStateList<MutableState<Person>> {
    val ret : SnapshotStateList<MutableState<Person>> = SnapshotStateList()
    for (i in StaticObj.initPersonDatabaseList) {
        Person.maxId = Person
            .maxId
            .coerceAtLeast(i.id)
        ret.add(
            mutableStateOf(i)
        )
    }
    Log.i("zeta", "onCreate: ${iterableTOString(ret)}")
    return ret
}



