package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.general_devece_information

interface GeneralDeviceInformationBluetoothServiceProtocol {

    fun fetchFirmwareVersion()
    fun fetchSoftwareVersion()
    fun fetchHardwareVersion()
    fun fetchManufactureName()
    fun fetchModelNumber()

    fun subscribe(subscriber: GeneralDeviceInformationBluetoothServiceDelegate)
    fun unsubscribe(subscriber: GeneralDeviceInformationBluetoothServiceDelegate)
}