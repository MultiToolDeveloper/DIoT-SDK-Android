package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.general

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.content.Context
import com.daatrics.diotdemoapp.diotsdk.bluetooth.dispatcher.DIoTAndroidBleDispatcher
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothmanager.DIoTBluetoothManager
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.service.BluetoothServiceProtocol
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.types.BluetoothServiceType
import com.daatrics.diotdemoapp.diotsdk.data.diotdeviceid.entities.DeviceId
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

interface GeneralBluetoothDeviceProtocol {
    val context: Context
    val bleDispatcher: DIoTAndroidBleDispatcher
    var delegate: GeneralBluetoothDeviceDelegate?

    val deviceId: DeviceId
    val deviceName: String
    val device: BluetoothDevice
    val gatt: BluetoothGatt?
    var manager: DIoTBluetoothManager
    var services: HashMap<BluetoothServiceType, BluetoothServiceProtocol>

    fun connect()
    fun disconnect()

    fun readRSSI()
    fun discoverServices(servicesUUIDs: ArrayList<UUID>?)
    fun readValue(characteristic: BluetoothGattCharacteristic)
    fun writeValue(data: ByteArray, characteristic: BluetoothGattCharacteristic, response: Boolean)
    fun setNotifyValue(enabled: Boolean, characteristic: BluetoothGattCharacteristic)

    fun requestMTU(size: Int) //Android only
}