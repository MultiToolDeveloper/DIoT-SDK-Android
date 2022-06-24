package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.service

import android.annotation.SuppressLint
import android.bluetooth.BluetoothGattCharacteristic
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.general.GeneralBluetoothDeviceProtocol
import java.util.*

open class BluetoothService(
    val generalDevice: GeneralBluetoothDeviceProtocol
):
    BluetoothServiceProtocol {

    override fun handleDidUpdateValue(characteristic: BluetoothGattCharacteristic) {}

    override fun handleDidWriteValue(characteristic: BluetoothGattCharacteristic) {}

    override fun handleDidUpdateNotificationState(characteristic: BluetoothGattCharacteristic) {}

    //BluetoothServiceProtocol
    @SuppressLint("MissingPermission")
    override fun readValue(characteristicUUID: UUID) {
        generalDevice.gatt?.let {
            for (service in it.services) {
                for (characteristic in service.characteristics)
                    if (characteristic.uuid == characteristicUUID)
                        generalDevice.readValue(characteristic)
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun setNotifyValue(
        enabled: Boolean,
        characteristicUUID: UUID
    ) {
        generalDevice.gatt?.let {
            for (service in it.services) {
                for (characteristic in service.characteristics)
                    if (characteristic.uuid == characteristicUUID)
                        generalDevice.setNotifyValue(enabled, characteristic)
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun writeValue(data: ByteArray, characteristicUUID: UUID) {
        generalDevice.gatt?.let {
            for (service in it.services) {
                for (characteristic in service.characteristics)
                    if (characteristic.uuid == characteristicUUID)
                        generalDevice.writeValue(data, characteristic, true)
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun writeValue(
        data: ByteArray,
        characteristicUUID: UUID,
        response: Boolean
    ) {
        generalDevice.gatt?.let {
            for (service in it.services) {
                for (characteristic in service.characteristics)
                    if (characteristic.uuid == characteristicUUID)
                        generalDevice.writeValue(data, characteristic, response)
            }
        }
    }

    companion object {
        //descriptors for notification
        const val CHARACTERISTIC_USER_DESCRIPTION = "00002901-0000-1000-8000-00805f9b34fb"
        const val CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb"
    }
}