package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.general

import android.annotation.SuppressLint
import android.bluetooth.*
import android.content.Context
import com.daatrics.diotdemoapp.diotsdk.bluetooth.dispatcher.DIoTAndroidBleDispatcher
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothmanager.*
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.service.BluetoothService
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.service.BluetoothServiceProtocol
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.types.BluetoothServiceType
import com.daatrics.diotdemoapp.diotsdk.data.diotdeviceid.entities.DeviceId
import com.daatrics.diotdemoapp.diotsdk.support.diothashtable.DIoTHashTable
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

open class GeneralBluetoothDevice(
    override val context: Context,
    override val bleDispatcher: DIoTAndroidBleDispatcher,
    override val deviceId: DeviceId,
    override val deviceName: String,
    override val device: BluetoothDevice,
    override var manager: DIoTBluetoothManager,
): GeneralBluetoothDeviceProtocol,
    DIoTBluetoothManagerConnectionDelegate,
    DIoTBluetoothManagerDataUpdateDelegate {

    override var delegate: GeneralBluetoothDeviceDelegate? = null
    override var services: HashMap<BluetoothServiceType, BluetoothServiceProtocol> = HashMap()
    override var gatt: BluetoothGatt? = null

    var subscribers: HashMap<DIoTBluetoothManagerSubscriptionType, DIoTHashTable> = HashMap()

    @SuppressLint("MissingPermission")
    override fun connect() {
        manager.subscribe(this@GeneralBluetoothDevice, DIoTBluetoothManagerSubscriptionType.connection)
        manager.subscribe(this@GeneralBluetoothDevice, DIoTBluetoothManagerSubscriptionType.data)
        manager.connect(this@GeneralBluetoothDevice)
    }

    @SuppressLint("MissingPermission")
    override fun disconnect() {
        manager.disconnect(this@GeneralBluetoothDevice)
    }

    @SuppressLint("MissingPermission")
    override fun readRSSI() {
        bleDispatcher.addTask {
            gatt?.readRemoteRssi()
        }
    }

    @SuppressLint("MissingPermission")
    override fun requestMTU(size: Int) {
        bleDispatcher.addTask {
            gatt?.requestMtu(size)
        }
    }

    @SuppressLint("MissingPermission")
    override fun discoverServices(servicesUUIDs: ArrayList<UUID>?) {
        bleDispatcher.addTask {
            gatt?.discoverServices()
        }
    }

    @SuppressLint("MissingPermission")
    override fun readValue(characteristic: BluetoothGattCharacteristic) {
        bleDispatcher.addTask {
            gatt?.readCharacteristic(characteristic)
        }
    }

    @SuppressLint("MissingPermission")
    override fun writeValue(
        data: ByteArray,
        characteristic: BluetoothGattCharacteristic,
        response: Boolean
    ) {
        characteristic.value = data
        if (!response) {
            characteristic.properties.and(BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE)
        } else {
            characteristic.properties.and(BluetoothGattCharacteristic.PROPERTY_WRITE)
        }
        bleDispatcher.addTask {
            gatt?.writeCharacteristic(characteristic)
        }
    }

    @SuppressLint("MissingPermission")
    override fun setNotifyValue(
        enabled: Boolean,
        characteristic: BluetoothGattCharacteristic
    ) {
        bleDispatcher.addTask {
            gatt?.setCharacteristicNotification(characteristic, enabled);
        }

        val descriptor = characteristic.getDescriptor(UUID.fromString(BluetoothService.CLIENT_CHARACTERISTIC_CONFIG))
        descriptor?.let {
            if (enabled) {
                it.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
            } else {
                it.value = BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE
            }
            bleDispatcher.addTask {
                gatt?.writeDescriptor(it)
            }
        }
    }

    //DIoTBluetoothManagerConnectionDelegate
    @SuppressLint("MissingPermission")
    override fun didConnectTo(
        manager: DIoTBluetoothManagerProtocol,
        gatt: BluetoothGatt
    ) {
        this.gatt = gatt
        this.gatt?.discoverServices()
        //do not send connected before characteristics finished to discovery
        //delegate?.didConnect(this)
    }

    override fun didFailToConnect(
        manager: DIoTBluetoothManagerProtocol,
        gatt: BluetoothGatt,
        error: Error?
    ) {
        this.gatt = null
        manager.unsubscribe(this@GeneralBluetoothDevice, DIoTBluetoothManagerSubscriptionType.connection)
        manager.unsubscribe(this@GeneralBluetoothDevice, DIoTBluetoothManagerSubscriptionType.data)
        delegate?.didFailToConnect(this)
    }

    override fun didDisconnectFrom(
        manager: DIoTBluetoothManagerProtocol,
        gatt: BluetoothGatt
    ) {
        this.gatt = null
        manager.unsubscribe(this@GeneralBluetoothDevice, DIoTBluetoothManagerSubscriptionType.connection)
        manager.unsubscribe(this@GeneralBluetoothDevice, DIoTBluetoothManagerSubscriptionType.data)
        delegate?.didDisconnect(this)
    }

    //DIoTBluetoothManagerDataUpdateDelegate
    override fun didDiscoverServices(gatt: BluetoothGatt, error: Error?) {
        if (gatt != this.gatt)
            return

        services.clear()

        for (service in gatt.services) {
            for (value in BluetoothServiceType.values()) {
                if (value.serviceString.lowercase() == service.uuid.toString().lowercase())
                services.put(value, value.service(this))
            }
        }

        //send DIoT is connected after the characteristics been discovered
        delegate?.didConnect(this)
    }

    override fun didDiscoverCharacteristicsFor(
        gatt: BluetoothGatt,
        service: BluetoothGattService,
        error: Error?
    ) {
        if (gatt != this.gatt)
            return

        //not needed
    }

    override fun didUpdateValueFor(
        gatt: BluetoothGatt,
        characteristic: BluetoothGattCharacteristic,
        error: Error?
    ) {
        if (gatt != this.gatt)
            return

        for (service in services)
            if (service.key.uuid == characteristic.service.uuid)
                service.value.handleDidUpdateValue(characteristic)
    }

    override fun didUpdateNotificationStateFor(
        gatt: BluetoothGatt,
        characteristic: BluetoothGattCharacteristic,
        enable: Boolean,
        error: Error?
    ) {
        if (gatt != this.gatt)
            return

        for (service in services)
            if (service.key.uuid == characteristic.service.uuid)
                service.value.handleDidUpdateNotificationState(characteristic)
    }

    override fun didWriteValueFor(
        gatt: BluetoothGatt,
        characteristic: BluetoothGattCharacteristic,
        error: Error?
    ) {
        if (gatt != this.gatt)
            return

        for (service in services)
            if (service.key.uuid == characteristic.service.uuid)
                service.value.handleDidWriteValue(characteristic)
    }

    override fun didReadRSSI(gatt: BluetoothGatt?, RSSI: Int, error: Error?) {
        if (gatt != this.gatt)
            return

        delegate?.didReceiveRSSI(this, RSSI)
    }
}