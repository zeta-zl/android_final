package com.zl.testhelper

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.zl.testhelper.MainPage.MainActivity


class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context : Context, intent : Intent) {
        val id = intent.getIntExtra("id", -1)


        // 创建通知
        val notification = createNotification(context)

        // 发送通知
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(id, notification)
    }

    private fun createNotification(context : Context) : Notification {
        // 构建通知内容
//        val contentText = "提醒内容：" + selectTime.id.toString() // 根据实际需求构建提醒内容
        val contentText = "请确认"
        // 创建通知渠道（如果需要）
        createNotificationChannel(context)

        // 创建通知
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_gps_fixed_24)
            .setContentTitle("Reminder")
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        // 设置点击通知时的行为（如果需要）
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        notificationBuilder.setContentIntent(pendingIntent)

        return notificationBuilder.build()
    }

    private fun createNotificationChannel(context : Context) {
        // 创建通知渠道（适用于 Android 8.0 及以上版本）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "提醒渠道名称",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "提醒渠道描述"

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "reminder_channel"
    }
}
