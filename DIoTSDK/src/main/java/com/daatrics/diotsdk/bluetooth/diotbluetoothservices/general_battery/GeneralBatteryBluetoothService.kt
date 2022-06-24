package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.general_battery

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.general.GeneralBluetoothDevice
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.service.BluetoothService
import com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.battery.BatteryParser
import com.daatrics.diotdemoapp.diotsdk.support.diothashtable.DIoTHashTable
import java.util.*


class GeneralBatteryBluetoothService(
    generalDevice: GeneralBluetoothDevice
) : BluetoothService(generalDevice),
    GeneralBatteryBluetoothServiceProtocol{

    private var subscribers = DIoTHashTable()

    override fun handleDidUpdateValue(characteristic: BluetoothGattCharacteristic) {
        if (!GeneralBatteryBluetoothServiceCharacteristics.contents(characteristic.uuid))
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
    override fun fetchBatteryLevel() {
        readValue(GeneralBatteryBluetoothServiceCharacteristics.level.uuid)
    }

    override fun notifyBatteryLevel(enabled: Boolean) {
        setNotifyValue(enabled, GeneralBatteryBluetoothServiceCharacteristics.level.uuid)
    }

    override fun subscribe(subscriber: GeneralBatteryBluetoothServiceDelegate) {
        if (!(subscribers.values.contains(subscriber)))
            subscribers.values.add(subscriber)
    }

    override fun unsubscribe(subscriber: GeneralBatteryBluetoothServiceDelegate) {
        if ((subscribers.values.contains(subscriber)))
            subscribers.values.remove(subscriber)
    }

    //Handle updating of characteristic
    fun handleUpdateValue(characteristic: BluetoothGattCharacteristic) {
        when (characteristic.uuid) {
            GeneralBatteryBluetoothServiceCharacteristics.level.uuid -> handleUpdateLevel(characteristic.value)
        }
    }

    fun handleUpdateLevel(data: ByteArray){
        for (subscriber in subscribers.values) {
            (subscriber as? GeneralBatteryBluetoothServiceDelegate)?.let {
                subscriber.didReceiveLevel(this, BatteryParser.valueFromByteArray(data))
            }
        }
    }

    fun handleUpdateNotificationState(characteristic: BluetoothGattCharacteristic) {
        val isEnabled = characteristic.getDescriptor(UUID.fromString(BluetoothService.CLIENT_CHARACTERISTIC_CONFIG)).value.contentEquals(
            BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE)
        for (subscriber in subscribers.values) {
            (subscriber as? GeneralBatteryBluetoothServiceDelegate)?.let {
                subscriber.subscriptionStatusChange(this, isEnabled)
            }
        }
    }

}