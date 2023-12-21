package com.alikn.logexplorer;

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class LogExplorerService : Service() {

    val name: CharSequence = "My Channel"
    val notificationDescription = "Exception logs"
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    val notificationId = 1
    val channel = NotificationChannel("log_explorer_channel_id", name, importance).apply {
        this.description = notificationDescription
    }

    override fun onCreate() {
        super.onCreate()
        val channelId = "my_service"
        val channelName = "My Background Service"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val chan =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_NONE)
            notificationManager.createNotificationChannel(chan)
        val notificationBuilder = Notification.Builder(this, channelId)
            .setContentTitle("Service is Running")
            .setContentText("Doing something in the background")
            .setSmallIcon(R.drawable.ic_dialog_alert)

        val notification = notificationBuilder.build()
        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        performRepeatingTask()

        val handler = Handler(Looper.getMainLooper())
        val runnable: Runnable = object : Runnable {
            override fun run() {
                performRepeatingTask()
                handler.postDelayed(this, 5000)
            }
        }

        return START_STICKY
    }

    private fun performRepeatingTask() {

        try {
            Runtime.getRuntime().exec("logcat -c")
            val process = Runtime.getRuntime().exec("logcat -d")
            val bufferedReader = BufferedReader(
                InputStreamReader(process.inputStream)
            )
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                line?.let { createNotificationChannel(it) }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun createNotificationChannel(log: String) {

        if(log.contains("Exception")){
            val notificationManager = getSystemService(
                NotificationManager::class.java
            ).apply {
                this.createNotificationChannel(channel)
            }
            val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, "log_explorer_channel_id")
                .setSmallIcon(R.drawable.ic_dialog_alert) // Replace with your own drawable resource
                .setContentTitle("Exception logs")
                .setContentText(log)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(NotificationCompat.BigTextStyle().bigText(log))

            val notificationBuilt =  builder.build()

            notificationManager.notify(notificationId, notificationBuilt)
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
