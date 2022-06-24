package com.daatrics.diotdemoapp

import android.app.Application
import com.daatrics.diotdemoapp.diotsdk.DIoT

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        //do init the iot sdk
        DIoT.initialize(this)
    }
}