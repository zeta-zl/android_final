package com.zl.version10.utils

import android.Manifest
import android.app.Activity
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.telephony.SmsManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.zl.testhelper.TextStorage.StringStorageManager.getEmailContent
import com.zl.testhelper.TextStorage.StringStorageManager.getMessageContent
import com.zl.version10.database.SelectTime
import com.zl.version10.utils.receiver.ReminderReceiver
import com.zl.version10.utils.receiver.TextReceiver
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.*
import kotlin.collections.ArrayList

const val PERMISSION_REQUEST_CODE = 1
fun getCurrentDate() : String {
    return LocalDate.now().toString()
}

private fun getNextTriggerTime(selectTime : SelectTime) : Calendar {
    val triggerTime = Calendar.getInstance()
    triggerTime.set(
        Calendar.HOUR_OF_DAY,
        selectTime.hour + selectTime.noon * 12
    )
    triggerTime.set(Calendar.MINUTE, selectTime.minute)
    triggerTime.set(Calendar.SECOND, 0)
    return triggerTime
}

fun setItem(context : Context, selectTime : SelectTime) {
    if (!selectTime.enabled) {
        return
    }
    if (selectTime.important) {
        setAlarm(context, selectTime)
        setReminder(context, selectTime)
//        cancelAlarm(context, selectTime)
    } else {
        setReminder(context, selectTime)
        cancelAlarm(context, selectTime)
    }
}

fun setAlarm(context : Context, selectTime : SelectTime) {
    val id = selectTime.id
    val intent = Intent(context, TextReceiver::class.java)
    intent.putExtra("id", id)
    val pendingIntent =
        PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

    val telephoneList = StaticObj
        .personDataBase
        .PersonDataDao()
        .getAll()
        .map {
            it.telephone
        }
    val emailList = StaticObj
        .personDataBase
        .PersonDataDao()
        .getAll()
        .map {
            it.email
        }

    val triggerTime = getNextTriggerTime(selectTime)

    val alarmManager = context
        .getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val alarmIntent = Intent(context, TextReceiver::class.java).apply {
        putExtra("id", id)
        putStringArrayListExtra("telephoneList", ArrayList(telephoneList))
        putStringArrayListExtra("emailList", ArrayList(emailList))
        putExtra("MessageText", getMessageContent(context))
        putExtra("EmailText", getEmailContent(context))
    }
    val pendingAlarmIntent = PendingIntent.getBroadcast(
        context,
        id,
        alarmIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    Log.i(
        "zeta", SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss",
            Locale.getDefault()
        ).format(triggerTime.time)
    )

    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        triggerTime.timeInMillis,
        AlarmManager.INTERVAL_DAY,
        pendingAlarmIntent
    )
}

fun setReminder(context : Context, selectTime : SelectTime) {
    val id = selectTime.id

    val intent = Intent(context, ReminderReceiver::class.java)
    intent.putExtra("id", id)
    val pendingIntent = PendingIntent.getBroadcast(
        context, id, intent, PendingIntent.FLAG_NO_CREATE
    )
    if (pendingIntent != null) {
        return
    }

    val triggerTime = getNextTriggerTime(selectTime)

    val alarmIntent = Intent(context, ReminderReceiver::class.java)
    alarmIntent.putExtra("id", id)
    val pendingAlarmIntent = PendingIntent.getBroadcast(
        context, id, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT
    )

    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.setExact(
        AlarmManager.RTC_WAKEUP,
        triggerTime.timeInMillis,
        pendingAlarmIntent
    )
}

fun sendMessage(telephone : String, content : String?) {
    if (content.isNullOrEmpty()) {
        return
    }
    val smsManager = SmsManager.getDefault()
    smsManager.sendTextMessage(
        telephone,
        null, content, null, null
    )
}

fun sendEmail(email : String, content : String?) {
    // 发送邮件操作
}

fun testSendMessages() {
    sendMessage(
        "10086",
        "test for automatically send"
    )
}


