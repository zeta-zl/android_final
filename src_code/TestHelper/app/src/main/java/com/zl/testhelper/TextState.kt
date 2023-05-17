package com.zl.testhelper

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class TextState {
    object StringStorageManager {
        private const val PREF_NAME = "StringStoragePref"
        private const val EMAIL_CONTENT_KEY = "email_content"
        private const val MESSAGE_CONTENT_KEY = "message_content"

        private fun getSharedPreferences(context : Context) : SharedPreferences {
            return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }

        fun getEmailContent(context : Context) : String? {
            return getSharedPreferences(context).getString(EMAIL_CONTENT_KEY, null)
        }

        fun saveEmailContent(context : Context, content : String) {
            getSharedPreferences(context).edit {
                putString(EMAIL_CONTENT_KEY, content)
            }
        }

        fun getMessageContent(context : Context) : String? {
            return getSharedPreferences(context).getString(MESSAGE_CONTENT_KEY, null)
        }

        fun saveMessageContent(context : Context, content : String) {
            getSharedPreferences(context).edit {
                putString(MESSAGE_CONTENT_KEY, content)
            }
        }
    }
}
