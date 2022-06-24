package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.general

import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.types.BluetoothServiceType

sealed class GeneralBluetoothDeviceError {
    data class serviceUnavaliable(val type: BluetoothServiceType): GeneralBluetoothDeviceError()
    data class cannotHandleService(val service: String): GeneralBluetoothDeviceError()
    data class cannotHandleServiceFromCaracteristic(val service: String): GeneralBluetoothDeviceError()
    class connectionError(): GeneralBluetoothDeviceError()
    data class other(val other: Error): GeneralBluetoothDeviceError()
}