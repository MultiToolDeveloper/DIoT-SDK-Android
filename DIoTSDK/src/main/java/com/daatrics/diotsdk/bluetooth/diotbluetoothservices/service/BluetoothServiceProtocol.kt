package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.service

import android.bluetooth.BluetoothGattCharacteristic
import java.util.*

interface BluetoothServiceProtocol {
    fun handleDidUpdateValue(characteristic: BluetoothGattCharacteristic)
    fun handleDidWriteValue(characteristic: BluetoothGattCharacteristic)
    fun handleDidUpdateNotificationState(characteristic: BluetoothGattCharacteristic)
    fun readValue(characteristicUUID: UUID)
    fun setNotifyValue(enabled: Boolean, characteristicUUID: UUID)
    fun writeValue(data: ByteArray, characteristicUUID: UUID)
    fun writeValue(data: ByteArray, characteristicUUID: UUID, response: Boolean)
}