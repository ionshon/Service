package com.inu.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var serviceIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        serviceIntent = Intent(this, MyService::class.java)
    }

    fun serviceStart(view: View) {
//        val intent = Intent(this, MyService::class.java)
        serviceIntent.action = MyService.ACTION_CREATE
        startService(intent)
    }

    /*
    fun serviceCommand() {
        serviceIntent.action = MyService.ACTION_DELETE
        startService(intent)
    } */

    fun serviceStop(view: View) {
     //   val intent = Intent(this, MyService::class.java)
        stopService(intent)
    }

    var myService:MyService? = null
    var isService = false
    var connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, iBinder: IBinder?) {
            isService = true
            val binder = iBinder as MyService.MyBinder
            myService = binder.getService()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isService = false
        }
    }
    fun serviceBind(view: View) {
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    fun serviceCommand() {
        myService?.create()
        myService?.delete()
    }

    fun serviceUnbind(view: View) {

    }

/* Foreground service */

    fun notiStart(view: View) {
        val intent = Intent(this, Foreground::class.java)
        ContextCompat.startForegroundService(this, intent)
    }

    fun notiStop(view: View) {
        val intent = Intent(this, Foreground::class.java)
        stopService(intent)
    }
}