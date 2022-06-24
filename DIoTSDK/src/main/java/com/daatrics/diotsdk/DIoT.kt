package com.daatrics.diotdemoapp.diotsdk

import android.content.Context
import com.daatrics.diotdemoapp.diotsdk.bluetooth.dispatcher.DIoTAndroidBleDispatcher
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothmanager.*
import com.daatrics.diotdemoapp.diotsdk.support.diotlogger.LoggerService
import com.daatrics.diotdemoapp.diotsdk.support.diotlogger.interfaces.LoggerServiceProtocol

class DIoT() {

    companion object {
        lateinit var context: Context
        var isInitialised = false

        fun initialize(context: Context){

            if (isInitialised)
                return

            Companion.context = context

            isInitialised = true
        }

        private val loggerService: LoggerServiceProtocol by lazy {
            LoggerService(context)
        }

        val bluetoothManager: DIoTBluetoothManagerProtocol? by lazy {

            if (!isInitialised)
                return@lazy null

            val bleDispatcher = DIoTAndroidBleDispatcher (loggerService, null)
            val manager = DIoTBluetoothManager(context, bleDispatcher)
            manager.logger = loggerService
            return@lazy manager
        }
    }

    init { }
}