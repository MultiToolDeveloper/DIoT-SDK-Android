package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothmanager

import android.content.Context
import com.daatrics.diotdemoapp.diotsdk.bluetooth.dispatcher.DIoTAndroidBleDispatcher
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.general.GeneralBluetoothDeviceProtocol
import java.util.*

interface DIoTBluetoothManagerProtocol {
    val context: Context
    val bleDispatcher: DIoTAndroidBleDispatcher

    fun subscribe(subscriber: DIoTBluetoothManagerDelegate, subscriptionType: DIoTBluetoothManagerSubscriptionType)
    fun unsubscribe(subscriber: DIoTBluetoothManagerDelegate, subscriptionType: DIoTBluetoothManagerSubscriptionType)

    fun fetchBluetoothPowerState()

    fun startScan(service: UUID?, name: String?)
    fun stopScan()

    fun connect(generalDevice: GeneralBluetoothDeviceProtocol)
    fun disconnect(generalDevice: GeneralBluetoothDeviceProtocol)
}
