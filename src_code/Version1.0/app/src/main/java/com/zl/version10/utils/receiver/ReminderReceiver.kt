package com.zl.version10.utils.receiver

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.zl.version10.MainActivity
import com.zl.version10.R


class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context : Context, intent : Intent) {
        val id = intent.getIntExtra("id", -1)

        val notification = createNotification(context)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(id, notification)
    }

    private fun createNotification(context : Context) : Notification {
        val contentText = "请确认"
        createNotificationChannel(context)

        // 创建通知
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_gps_fixed_24)
            .setContentTitle("Reminder")
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)


        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        notificationBuilder.setContentIntent(pendingIntent)

        return notificationBuilder.build()
    }

    private fun createNotificationChannel(context : Context) {

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
