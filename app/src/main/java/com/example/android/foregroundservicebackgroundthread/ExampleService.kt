package com.example.android.foregroundservicebackgroundthread

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.example.android.foregroundservicebackgroundthread.App.Companion.CHANNEL_ID


/**
 * Created by Arif Ikhsanudin on Tuesday, 04 December 2018.
 */

class ExampleService : Service() {

    companion object {
        val TAG = ExampleService::class.java.simpleName
        var STOP_THREAD = false
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val input = intent.getStringExtra("inputExtra")

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Example Service")
            .setContentText(input)
            .setSmallIcon(R.drawable.ic_android)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)

        //do heavy work on a background thread
        STOP_THREAD = false
        heavyWork(60)
        //stopSelf();

        return Service.START_NOT_STICKY
    }

    override fun onDestroy() {
        STOP_THREAD = true
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }

    private fun heavyWork(seconds: Int) {
        ExampleThread(seconds).start()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    class ExampleThread(val seconds: Int) : Thread() {

        override fun run() {
            for (i in 0 until seconds) {
                if (STOP_THREAD) return
                Log.d(TAG, "startThread: $i")
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }

    }
}
