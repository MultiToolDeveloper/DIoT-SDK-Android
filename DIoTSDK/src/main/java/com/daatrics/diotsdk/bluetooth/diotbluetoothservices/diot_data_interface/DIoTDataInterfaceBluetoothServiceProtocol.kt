package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_data_interface

interface DIoTDataInterfaceBluetoothServiceProtocol {

    fun fetchData(channelNumber: Int)
    fun notifyData(channelNumber: Int, enable: Boolean)

    fun subscribe(subscriber: DIoTDataInterfaceBluetoothServiceDelegate)
    fun unsubscribe(subscriber: DIoTDataInterfaceBluetoothServiceDelegate)
}