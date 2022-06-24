package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.general_devece_information

import android.bluetooth.BluetoothGattCharacteristic
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.general.GeneralBluetoothDevice
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.service.BluetoothService
import com.daatrics.diotdemoapp.diotsdk.support.diotextensions.toText
import com.daatrics.diotdemoapp.diotsdk.support.diothashtable.DIoTHashTable


class GeneralDeviceInformationBluetoothService(
    generalDevice: GeneralBluetoothDevice
) : BluetoothService(generalDevice),
    GeneralDeviceInformationBluetoothServiceProtocol{

    private var subscribers = DIoTHashTable()

    override fun handleDidUpdateValue(characteristic: BluetoothGattCharacteristic) {
        if (!GeneralDeviceInformationBluetoothServiceCharacteristics.contents(characteristic.uuid))
            return
        handleUpdateValue(characteristic)
    }

    override fun handleDidWriteValue(bluetoothCharacteristic: BluetoothGattCharacteristic) {
        // No need
    }

    override fun handleDidUpdateNotificationState(bluetoothCharacteristic: BluetoothGattCharacteristic) {
        // No need
    }

    //GeneralDeviceInformationServiceProtocol
    override fun fetchFirmwareVersion() {
        readValue(GeneralDeviceInformationBluetoothServiceCharacteristics.firmwareRevision.uuid)
    }

    override fun fetchSoftwareVersion() {
        readValue(GeneralDeviceInformationBluetoothServiceCharacteristics.softwareRevision.uuid)
    }

    override fun fetchHardwareVersion() {
        readValue(GeneralDeviceInformationBluetoothServiceCharacteristics.hardwareRevision.uuid)
    }

    override fun fetchManufactureName() {
        readValue(GeneralDeviceInformationBluetoothServiceCharacteristics.manufacturerName.uuid)
    }

    override fun fetchModelNumber() {
        readValue(GeneralDeviceInformationBluetoothServiceCharacteristics.modelNumber.uuid)
    }

    override fun subscribe(subscriber: GeneralDeviceInformationBluetoothServiceDelegate) {
        if (!(subscribers.values.contains(subscriber)))
            subscribers.values.add(subscriber)
    }

    override fun unsubscribe(subscriber: GeneralDeviceInformationBluetoothServiceDelegate) {
        if ((subscribers.values.contains(subscriber)))
            subscribers.values.remove(subscriber)
    }

    //Handle updating of characteristic
    fun handleUpdateValue(characteristic: BluetoothGattCharacteristic) {
        when (characteristic.uuid) {
            GeneralDeviceInformationBluetoothServiceCharacteristics.firmwareRevision.uuid -> handleUpdateFirmwareRevision(characteristic.value)
            GeneralDeviceInformationBluetoothServiceCharacteristics.softwareRevision.uuid -> handleUpdateSoftwareRevision(characteristic.value)
            GeneralDeviceInformationBluetoothServiceCharacteristics.hardwareRevision.uuid -> handleUpdateHardwareRevision(characteristic.value)
            GeneralDeviceInformationBluetoothServiceCharacteristics.manufacturerName.uuid -> handleUpdateManufactureName(characteristic.value)
            GeneralDeviceInformationBluetoothServiceCharacteristics.modelNumber.uuid -> handleUpdateModelNumber(characteristic.value)
        }
    }

    fun handleUpdateFirmwareRevision(data: ByteArray){
        for (subscriber in subscribers.values) {
            (subscriber as? GeneralDeviceInformationBluetoothServiceDelegate)?.let {
                subscriber.didReceiveFirmwareRevision(this, data.toText())
            }
        }
    }

    fun handleUpdateSoftwareRevision(data: ByteArray){
        for (subscriber in subscribers.values) {
            (subscriber as? GeneralDeviceInformationBluetoothServiceDelegate)?.let {
                subscriber.didReceiveSoftwareRevision(this, data.toText())
            }
        }
    }

    fun handleUpdateHardwareRevision(data: ByteArray){
        for (subscriber in subscribers.values) {
            (subscriber as? GeneralDeviceInformationBluetoothServiceDelegate)?.let {
                subscriber.didReceiveHardwareRevision(this, data.toText())
            }
        }
    }

    fun handleUpdateManufactureName(data: ByteArray){
        for (subscriber in subscribers.values) {
            (subscriber as? GeneralDeviceInformationBluetoothServiceDelegate)?.let {
                subscriber.didReceiveManufactureName(this, data.toText())
            }
        }
    }

    fun handleUpdateModelNumber(data: ByteArray){
        for (subscriber in subscribers.values) {
            (subscriber as? GeneralDeviceInformationBluetoothServiceDelegate)?.let {
                subscriber.didReceiveModelNumber(this, data.toText())
            }
        }
    }

}