package com.zl.timeselect

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow


class DetailTypeConverters {
    @TypeConverter
    fun stringToObject(value : String?) : ImageVector? {
        val it = object : TypeToken<ImageVector>() {}.type
//        if (value != null) {
//            return Gson().fromJson(value, it)
//        } else {
//            return Icons.Default.Person
//        }
        return try {
            Gson().fromJson(value, it)
        } catch (e : java.lang.Exception) {
            Icons.Default.Person
        }


    }

    @TypeConverter
    fun objectToString(value : ImageVector?) : String? {
        val gson = Gson()
        return gson.toJson(value)
    }
}


@Entity(tableName = "TimeDatabase")
class SelectTime(
    var noon : Int = 0,
    var hour : Int = 0,
    var minute : Int = 0,
    var enabled : Boolean = true,
    var important : Boolean = true,
    @PrimaryKey(autoGenerate = false)
    var id : Int

) {
    constructor(p : SelectTime) : this(id = p.id) {
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
        _important : Boolean? = null,
        _id : Int? = null
    ) : this(id = p.id) {

        id = _id ?: p.id
        maxId = maxId.coerceAtLeast(id)
        noon = _noon ?: p.noon
        hour = _hour ?: p.hour
        minute = _minute ?: p.minute
        enabled = _enabled ?: p.enabled
        important = _important ?: p.important
    }

    override fun toString() : String {
        return "time id:$id noon:$noon hour:$hour minute:$minute"
    }

    companion object {
        var maxId : Int = 0
    }
}

//class Person(
//    var name : String = "test",
//    var telephone : String = (Random.nextLong((1 * 10e9).toLong(), (9 * 10e9).toLong())
//        .toString()),
//    var email : String = Random.nextInt((9 * 10e9).toInt()).toString() + "@qq.com",
//    var img : ImageVector = Icons.Default.Person,
//
//    @PrimaryKey(autoGenerate = false)
//    var id : Int
//) {
//    constructor(
//        p : Person,
//        _id : Int? = null,
//        _name : String? = null,
//        _telephone : String? = null,
//        _email : String? = null,
//        _img : ImageVector? = null
//    ) : this(id = p.id) {
//        id = _id ?: p.id
//        maxId = maxId.coerceAtLeast(id)
////        id = p.id + 1
//        name = _name ?: p.name
//        telephone = _telephone ?: p.telephone
//        email = _email ?: p.email
//        img = _img ?: p.img
//    }
//
//
//    override fun toString() : String {
//        return "person id:$id name:$name telephone:$telephone email:$email"
//    }
//
//    companion object {
//        var maxId : Int = 0
//    }
//}


@Dao
interface TimeDataDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(myData : SelectTime)

    @Delete
    fun delete(myData : SelectTime)

    @Update
    fun update(myData : SelectTime)

    @Query("select * from TimeDatabase")
    fun getAll() : List<SelectTime>

    @Query("select * from TimeDatabase where id=:id")
    fun findById(id : Int) : Flow<SelectTime>
}