fun sendMessagesAndEmails(context : Context) {
    val personList = StaticObj.personDataBase.PersonDataDao().getAll()

    for (person in personList) {
        val messageContent = getMessageContent(context)
        val emailContent = getEmailContent(context)

        sendMessage(person.telephone, messageContent)
        sendEmail(person.email, emailContent)
    }
}

fun showConfirmationDialog(activity : Activity, onConfirm : () -> Unit) {
    val builder = AlertDialog.Builder(activity)
    builder.apply {
        setTitle("确认操作")
        setMessage("为了在不经过确认的情况下发送短信，我们需要获取短信发送的权限。\n我们将向10086发送一条短信，作为测试，此过程中不会产生费用")
        setPositiveButton("确定") { _, _ ->
            onConfirm()
        }
        setNegativeButton("取消", null)
    }
    val dialog = builder.create()
    dialog.show()
}


fun registerForRequestPermissionLauncher(activity : ComponentActivity) {
    val requestPermissionLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission(

        )
    ) { isGranted : Boolean ->
        if (isGranted) {
            testSendMessages()
//            isPermissionGranted = true
            // Permission is granted. Continue the action or workflow in your app.
        } else {
            Log.i("zeta", "request permission failed")
            // Explain to the user that the feature is unavailable because the
            // feature requires a permission that the user has denied. At the
            // same time, respect the user's decision. Don't link to system
            // settings in an effort to convince the user to change their
            // decision.
        }
    }
    StaticObj.requestPermissionLauncher = requestPermissionLauncher
}

fun requestPermissionsAndSendMessagesAndEmails() {
    val permission = Manifest.permission.SEND_SMS
    StaticObj.requestPermissionLauncher.launch(permission)
}

fun requestPermissionsAndSendMessagesAndEmails(activity : ComponentActivity) : Boolean {
    val permission = Manifest.permission.SEND_SMS
    var isPermissionGranted = false

    val requestPermissionLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission(

        )
    ) { isGranted : Boolean ->
        if (isGranted) {
            testSendMessages()
            isPermissionGranted = true
            // Permission is granted. Continue the action or workflow in your app.
        } else {
            Log.i("zeta", "request permission failed")
            // Explain to the user that the feature is unavailable because the
            // feature requires a permission that the user has denied. At the
            // same time, respect the user's decision. Don't link to system
            // settings in an effort to convince the user to change their
            // decision.
        }
    }

//    requestPermissionLauncher.launch(permission)
    return isPermissionGranted
}



fun TestSend(context : Context) {
    val currentDate = getCurrentDate()
    ButtonStateManager.saveState(context, currentDate)
//    Log.i("zeta", ButtonStateManager.getButtonClickedDate(context).toString())
    val currentTime = LocalTime.now()
    var triggerTime = currentTime.plusMinutes(1)
//                        requestPermissionsAndSendMessagesAndEmails(context as Activity)
    setAlarm(
        context, SelectTime(
            noon = if (currentTime.hour < 12) 0 else 1,
            hour = triggerTime.hour % 12,
            minute = triggerTime.minute,
            id = 1
        )
    )
}

fun cancelAlarm(context : Context, selectTime : SelectTime) {
    val id = selectTime.id
    val intent = Intent(context, TextReceiver::class.java)
    intent.putExtra("id", id)
    val pendingIntent = PendingIntent.getBroadcast(
        context, id, intent, PendingIntent.FLAG_NO_CREATE
    )

    if (pendingIntent != null) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun uploadDataBase() {
    Log.i("zeta", "uploadDataBase")
    GlobalScope.launch(Dispatchers.Default) {
        coroutineScope {
            StaticObj.initTimeDatabaseList = StaticObj.TimeDataBase.TimeDataDao().getAll()
        }
    }
    GlobalScope.launch(Dispatchers.Default) {
        coroutineScope {
            StaticObj.initPersonDatabaseList = StaticObj.personDataBase.PersonDataDao().getAll()
        }
    }
}