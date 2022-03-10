package com.google.mlkit.vision.demo.kotlin

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class VideoService : Service() {

    val TAG = "MyService"

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        ShowLog("onCreate")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        ShowLog("onStartCommand")

        val runable = Runnable {
            for (i in 1..10) {
                ShowLog("Service doing something." + i.toString())
                Thread.sleep(1000)
            }
            stopSelf()
        }

        val thread = Thread(runable)
        thread.start()


        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        ShowLog("onDestroy")
        super.onDestroy()
    }

    fun ShowLog(message: String) {
        Log.d(TAG, message)
    }
}