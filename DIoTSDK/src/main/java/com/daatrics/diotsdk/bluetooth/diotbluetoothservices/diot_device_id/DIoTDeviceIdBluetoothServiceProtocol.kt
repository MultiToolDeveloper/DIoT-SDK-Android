package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_device_id

interface DIoTDeviceIdBluetoothServiceProtocol {

    fun fetchChipIdentier()

    fun subscribe(subscriber: DIoTDeviceIdBluetoothServiceDelegate)
    fun unsubscribe(subscriber: DIoTDeviceIdBluetoothServiceDelegate)
}