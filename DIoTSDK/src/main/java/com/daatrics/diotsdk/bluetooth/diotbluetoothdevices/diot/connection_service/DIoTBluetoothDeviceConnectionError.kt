package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.general

import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.types.BluetoothServiceType

sealed class DIoTBluetoothDeviceConnectionError {
    data class serviceUnavaliable(val type: BluetoothServiceType): DIoTBluetoothDeviceConnectionError()
    data class cannotHandleService(val service: String): DIoTBluetoothDeviceConnectionError()
    data class cannotHandleServiceFromCaracteristic(val service: String): DIoTBluetoothDeviceConnectionError()
    class connectionError(): DIoTBluetoothDeviceConnectionError()
    data class other(val other: Error): DIoTBluetoothDeviceConnectionError()
}