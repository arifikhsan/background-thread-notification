package com.example.android.foregroundservicebackgroundthread

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startService(view: View) {
        val input = edit_text_input.text.toString()

        val serviceIntent = Intent(this, ExampleService::class.java)
        serviceIntent.putExtra("inputExtra", input)

        ContextCompat.startForegroundService(this, serviceIntent)
    }

    fun stopService(view: View) {
        val serviceIntent = Intent(this, ExampleService::class.java)
        stopService(serviceIntent)
    }


}
