package com.zl.testhelper

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.zl.version10.utils.ButtonStateManager
import com.zl.version10.utils.StateManager
import java.time.LocalDate


interface StateManager {
    val preferencesName : String
    val stateKey : String
    private fun getSharedPreferences(context : Context) : SharedPreferences {
        return context.getSharedPreferences(this.preferencesName, Context.MODE_PRIVATE)
    }

    fun getState(context : Context) : String? {
        return this.getSharedPreferences(context)
            .getString(this.stateKey, null)
    }

    fun saveState(context : Context, content : String) {
        val key = this.stateKey
        this.getSharedPreferences(context).edit {
            putString(key, content)
        }
    }
}

object ButtonStateManager: StateManager {
    override val preferencesName : String
        get() = "ButtonStatePref"
    override val stateKey : String
        get() = "button_clicked_date"
    fun isStoredDateToday(context : Context) : Boolean {
        val clickedDate = ButtonStateManager.getState(context)
        return clickedDate == LocalDate.now().toString()
    }
}
object ContactsHelpStateManager: StateManager {
    override val preferencesName : String
        get() = "HelpStatePref"
    override val stateKey : String
        get() = "list_help"
}

object MainPageHelpStateManager: StateManager {
    override val preferencesName : String
        get() = "HelpStatePref"
    override val stateKey : String
        get() = "main_help"
}

