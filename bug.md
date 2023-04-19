### 在这里会记录一些遇到的bug和解决方案，都是compose相关的，标题作为关键词

#### LazyRow
在使用LazyRow时，遵循：  
`LazyRow(state = scrollState) {    
                items(item) {}}`  
的形式，此处items如果是一个list，需要  
`import androidx.compose.foundation.lazy.items`