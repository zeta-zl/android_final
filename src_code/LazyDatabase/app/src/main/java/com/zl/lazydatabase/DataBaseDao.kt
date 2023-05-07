package com.zl.lazydatabase

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlin.random.Random

@Entity(tableName = "testDatabase")
data class PersonData(
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    val info : String
)

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

@Entity(tableName = "PersonDatabase")
class Person(
    var name : String = "test",
    var telephone : String = (Random.nextLong((1 * 10e9).toLong(), (9 * 10e9).toLong())
        .toString()),
    var email : String = Random.nextInt((9 * 10e9).toInt()).toString() + "@qq.com",
    var img : ImageVector = Icons.Default.Person,

    @PrimaryKey(autoGenerate = false)
    var id : Int
) {
    constructor(
        p : Person,
        _id : Int? = null,
        _name : String? = null,
        _telephone : String? = null,
        _email : String? = null,
        _img : ImageVector? = null
    ) : this(id = p.id) {
        id = _id ?: p.id
        maxId = maxId.coerceAtLeast(id)
//        id = p.id + 1
        name = _name ?: p.name
        telephone = _telephone ?: p.telephone
        email = _email ?: p.email
        img = _img ?: p.img
    }


    override fun toString() : String {
        return "person id:$id name:$name telephone:$telephone email:$email"
    }

    companion object {
        var maxId : Int = 0
    }
}


@Dao
interface PersonDataDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(myData : Person)

    @Delete
    fun delete(myData : Person)

    @Update
    fun update(myData : Person)

    @Query("select * from PersonDatabase")
    fun getAll() : List<Person>

    @Query("select * from PersonDatabase where id=:id")
    fun findById(id : Int) : Flow<Person>
}