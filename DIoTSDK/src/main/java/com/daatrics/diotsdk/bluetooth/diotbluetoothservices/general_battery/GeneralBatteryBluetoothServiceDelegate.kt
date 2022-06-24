package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.general_battery

interface GeneralBatteryBluetoothServiceDelegate {
    fun didReceiveLevel(service: GeneralBatteryBluetoothServiceProtocol,  level: Int)
    fun subscriptionStatusChange(service: GeneralBatteryBluetoothServiceProtocol, enabled: Boolean)
}