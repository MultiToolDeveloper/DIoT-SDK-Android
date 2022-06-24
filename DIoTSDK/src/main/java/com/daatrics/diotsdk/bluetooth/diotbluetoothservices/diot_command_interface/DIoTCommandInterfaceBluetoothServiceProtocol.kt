package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_command_interface

import com.daatrics.diotdemoapp.diotsdk.data.diotfeature.DIoTFeatureCode
import com.daatrics.diotdemoapp.diotsdk.data.diotfeature.DIoTFeatureData

interface DIoTCommandInterfaceBluetoothServiceProtocol {

    fun fetchFeatures()
    fun fetchChannels()
    fun fetchRates()
    fun fetchFeature(code: DIoTFeatureCode)
    fun fetchChannel(channel: Int)
    fun fetchRate(channel: Int)
    fun notifyFeatures(enable: Boolean)
    fun notifyChannels(enable: Boolean)
    fun notifyRates(enable: Boolean)

    fun setFeature(feature: DIoTFeatureData)
    fun cleanFeature(code: DIoTFeatureCode)
    fun cleanFeatures()
    fun setChannel(channel: Int, code: DIoTFeatureCode)
    fun cleanChannel(channel: Int)
    fun cleanChannels()
    fun setRate(channel: Int, rate: Int)
    fun cleanRate(channel: Int)
    fun cleanRates()

    fun subscribe(subscriber: DIoTCommandInterfaceBluetoothServiceDelegate)
    fun unsubscribe(subscriber: DIoTCommandInterfaceBluetoothServiceDelegate)
}