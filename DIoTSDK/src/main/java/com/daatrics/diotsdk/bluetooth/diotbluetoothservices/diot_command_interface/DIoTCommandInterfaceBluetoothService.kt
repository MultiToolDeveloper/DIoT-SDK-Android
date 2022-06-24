package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_command_interface

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.general.GeneralBluetoothDevice
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.service.BluetoothService
import com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.command_interface.CommandInterfaceChannelsParser
import com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.command_interface.CommandInterfaceFeaturesParser
import com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.command_interface.CommandInterfaceRatesParser
import com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.parser_errors.BluetoothCharacteristicValueParserError
import com.daatrics.diotdemoapp.diotsdk.support.diotextensions.intToByteArray
import com.daatrics.diotdemoapp.diotsdk.data.diotfeature.DIoTFeatureCode
import com.daatrics.diotdemoapp.diotsdk.data.diotfeature.DIoTFeatureData
import com.daatrics.diotdemoapp.diotsdk.support.diothashtable.DIoTHashTable
import java.util.*


class DIoTCommandInterfaceBluetoothService(
    generalDevice: GeneralBluetoothDevice
) : BluetoothService(generalDevice),
    DIoTCommandInterfaceBluetoothServiceProtocol
{
    private var subscribers = DIoTHashTable()

    override fun handleDidUpdateValue(characteristic: BluetoothGattCharacteristic) {
        if (!DIoTCommandInterfaceBluetoothServiceCharacteristics.contents(characteristic.uuid))
            return
        handleUpdateValue(characteristic)
    }

    override fun handleDidWriteValue(characteristic: BluetoothGattCharacteristic) {
        handleWriteValue(characteristic)
    }

    override fun handleDidUpdateNotificationState(characteristic: BluetoothGattCharacteristic) {
        handleUpdateNotificationState(characteristic)
    }

    //CommandInterfaceBluetoothServiceProtocol
    override fun fetchFeatures() {
        val data = ByteArray(1)
        data[0] = 1
        writeValue(data, DIoTCommandInterfaceBluetoothServiceCharacteristics.commandFeatures.uuid, false)
    }

    override fun fetchChannels() {
        val data = ByteArray(1)
        data[0] = 1
        writeValue(data, DIoTCommandInterfaceBluetoothServiceCharacteristics.commandChannels.uuid, false)
    }

    override fun fetchRates() {
        val data = ByteArray(1)
        data[0] = 1
        writeValue(data, DIoTCommandInterfaceBluetoothServiceCharacteristics.commandRate.uuid, false)
    }

    override fun fetchFeature(code: DIoTFeatureCode) {
        val data = ByteArray(2)
        data[0] = 1
        data[1] = code.code.toByte()
        writeValue(data, DIoTCommandInterfaceBluetoothServiceCharacteristics.commandFeatures.uuid, false)
    }

    override fun fetchChannel(channel: Int) {
        val data = ByteArray(2)
        data[0] = 1
        data[1] = channel.toByte()
        writeValue(data, DIoTCommandInterfaceBluetoothServiceCharacteristics.commandChannels.uuid, false)
    }

    override fun fetchRate(channel: Int) {
        val data = ByteArray(2)
        data[0] = 1
        data[1] = channel.toByte()
        writeValue(data, DIoTCommandInterfaceBluetoothServiceCharacteristics.commandRate.uuid, false)
    }

    override fun notifyFeatures(enable: Boolean) {
        setNotifyValue(enable, DIoTCommandInterfaceBluetoothServiceCharacteristics.commandFeatures.uuid)
    }

    override fun notifyChannels(enable: Boolean) {
        setNotifyValue(enable, DIoTCommandInterfaceBluetoothServiceCharacteristics.commandChannels.uuid)
    }

    override fun notifyRates(enable: Boolean) {
        setNotifyValue(enable, DIoTCommandInterfaceBluetoothServiceCharacteristics.commandRate.uuid)
    }

    //CommandInterfaceWriteBluetoothServiceProtocol
    override fun setFeature(feature: DIoTFeatureData) {
        val data = ByteArray(6)
        val par = DIoTFeatureData.getData(feature)
        data[0] = 2
        data[1] = par[0]
        data[2] = par[1]
        data[3] = par[2]
        data[4] = par[3]
        data[5] = par[4]
        writeValue(data, DIoTCommandInterfaceBluetoothServiceCharacteristics.commandFeatures.uuid, false)
    }

    override fun cleanFeature(code: DIoTFeatureCode) {
        val data = ByteArray(2)
        data[0] = 3
        data[1] = code.code.toByte()
        writeValue(data, DIoTCommandInterfaceBluetoothServiceCharacteristics.commandFeatures.uuid, false)
    }

    override fun cleanFeatures() {
        val data = ByteArray(1)
        data[0] = 3
        writeValue(data, DIoTCommandInterfaceBluetoothServiceCharacteristics.commandFeatures.uuid, false)
    }

    override fun setChannel(channel: Int, code: DIoTFeatureCode) {
        val data = ByteArray(3)
        data[0] = 2
        data[1] = channel.toByte()
        data[2] = code.code.toByte()
        writeValue(data, DIoTCommandInterfaceBluetoothServiceCharacteristics.commandChannels.uuid, false)
    }

    override fun cleanChannel(channel: Int) {
        val data = ByteArray(2)
        data[0] = 3
        data[1] = channel.toByte()
        writeValue(data, DIoTCommandInterfaceBluetoothServiceCharacteristics.commandChannels.uuid, false)
    }

    override fun cleanChannels() {
        val data = ByteArray(1)
        data[0] = 3
        writeValue(data, DIoTCommandInterfaceBluetoothServiceCharacteristics.commandChannels.uuid, false)
    }

    override fun setRate(channel: Int, rate: Int) {
        val data = ByteArray(6)
        val floatByte = rate.intToByteArray() ?: ByteArray(4)
        data[0] = 2
        data[1] = channel.toByte()
        data[2] = floatByte[0]
        data[3] = floatByte[1]
        data[4] = floatByte[2]
        data[5] = floatByte[3]
        writeValue(data, DIoTCommandInterfaceBluetoothServiceCharacteristics.commandRate.uuid, false)
    }

    override fun cleanRate(channel: Int) {
        val data = ByteArray(2)
        data[0] = 3
        data[1] = channel.toByte()
        writeValue(data, DIoTCommandInterfaceBluetoothServiceCharacteristics.commandRate.uuid, false)
    }

    override fun cleanRates() {
        val data = ByteArray(1)
        data[0] = 3
        writeValue(data, DIoTCommandInterfaceBluetoothServiceCharacteristics.commandRate.uuid, false)
    }

    override fun subscribe(subscriber: DIoTCommandInterfaceBluetoothServiceDelegate) {
        if (!(subscribers.values.contains(subscriber)))
            subscribers.values.add(subscriber)
    }

    override fun unsubscribe(subscriber: DIoTCommandInterfaceBluetoothServiceDelegate) {
        if ((subscribers.values.contains(subscriber)))
            subscribers.values.remove(subscriber)
    }

    //Handle updating of characteristic
    fun handleUpdateValue(characteristic: BluetoothGattCharacteristic) {
        when (characteristic.uuid) {
            DIoTCommandInterfaceBluetoothServiceCharacteristics.commandFeatures.uuid -> handleUpdateFeatures(characteristic.value)
            DIoTCommandInterfaceBluetoothServiceCharacteristics.commandChannels.uuid -> handleUpdateChannels(characteristic.value)
            DIoTCommandInterfaceBluetoothServiceCharacteristics.commandRate.uuid -> handleUpdateRate(characteristic.value)
        }
    }

    fun handleUpdateFeatures(data: ByteArray){
        try {
            val value = CommandInterfaceFeaturesParser.valueFromByteArray(data)
            for (subscriber in subscribers.values) {
                (subscriber as? DIoTCommandInterfaceBluetoothServiceDelegate)?.let {
                    subscriber.didReceiveCommandFeatures(this, value)
                }
            }
        } catch (e: BluetoothCharacteristicValueParserError){
            for (subscriber in subscribers.values) {
                (subscriber as? DIoTCommandInterfaceBluetoothServiceDelegate)?.let {
                    subscriber.didReceiveError(this, DIoTCommandInterfaceBluetoothServiceError.cannotParse(e))
                }
            }
        }
    }

    fun handleUpdateChannels(data: ByteArray){
        try {
            val value = CommandInterfaceChannelsParser.valueFromByteArray(data)
            for (subscriber in subscribers.values) {
                (subscriber as? DIoTCommandInterfaceBluetoothServiceDelegate)?.let {
                    subscriber.didReceiveCommandChannels(this, value)
                }
            }
        } catch (e: BluetoothCharacteristicValueParserError){
            for (subscriber in subscribers.values) {
                (subscriber as? DIoTCommandInterfaceBluetoothServiceDelegate)?.let {
                    subscriber.didReceiveError(this, DIoTCommandInterfaceBluetoothServiceError.cannotParse(e))
                }
            }
        }
    }

    fun handleUpdateRate(data: ByteArray){
        try {
            val value = CommandInterfaceRatesParser.valueFromByteArray(data)
            for (subscriber in subscribers.values) {
                (subscriber as? DIoTCommandInterfaceBluetoothServiceDelegate)?.let {
                    subscriber.didReceiveCommandRate(this, value)
                }
            }
        } catch (e: BluetoothCharacteristicValueParserError){
            for (subscriber in subscribers.values) {
                (subscriber as? DIoTCommandInterfaceBluetoothServiceDelegate)?.let {
                    subscriber.didReceiveError(this, DIoTCommandInterfaceBluetoothServiceError.cannotParse(e))
                }
            }
        }
    }

    // Handle notification state changes
    fun handleUpdateNotificationState(characteristic: BluetoothGattCharacteristic) {
        val isEnabled = characteristic.getDescriptor(UUID.fromString(BluetoothService.CLIENT_CHARACTERISTIC_CONFIG)).value.contentEquals(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE)
        when (characteristic.uuid) {
            DIoTCommandInterfaceBluetoothServiceCharacteristics.commandFeatures.uuid -> {
                for (subscriber in subscribers.values) {
                    (subscriber as? DIoTCommandInterfaceBluetoothServiceDelegate)?.let {
                        subscriber.subscriptionFeaturesStatusChange(this, isEnabled)
                    }
                }
            }
            DIoTCommandInterfaceBluetoothServiceCharacteristics.commandChannels.uuid -> {
                for (subscriber in subscribers.values) {
                    (subscriber as? DIoTCommandInterfaceBluetoothServiceDelegate)?.let {
                        subscriber.subscriptionChannelsStatusChange(this, isEnabled)
                    }
                }
            }
            DIoTCommandInterfaceBluetoothServiceCharacteristics.commandRate.uuid -> {
                for (subscriber in subscribers.values) {
                    (subscriber as? DIoTCommandInterfaceBluetoothServiceDelegate)?.let {
                        subscriber.subscriptionRatesStatusChange(this, isEnabled)
                    }
                }
            }
        }
    }

    // Handle write values
    fun handleWriteValue(characteristic: BluetoothGattCharacteristic) {
        when (characteristic.uuid) {
            DIoTCommandInterfaceBluetoothServiceCharacteristics.commandFeatures.uuid -> {
                for (subscriber in subscribers.values) {
                    (subscriber as? DIoTCommandInterfaceBluetoothServiceDelegate)?.let {
                        subscriber.didWriteCommandFeatures(this)
                    }
                }
            }
            DIoTCommandInterfaceBluetoothServiceCharacteristics.commandChannels.uuid -> {
                for (subscriber in subscribers.values) {
                    (subscriber as? DIoTCommandInterfaceBluetoothServiceDelegate)?.let {
                        subscriber.didWriteCommandChannels(this)
                    }
                }
            }
            DIoTCommandInterfaceBluetoothServiceCharacteristics.commandRate.uuid -> {
                for (subscriber in subscribers.values) {
                    (subscriber as? DIoTCommandInterfaceBluetoothServiceDelegate)?.let {
                        subscriber.didWriteCommandRate(this)
                    }
                }
            }
        }
    }

}