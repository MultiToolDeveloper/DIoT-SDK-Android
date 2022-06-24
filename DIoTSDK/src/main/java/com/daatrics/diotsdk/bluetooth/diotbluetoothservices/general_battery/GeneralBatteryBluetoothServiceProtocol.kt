package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.general_battery

interface GeneralBatteryBluetoothServiceProtocol {

    fun fetchBatteryLevel()
    fun notifyBatteryLevel(enabled: Boolean)

    fun subscribe(subscriber: GeneralBatteryBluetoothServiceDelegate)
    fun unsubscribe(subscriber: GeneralBatteryBluetoothServiceDelegate)
}