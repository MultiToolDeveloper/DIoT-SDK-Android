package com.daatrics.diotdemoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.daatrics.diotdemoapp.activities.ScanningActivity

class LaunchScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_screen)

        //run next activity after delay
        Handler(Looper.getMainLooper()).postDelayed({
            val mainIntent = Intent(this@LaunchScreenActivity, ScanningActivity::class.java)
            this@LaunchScreenActivity.startActivity(mainIntent)
            this@LaunchScreenActivity.finish()
        }, 1000)
    }
}