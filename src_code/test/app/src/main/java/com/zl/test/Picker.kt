import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 * 日期选择器;
 */
@Composable
fun DatePicker(
    noon : Int = 0,
    hour : Int = 0,
    minute : Int = 0,
    title : String = "选择日期",
    onDismiss : (selected : Boolean, noon : Int, hour : Int, minute : Int) -> Unit
) {
    var selectYear by remember { mutableStateOf(noon) }
    var selectHour by remember { mutableStateOf(hour) }
    val selectMinute = remember { mutableStateOf(minute) }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
    ) {
        Box(Modifier.fillMaxWidth()) {
            Text(
                text = title,
                fontSize = 30.sp,
                modifier = Modifier.align(Alignment.Center)
            )
//            endText = "确定",
            Text(text = "确定",
                fontSize = 30.sp,
                modifier = Modifier.align(Alignment.BottomEnd)
                .clickable {
                    onDismiss(true, selectYear, selectHour, selectMinute.value)
                })
        }
//            endClick = {
//
//            }
//            { onDismiss(false, 0, 0, 0) }
        DateWheel(selectYear, selectHour, selectMinute) { index, value ->
            when (index) {
                0 -> selectYear = value
                1 -> selectHour = value
                2 -> selectMinute.value = value
            }
        }
    }
}

/**
 * 时间选择器 - 睡眠 - (开始-结束时间)
 */
@Composable
private fun DateWheel(
    year : Int,
    month : Int,
    day : MutableState<Int>,
    onChange : (index : Int, value : Int) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        Alignment.Center
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            val modifier = Modifier.weight(1f)

            //  上午/下午
            ColumnPicker(
                modifier = modifier,
                value = year,
                label = {
                    val items = listOf("上午", "下午")
                    items[it]
                },
                range = 0..1,
                onValueChange = {
                    onChange(0, it)
                }
            )
            //  时
            ColumnPicker(
                modifier = modifier,
                value = month,
                label = { "${it}时" },
                range = 0..11,
                onValueChange = {
                    onChange(1, it)
                }
            )

            //  分
//            val lastDay = getLastDay(year, month)
//            if (day.value > lastDay) day.value = lastDay
            ColumnPicker(
                modifier = modifier,
                value = day.value,
                label = { "${it}分" },
                range = 0..59,
                onValueChange = {
                    onChange(2, it)
                }
            )
        }

        // 中间两道横线
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(38.dp)
                .align(Alignment.Center)
        ) {
            Divider(Modifier.padding(horizontal = 15.dp))
            Divider(
                Modifier
                    .padding(horizontal = 15.dp)
                    .align(Alignment.BottomStart)
            )
        }
    }
}

/**
 * 根据年月, 获取天数
 */
private fun getLastDay(year : Int, month : Int) : Int {
    return when (month) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        else -> {
            // 百年: 四百年一闰年;  否则: 四年一闰年;
            if (year % 100 == 0) {
                if (year % 400 == 0) {
                    29
                } else {
                    28
                }
            } else {
                if (year % 4 == 0) {
                    29
                } else {
                    28
                }
            }
        }
    }
}

@Preview
@Composable
private fun TimePreview() {
    DatePicker { selected, year, month, day ->
    }
}
