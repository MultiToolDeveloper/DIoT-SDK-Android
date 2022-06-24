package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_debug


interface DIoTDebugBluetoothServiceDelegate {
    fun didReceiveLogs(service: DIoTDebugBluetoothServiceProtocol, logs: String)
    fun subscriptionStatusChange(service: DIoTDebugBluetoothServiceProtocol, enabled: Boolean)
}