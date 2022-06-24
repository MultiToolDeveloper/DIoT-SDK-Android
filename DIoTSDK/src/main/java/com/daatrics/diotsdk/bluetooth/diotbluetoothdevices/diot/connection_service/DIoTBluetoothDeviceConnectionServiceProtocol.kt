package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.diot

import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.general.DIoTBluetoothDeviceConnectionServiceDelegate

interface DIoTBluetoothDeviceConnectionServiceProtocol {

    fun connect()
    fun disconnect()
    fun readRSSI()

    fun subscribe(subscriber: DIoTBluetoothDeviceConnectionServiceDelegate)
    fun unsubscribe(subscriber: DIoTBluetoothDeviceConnectionServiceDelegate)
}