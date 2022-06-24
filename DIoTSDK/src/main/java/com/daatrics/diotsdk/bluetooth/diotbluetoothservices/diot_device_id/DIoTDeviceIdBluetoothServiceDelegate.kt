package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_device_id

interface DIoTDeviceIdBluetoothServiceDelegate {
    fun didReceiveChipIdentifier(service: DIoTDeviceIdBluetoothServiceProtocol,  chipIdentifier: String)
    fun didReceiveError(service: DIoTDeviceIdBluetoothServiceProtocol,  error: DIoTDeviceIdBluetoothServiceError)
}