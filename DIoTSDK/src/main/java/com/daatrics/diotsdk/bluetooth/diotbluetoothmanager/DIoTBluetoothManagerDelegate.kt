package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothmanager

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.diot.DIoTBluetoothDevice

interface DIoTBluetoothManagerDelegate {
}

interface DIoTBluetoothManagerStateDelegate: DIoTBluetoothManagerDelegate {
    fun bluetoothManagerEnabledBluetooth(manager: DIoTBluetoothManagerProtocol)
    fun bluetoothManagerDisabledBluetooth(manager: DIoTBluetoothManagerProtocol)
    fun bluetoothManagerNotAllowedBluetooth(manager: DIoTBluetoothManagerProtocol)
    fun bluetoothManagerNoBLESupport(manager: DIoTBluetoothManagerProtocol)
}

interface DIoTBluetoothManagerConnectionDelegate: DIoTBluetoothManagerDelegate {
    fun didConnectTo(manager: DIoTBluetoothManagerProtocol,  gatt: BluetoothGatt)
    fun didFailToConnect(manager: DIoTBluetoothManagerProtocol,  gatt: BluetoothGatt, error: Error?)
    fun didDisconnectFrom(manager: DIoTBluetoothManagerProtocol,  gatt: BluetoothGatt)
}

interface DIoTBluetoothManagerScanningDelegate: DIoTBluetoothManagerDelegate {
    fun didDiscoverDevice(manager: DIoTBluetoothManagerProtocol, device: DIoTBluetoothDevice, rssi: Int)
    fun didReceiveScanningError(manager: DIoTBluetoothManagerProtocol, error: DIoTBluetoothManagerScanningError)
}

interface DIoTBluetoothManagerDataUpdateDelegate: DIoTBluetoothManagerDelegate {
    fun didDiscoverServices(gatt: BluetoothGatt, error: Error?)
    fun didDiscoverCharacteristicsFor(gatt: BluetoothGatt, service: BluetoothGattService, error: Error?)
    fun didUpdateValueFor(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic, error: Error?)
    fun didUpdateNotificationStateFor(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic, enable: Boolean, error: Error?)
    fun didWriteValueFor(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic, error: Error?)
    fun didReadRSSI(gatt: BluetoothGatt?, RSSI: Int, error: Error?)
}