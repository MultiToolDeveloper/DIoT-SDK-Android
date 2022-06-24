package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.general

interface GeneralBluetoothDeviceDelegate {
    fun didReceiveRSSI(device: GeneralBluetoothDeviceProtocol, rssi: Int)
    fun didConnect(device: GeneralBluetoothDeviceProtocol)
    fun didDisconnect(device: GeneralBluetoothDeviceProtocol)
    fun didFailToConnect(device: GeneralBluetoothDeviceProtocol)
    fun didReceiveError(device: GeneralBluetoothDeviceProtocol,  error: GeneralBluetoothDeviceError)
}