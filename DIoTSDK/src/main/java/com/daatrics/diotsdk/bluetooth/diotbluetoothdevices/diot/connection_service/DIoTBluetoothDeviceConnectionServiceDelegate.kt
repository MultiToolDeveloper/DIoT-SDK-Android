package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.general

import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.diot.DIoTBluetoothDeviceConnectionServiceProtocol

interface DIoTBluetoothDeviceConnectionServiceDelegate {
    fun didConnect(service: DIoTBluetoothDeviceConnectionServiceProtocol)
    fun didDisconnect(service: DIoTBluetoothDeviceConnectionServiceProtocol)
    fun didFailToConnect(service: DIoTBluetoothDeviceConnectionServiceProtocol)
    fun didReceiveError(service: DIoTBluetoothDeviceConnectionServiceProtocol,  error: DIoTBluetoothDeviceConnectionError)
    fun didReceiveRSSI(service: DIoTBluetoothDeviceConnectionServiceProtocol, rssi: Int)
}
