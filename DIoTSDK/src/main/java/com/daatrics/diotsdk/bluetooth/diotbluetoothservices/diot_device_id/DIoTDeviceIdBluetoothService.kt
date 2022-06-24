package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_device_id

import android.bluetooth.BluetoothGattCharacteristic
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.general.GeneralBluetoothDevice
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.service.BluetoothService
import com.daatrics.diotdemoapp.diotsdk.support.diotextensions.toHexString
import com.daatrics.diotdemoapp.diotsdk.support.diothashtable.DIoTHashTable


class DIoTDeviceIdBluetoothService(
    generalDevice: GeneralBluetoothDevice
) : BluetoothService(generalDevice),
    DIoTDeviceIdBluetoothServiceProtocol{

    private var subscribers = DIoTHashTable()

    override fun handleDidUpdateValue(characteristic: BluetoothGattCharacteristic) {
        if (!DIoTDeviceIdBluetoothServiceCharacteristics.contents(characteristic.uuid))
            return
        handleUpdateValue(characteristic)
    }

    override fun handleDidWriteValue(bluetoothCharacteristic: BluetoothGattCharacteristic) {
        // No need
    }

    override fun handleDidUpdateNotificationState(bluetoothCharacteristic: BluetoothGattCharacteristic) {
        // No need
    }

    //GeneraBatteryServiceProtocol
    override fun fetchChipIdentier() {
        readValue(DIoTDeviceIdBluetoothServiceCharacteristics.chipIdentifier.uuid)
    }

    override fun subscribe(subscriber: DIoTDeviceIdBluetoothServiceDelegate) {
        if (!(subscribers.values.contains(subscriber)))
            subscribers.values.add(subscriber)
    }

    override fun unsubscribe(subscriber: DIoTDeviceIdBluetoothServiceDelegate) {
        if ((subscribers.values.contains(subscriber)))
            subscribers.values.remove(subscriber)
    }

    //Handle updating of characteristic
    fun handleUpdateValue(characteristic: BluetoothGattCharacteristic) {
        when (characteristic.uuid) {
            DIoTDeviceIdBluetoothServiceCharacteristics.chipIdentifier.uuid -> handleUpdateIdentifier(characteristic.value)
        }
    }

    fun handleUpdateIdentifier(data: ByteArray){
        for (subscriber in subscribers.values) {
            (subscriber as? DIoTDeviceIdBluetoothServiceDelegate)?.let {
                subscriber.didReceiveChipIdentifier(this, data.toHexString())
            }
        }
    }

}