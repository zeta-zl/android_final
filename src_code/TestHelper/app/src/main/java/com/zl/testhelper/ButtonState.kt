package com.zl.testhelper

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import java.time.LocalDate


object ButtonStateManager {
    private const val PREF_NAME = "ButtonStatePref"
    private const val BUTTON_CLICKED_DATE_KEY = "button_clicked_date"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getButtonClickedDate(context: Context): String? {
        return getSharedPreferences(context).getString(BUTTON_CLICKED_DATE_KEY, null)
    }

    fun saveButtonClickedDate(context: Context, date: String) {
        getSharedPreferences(context).edit {
            putString(BUTTON_CLICKED_DATE_KEY, date)
        }
    }
}
// 判断按钮是否被点击
fun isButtonClicked(context: Context): Boolean {
    val clickedDate = ButtonStateManager.getButtonClickedDate(context)
    return !clickedDate.isNullOrEmpty()
}

// 判断储存的日期是否是今天
fun isStoredDateToday(context: Context): Boolean {
    val clickedDate = ButtonStateManager.getButtonClickedDate(context)
    return clickedDate == LocalDate.now().toString()
}

object HelpStateManager {
    private const val PREF_NAME = "HelpStatePref"
    private const val MAIN_HELP_KEY = "main_help"
    private const val LIST_HELP_KEY = "list_help"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun isMainHelpEnabled(context: Context): Boolean {
        return getSharedPreferences(context).getBoolean(MAIN_HELP_KEY, true)
    }

    fun isListHelpEnabled(context: Context): Boolean {
        return getSharedPreferences(context).getBoolean(LIST_HELP_KEY, true)
    }

    fun setMainHelpEnabled(context: Context, isEnabled: Boolean) {
        getSharedPreferences(context).edit {
            putBoolean(MAIN_HELP_KEY, isEnabled)
        }
    }

    fun setListHelpEnabled(context: Context, isEnabled: Boolean) {
        getSharedPreferences(context).edit {
            putBoolean(LIST_HELP_KEY, isEnabled)
        }
    }
}

