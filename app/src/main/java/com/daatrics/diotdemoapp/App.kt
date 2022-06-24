package com.daatrics.diotdemoapp

import android.app.Application
import com.daatrics.diotdemoapp.diotsdk.bluetooth.DIoTSDK

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        //do init the iot sdk
        DIoTSDK.initialize(this)
    }
}