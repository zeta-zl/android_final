### 在这里会记录一些遇到的bug和解决方案，都是compose相关的，标题作为关键词

#### LazyRow

在使用LazyRow时，遵循：  
`LazyRow(state = scrollState) {    
items(item) {}}`  
的形式，此处items如果是一个list，需要  
`import androidx.compose.foundation.lazy.items`

#### compose不刷新
 
考虑我们有一个类time：  
`data class time(  
var hour : Int = 0,
var minute : Int = 0)`

然后创建一个`test : MutableState<time>`  
在使用test时，如果像这样改变值：  
`test.value.hour = NEWVALUE`  
不会刷新compose，要等下次更新相关ui时，才会刷新

大概是因为test中的value，并没有改变，仍然是同一个对象  
解决方案有两个：  
1. 在下面加一行任意语句，使compose状态刷新(其实理论上是有强制刷新的语句的，我不信会没有设计，但我确实没找到)
2. 直接new一个对象： `test.value = time(NEWVALUE,test.value.minute)`
这两种方式都能使compose刷新