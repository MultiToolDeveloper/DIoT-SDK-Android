package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_command_interface

import com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.command_interface.DIoTChannelData
import com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.command_interface.DIoTRateData
import com.daatrics.diotdemoapp.diotsdk.data.diotfeature.DIoTFeatureData

interface DIoTCommandInterfaceBluetoothServiceDelegate {
    fun didReceiveCommandFeatures(service: DIoTCommandInterfaceBluetoothServiceProtocol, dataFeatures: ArrayList<DIoTFeatureData>)
    fun didReceiveCommandChannels(service: DIoTCommandInterfaceBluetoothServiceProtocol, dataChannels: ArrayList<DIoTChannelData>)
    fun didReceiveCommandRate(service: DIoTCommandInterfaceBluetoothServiceProtocol, dataRates: ArrayList<DIoTRateData>)
    fun didReceiveError(service: DIoTCommandInterfaceBluetoothServiceProtocol,  error: DIoTCommandInterfaceBluetoothServiceError)

    fun didWriteCommandFeatures(service: DIoTCommandInterfaceBluetoothServiceProtocol)
    fun didWriteCommandChannels(service: DIoTCommandInterfaceBluetoothServiceProtocol)
    fun didWriteCommandRate(service: DIoTCommandInterfaceBluetoothServiceProtocol)

    fun subscriptionFeaturesStatusChange(service: DIoTCommandInterfaceBluetoothServiceProtocol,  enabled: Boolean)
    fun subscriptionChannelsStatusChange(service: DIoTCommandInterfaceBluetoothServiceProtocol,  enabled: Boolean)
    fun subscriptionRatesStatusChange(service: DIoTCommandInterfaceBluetoothServiceProtocol,  enabled: Boolean)
}