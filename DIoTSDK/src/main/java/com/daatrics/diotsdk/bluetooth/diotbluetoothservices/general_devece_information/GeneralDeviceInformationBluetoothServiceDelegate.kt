package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.general_devece_information

interface GeneralDeviceInformationBluetoothServiceDelegate {
    fun didReceiveFirmwareRevision(service: GeneralDeviceInformationBluetoothServiceProtocol, firmwareRevision: String)
    fun didReceiveHardwareRevision(service: GeneralDeviceInformationBluetoothServiceProtocol, hardwareRevision: String)
    fun didReceiveSoftwareRevision(service: GeneralDeviceInformationBluetoothServiceProtocol, softwareRevision: String)
    fun didReceiveManufactureName(service: GeneralDeviceInformationBluetoothServiceProtocol, manufactureName: String)
    fun didReceiveModelNumber(service: GeneralDeviceInformationBluetoothServiceProtocol, modelNumber: String)
}