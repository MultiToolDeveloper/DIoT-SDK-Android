package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.diot

import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.general.*
import com.daatrics.diotdemoapp.diotsdk.bluetooth.dispatcher.DIoTAndroidBleDispatcher
import com.daatrics.diotdemoapp.diotsdk.support.diothashtable.DIoTHashTable

class DIoTBluetoothDeviceConnectionService(
    private var generalDevice: GeneralBluetoothDeviceProtocol,
    private var diotDevice: DIoTBluetoothDevice):
    DIoTBluetoothDeviceConnectionServiceProtocol,
    GeneralBluetoothDeviceDelegate {

    private var subscribers = DIoTHashTable()

    init {
        generalDevice.delegate = this
    }

    override fun connect() {
        generalDevice.connect()
    }

    override fun disconnect() {
        generalDevice.disconnect()
    }

    override fun readRSSI() {
        generalDevice.readRSSI()
    }

    override fun subscribe(
        subscriber: DIoTBluetoothDeviceConnectionServiceDelegate
    ) {
        if (!(subscribers.values.contains(subscriber)))
            subscribers.values.add(subscriber)
    }

    override fun unsubscribe(
        subscriber: DIoTBluetoothDeviceConnectionServiceDelegate
    ) {
        if ((subscribers.values.contains(subscriber)))
            subscribers.values.remove(subscriber)
    }

    //GeneralBluetoothDeviceDelegate
    override fun didReceiveRSSI(device: GeneralBluetoothDeviceProtocol, rssi: Int) {
        for (subscriber in subscribers.values) {
            (subscriber as? DIoTBluetoothDeviceConnectionServiceDelegate)?.let {
                subscriber.didReceiveRSSI(this, rssi)
            }
        }
    }

    override fun didConnect(device: GeneralBluetoothDeviceProtocol) {
        //do some additional setup

        //request MTU
        generalDevice.requestMTU(512) //need this to receive more than 20 bytes over ble

        //subscribe on some available services
        diotDevice.commandInterfaceService?.notifyFeatures( true)
        diotDevice.commandInterfaceService?.notifyChannels( true)
        diotDevice.commandInterfaceService?.notifyRates( true)
        diotDevice.batteryService?.notifyBatteryLevel(true)
//        diotDevice.debugService?.notifyDebug(true)
//        diotDevice.dataInterfaceService?.notifyData(1, true)
//        diotDevice.dataInterfaceService?.notifyData(2, true)
//        diotDevice.dataInterfaceService?.notifyData(3, true)
//        diotDevice.dataInterfaceService?.notifyData(4, true)
//        diotDevice.dataInterfaceService?.notifyData(5, true)
//        diotDevice.dataInterfaceService?.notifyData(6, true)
//        diotDevice.dataInterfaceService?.notifyData(7, true)
//        diotDevice.dataInterfaceService?.notifyData(8, true)
//        diotDevice.dataInterfaceService?.notifyData(9, true)

        //wait until all commands be finished
        generalDevice.bleDispatcher.delegate = object: DIoTAndroidBleDispatcher.CommandExecutedCallback {
            override fun onExecuted(commandQueue: Int?) {
                if (commandQueue != 0)
                    return
                for (subscriber in subscribers.values) {
                    (subscriber as? DIoTBluetoothDeviceConnectionServiceDelegate)?.let {
                        subscriber.didConnect(this@DIoTBluetoothDeviceConnectionService)
                    }
                }

                device.bleDispatcher.delegate = null
            }
        }
    }

    override fun didDisconnect(device: GeneralBluetoothDeviceProtocol) {
        for (subscriber in subscribers.values) {
            (subscriber as? DIoTBluetoothDeviceConnectionServiceDelegate)?.let {
                subscriber.didDisconnect(this)
            }
        }
    }

    override fun didFailToConnect(device: GeneralBluetoothDeviceProtocol) {
        for (subscriber in subscribers.values) {
            (subscriber as? DIoTBluetoothDeviceConnectionServiceDelegate)?.let {
                subscriber.didFailToConnect(this)
            }
        }
    }

    override fun didReceiveError(
        device: GeneralBluetoothDeviceProtocol,
        error: GeneralBluetoothDeviceError
    ) {
        for (subscriber in subscribers.values) {
            (subscriber as? DIoTBluetoothDeviceConnectionServiceDelegate)?.let {
                when (error) {
                    is GeneralBluetoothDeviceError.cannotHandleService ->
                        subscriber.didReceiveError(this, DIoTBluetoothDeviceConnectionError.cannotHandleService(error.service))
                    is GeneralBluetoothDeviceError.cannotHandleServiceFromCaracteristic ->
                        subscriber.didReceiveError(this, DIoTBluetoothDeviceConnectionError.cannotHandleServiceFromCaracteristic(error.service))
                    is GeneralBluetoothDeviceError.connectionError ->
                        subscriber.didReceiveError(this, DIoTBluetoothDeviceConnectionError.connectionError())
                    is GeneralBluetoothDeviceError.other ->
                        subscriber.didReceiveError(this, DIoTBluetoothDeviceConnectionError.other(error.other))
                    is GeneralBluetoothDeviceError.serviceUnavaliable ->
                        subscriber.didReceiveError(this, DIoTBluetoothDeviceConnectionError.serviceUnavaliable(error.type))
                }
            }
        }
    }
}