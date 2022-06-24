package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.diot

import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_command_interface.DIoTCommandInterfaceBluetoothServiceProtocol
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_data_interface.DIoTDataInterfaceBluetoothServiceProtocol
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_debug.DIoTDebugBluetoothServiceProtocol
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_device_id.DIoTDeviceIdBluetoothServiceProtocol
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.general_battery.GeneralBatteryBluetoothServiceProtocol
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.general_devece_information.GeneralDeviceInformationBluetoothServiceProtocol
import com.daatrics.diotdemoapp.diotsdk.data.diotdeviceid.entities.DeviceId

interface DIoTBluetoothDeviceProtocol {

    var name: String
    var deviceId: DeviceId
    var address: String

    var connectionService: DIoTBluetoothDeviceConnectionServiceProtocol
    var deviceInformationService: GeneralDeviceInformationBluetoothServiceProtocol?
    var batteryService: GeneralBatteryBluetoothServiceProtocol?
    var commandInterfaceService: DIoTCommandInterfaceBluetoothServiceProtocol?
    var dataInterfaceService: (DIoTDataInterfaceBluetoothServiceProtocol)?
    var deviceIdentifierService: DIoTDeviceIdBluetoothServiceProtocol?
    var debugService: DIoTDebugBluetoothServiceProtocol?
}