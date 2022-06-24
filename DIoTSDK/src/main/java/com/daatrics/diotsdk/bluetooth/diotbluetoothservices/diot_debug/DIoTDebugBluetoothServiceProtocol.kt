package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_debug

interface DIoTDebugBluetoothServiceProtocol {

    fun notifyDebug(enable: Boolean)
    fun sendDebug(command: String)

    fun subscribe(subscriber: DIoTDebugBluetoothServiceDelegate)
    fun unsubscribe(subscriber: DIoTDebugBluetoothServiceDelegate)
}