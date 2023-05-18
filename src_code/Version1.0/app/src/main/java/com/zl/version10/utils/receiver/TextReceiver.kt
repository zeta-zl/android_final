package com.zl.version10.utils.receiver

import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.zl.version10.utils.sendEmail
import com.zl.version10.utils.sendMessage

class TextReceiver : BroadcastReceiver() {
    override fun onReceive(context : Context, intent : Intent) {
        Log.i("zeta", "TextReceiver received")
        val id = intent.getIntExtra("id", -1)
        val telephoneList = intent.getStringArrayListExtra("telephoneList") ?: emptyList<String>()
        val emailList = intent.getStringArrayListExtra("emailList") ?: emptyList<String>()
        val messageText = intent.getStringExtra("MessageText") ?: ""
        val emailText = intent.getStringExtra("EmailText") ?: ""
        if (ContextCompat.checkSelfPermission(
                context as Activity,
                Manifest.permission.SEND_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        // 发送短信
        sendMessages(telephoneList, messageText)

        // 发送邮件
        sendEmails(emailList, emailText)
    }

    private fun sendMessages(phoneNumbers : List<String>, message : String) {

        sendMessage("10086", "test")
        // 发送短信操作
        // 请确保已经获取了发送短信的权限
        for (phoneNumber in phoneNumbers) {
            sendMessage(phoneNumber, message)
            // 发送短信给每个号码
            // 请实现发送短信的逻辑
        }
    }

    private fun sendEmails(emails : List<String>, message : String) {

        // 发送邮件操作
        // 请确保已经获取了发送邮件的权限
        for (email in emails) {
            sendEmail(email, message)
            // 发送邮件给每个邮箱
            // 请实现发送邮件的逻辑
        }
    }
}
