package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_data_interface

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.general.GeneralBluetoothDevice
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.service.BluetoothService
import com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.data_interface.DataInterfaceParser
import com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.parser_errors.BluetoothCharacteristicValueParserError
import com.daatrics.diotdemoapp.diotsdk.support.diothashtable.DIoTHashTable
import java.util.*


class DIoTDataInterfaceBluetoothService(
    generalDevice: GeneralBluetoothDevice
) : BluetoothService(generalDevice),
    DIoTDataInterfaceBluetoothServiceProtocol{

    private var subscribers = DIoTHashTable()

    override fun handleDidUpdateValue(characteristic: BluetoothGattCharacteristic) {
        if (!DIoTDataInterfaceBluetoothServiceCharacteristics.contents(characteristic.uuid))
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
    override fun fetchData(channelNumber: Int) {
        val targetCharacteristic: DIoTDataInterfaceBluetoothServiceCharacteristics =
            when (channelNumber){
                1 -> { DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel1 }
                2 -> { DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel2 }
                3 -> { DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel3 }
                4 -> { DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel4 }
                5 -> { DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel5 }
                6 -> { DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel6 }
                7 -> { DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel7 }
                8 -> { DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel8 }
                9 -> { DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel9 }
                else -> { DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel1 }
            }

        readValue(targetCharacteristic.uuid)
    }

    override fun notifyData(channelNumber: Int, enable: Boolean) {
        val targetCharacteristic: DIoTDataInterfaceBluetoothServiceCharacteristics =
            when (channelNumber){
                1 -> { DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel1 }
                2 -> { DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel2 }
                3 -> { DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel3 }
                4 -> { DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel4 }
                5 -> { DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel5 }
                6 -> { DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel6 }
                7 -> { DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel7 }
                8 -> { DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel8 }
                9 -> { DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel9 }
                else -> { DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel1 }
            }
        setNotifyValue(enable, targetCharacteristic.uuid)
    }

    override fun subscribe(subscriber: DIoTDataInterfaceBluetoothServiceDelegate) {
        if (!(subscribers.values.contains(subscriber)))
            subscribers.values.add(subscriber)
    }

    override fun unsubscribe(subscriber: DIoTDataInterfaceBluetoothServiceDelegate) {
        if ((subscribers.values.contains(subscriber)))
            subscribers.values.remove(subscriber)
    }


    //Handle updating of characteristic
    fun handleUpdateValue(characteristic: BluetoothGattCharacteristic) {
        when (characteristic.uuid) {
            DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel1.uuid -> handleUpdateIdentifier(characteristic)
            DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel2.uuid -> handleUpdateIdentifier(characteristic)
            DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel3.uuid -> handleUpdateIdentifier(characteristic)
            DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel4.uuid -> handleUpdateIdentifier(characteristic)
            DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel5.uuid -> handleUpdateIdentifier(characteristic)
            DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel6.uuid -> handleUpdateIdentifier(characteristic)
            DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel7.uuid -> handleUpdateIdentifier(characteristic)
            DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel8.uuid -> handleUpdateIdentifier(characteristic)
            DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel9.uuid -> handleUpdateIdentifier(characteristic)
        }
    }

    fun handleUpdateNotificationState(characteristic: BluetoothGattCharacteristic) {
        val isEnabled = characteristic.getDescriptor(UUID.fromString(BluetoothService.CLIENT_CHARACTERISTIC_CONFIG)).value.contentEquals(
            BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE)
        when (characteristic.uuid) {
            DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel1.uuid -> {
                for (subscriber in subscribers.values) {
                    (subscriber as? DIoTDataInterfaceBluetoothServiceDelegate)?.let {
                        subscriber.subscriptionDataChannelStatusChange(this, isEnabled, 1)
                    }
                }
            }
            DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel2.uuid -> {
                for (subscriber in subscribers.values) {
                    (subscriber as? DIoTDataInterfaceBluetoothServiceDelegate)?.let {
                        subscriber.subscriptionDataChannelStatusChange(this, isEnabled, 2)
                    }
                }
            }
            DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel3.uuid -> {
                for (subscriber in subscribers.values) {
                    (subscriber as? DIoTDataInterfaceBluetoothServiceDelegate)?.let {
                        subscriber.subscriptionDataChannelStatusChange(this, isEnabled, 3)
                    }
                }
            }
            DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel4.uuid -> {
                for (subscriber in subscribers.values) {
                    (subscriber as? DIoTDataInterfaceBluetoothServiceDelegate)?.let {
                        subscriber.subscriptionDataChannelStatusChange(this, isEnabled, 4)
                    }
                }
            }
            DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel5.uuid -> {
                for (subscriber in subscribers.values) {
                    (subscriber as? DIoTDataInterfaceBluetoothServiceDelegate)?.let {
                        subscriber.subscriptionDataChannelStatusChange(this, isEnabled, 5)
                    }
                }
            }
            DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel6.uuid -> {
                for (subscriber in subscribers.values) {
                    (subscriber as? DIoTDataInterfaceBluetoothServiceDelegate)?.let {
                        subscriber.subscriptionDataChannelStatusChange(this, isEnabled, 6)
                    }
                }
            }
            DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel7.uuid -> {
                for (subscriber in subscribers.values) {
                    (subscriber as? DIoTDataInterfaceBluetoothServiceDelegate)?.let {
                        subscriber.subscriptionDataChannelStatusChange(this, isEnabled, 7)
                    }
                }
            }
            DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel8.uuid -> {
                for (subscriber in subscribers.values) {
                    (subscriber as? DIoTDataInterfaceBluetoothServiceDelegate)?.let {
                        subscriber.subscriptionDataChannelStatusChange(this, isEnabled, 8)
                    }
                }
            }
            DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel9.uuid -> {
                for (subscriber in subscribers.values) {
                    (subscriber as? DIoTDataInterfaceBluetoothServiceDelegate)?.let {
                        subscriber.subscriptionDataChannelStatusChange(this, isEnabled, 9)
                    }
                }
            }
        }
    }

    fun handleUpdateIdentifier(characteristic: BluetoothGattCharacteristic){
        val data = characteristic.value
        when (characteristic.uuid) {
            DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel1.uuid -> handleUpdateDataChannelValue(1, data)
            DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel2.uuid -> handleUpdateDataChannelValue(2, data)
            DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel3.uuid -> handleUpdateDataChannelValue(3, data)
            DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel4.uuid -> handleUpdateDataChannelValue(4, data)
            DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel5.uuid -> handleUpdateDataChannelValue(5, data)
            DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel6.uuid -> handleUpdateDataChannelValue(6, data)
            DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel7.uuid -> handleUpdateDataChannelValue(7, data)
            DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel8.uuid -> handleUpdateDataChannelValue(8, data)
            DIoTDataInterfaceBluetoothServiceCharacteristics.dataChannel9.uuid -> handleUpdateDataChannelValue(9, data)
        }
    }

    fun handleUpdateDataChannelValue(number: Int, data: ByteArray) {
        try {
            val value = DataInterfaceParser.valueFromByteArray(data)
            for (subscriber in subscribers.values) {
                (subscriber as? DIoTDataInterfaceBluetoothServiceDelegate)?.let {
                    subscriber.didReceiveDataChannel(this, value, number)
                }
            }
        } catch (e: BluetoothCharacteristicValueParserError) {
            for (subscriber in subscribers.values) {
                (subscriber as? DIoTDataInterfaceBluetoothServiceDelegate)?.let {
                    subscriber.didReceiveError(this, DIoTDataInterfaceBluetoothServiceError.cannotParse(e))
                }
            }
        }
    }

}