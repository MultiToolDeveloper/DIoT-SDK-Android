package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_data_interface

import com.daatrics.diotdemoapp.diotsdk.data.diotfeature.DIoTFeatureData


interface DIoTDataInterfaceBluetoothServiceDelegate {
    fun didReceiveDataChannel(service: DIoTDataInterfaceBluetoothServiceProtocol, dataFeatures: ArrayList<DIoTFeatureData>, channelNumber: Int)
    fun didReceiveError(service: DIoTDataInterfaceBluetoothServiceProtocol,  error: DIoTDataInterfaceBluetoothServiceError)
    fun subscriptionDataChannelStatusChange(service: DIoTDataInterfaceBluetoothServiceProtocol, enabled: Boolean, channelNumber: Int)

}