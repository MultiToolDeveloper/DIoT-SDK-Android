package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_debug

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.general.GeneralBluetoothDevice
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.service.BluetoothService
import com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.debug.DebugParser
import com.daatrics.diotdemoapp.diotsdk.support.diothashtable.DIoTHashTable
import java.util.*


class DIoTDebugBluetoothService(
    generalDevice: GeneralBluetoothDevice
) : BluetoothService(generalDevice),
    DIoTDebugBluetoothServiceProtocol{

    private var subscribers = DIoTHashTable()

    override fun handleDidUpdateValue(characteristic: BluetoothGattCharacteristic) {
        if (!DIoTDebugBluetoothServiceCharacteristics.contents(characteristic.uuid))
            return
        handleUpdateValue(characteristic)
    }

    override fun handleDidWriteValue(bluetoothCharacteristic: BluetoothGattCharacteristic) {
        // No need
    }

    override fun handleDidUpdateNotificationState(bluetoothCharacteristic: BluetoothGattCharacteristic) {
        handleUpdateNotificationState(bluetoothCharacteristic)
    }

    //GeneraBatteryServiceProtocol
    override fun notifyDebug(enable: Boolean) {
        setNotifyValue(enable, DIoTDebugBluetoothServiceCharacteristics.logs.uuid)
    }

    override fun sendDebug(command: String) {
        writeValue( DebugParser.byteArrayFromValue(command), DIoTDebugBluetoothServiceCharacteristics.logs.uuid, false)
    }

    override fun subscribe(subscriber: DIoTDebugBluetoothServiceDelegate) {
        if (!(subscribers.values.contains(subscriber)))
            subscribers.values.add(subscriber)
    }

    override fun unsubscribe(subscriber: DIoTDebugBluetoothServiceDelegate) {
        if ((subscribers.values.contains(subscriber)))
            subscribers.values.remove(subscriber)
    }

    //Handle updating of characteristic
    fun handleUpdateValue(characteristic: BluetoothGattCharacteristic) {
        when (characteristic.uuid) {
            DIoTDebugBluetoothServiceCharacteristics.logs.uuid -> handleUpdateLevel(characteristic.value)
        }
    }

    fun handleUpdateLevel(data: ByteArray){
        for (subscriber in subscribers.values) {
            (subscriber as? DIoTDebugBluetoothServiceDelegate)?.let {
                subscriber.didReceiveLogs(this, DebugParser.valueFromByteArray(data))
            }
        }
    }

    fun handleUpdateNotificationState(characteristic: BluetoothGattCharacteristic) {
        val isEnabled = characteristic.getDescriptor(UUID.fromString(BluetoothService.CLIENT_CHARACTERISTIC_CONFIG)).value.contentEquals(
            BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE)
        for (subscriber in subscribers.values) {
            (subscriber as? DIoTDebugBluetoothServiceDelegate)?.let {
                subscriber.subscriptionStatusChange(this, isEnabled)
            }
        }
    }

}