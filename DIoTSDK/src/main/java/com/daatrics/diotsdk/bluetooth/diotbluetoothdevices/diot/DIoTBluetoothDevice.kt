package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.diot

import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.general.*
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_command_interface.DIoTCommandInterfaceBluetoothServiceProtocol
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_data_interface.DIoTDataInterfaceBluetoothServiceProtocol
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_debug.DIoTDebugBluetoothServiceProtocol
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_device_id.DIoTDeviceIdBluetoothServiceProtocol
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.general_battery.GeneralBatteryBluetoothServiceProtocol
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.general_devece_information.GeneralDeviceInformationBluetoothServiceProtocol
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.types.BluetoothServiceType
import com.daatrics.diotdemoapp.diotsdk.data.diotdeviceid.entities.DeviceId

class DIoTBluetoothDevice(private var generalDevice: GeneralBluetoothDeviceProtocol):
    DIoTBluetoothDeviceProtocol
{
    override var name: String
        get() = generalDevice.deviceName
        set(value) {}

    override var deviceId: DeviceId
        get() = generalDevice.deviceId
        set(value) {}

    override var address: String
        get() = generalDevice.device.address
        set(value) {}

    private val dIoTBluetoothDeviceConnectionService = DIoTBluetoothDeviceConnectionService(generalDevice, this)
    override var connectionService: DIoTBluetoothDeviceConnectionServiceProtocol
        get() = dIoTBluetoothDeviceConnectionService
        set(value) {}

    override var deviceInformationService: GeneralDeviceInformationBluetoothServiceProtocol?
        get() = generalDevice.services.get(BluetoothServiceType.deviceInformation) as? GeneralDeviceInformationBluetoothServiceProtocol
        set(value) {}

    override var batteryService: GeneralBatteryBluetoothServiceProtocol?
        get() = generalDevice.services.get(BluetoothServiceType.battery) as? GeneralBatteryBluetoothServiceProtocol
        set(value) {}

    override var commandInterfaceService: DIoTCommandInterfaceBluetoothServiceProtocol?
        get() = generalDevice.services.get(BluetoothServiceType.command) as? DIoTCommandInterfaceBluetoothServiceProtocol
        set(value) {}

    override var dataInterfaceService: DIoTDataInterfaceBluetoothServiceProtocol?
        get() = generalDevice.services.get(BluetoothServiceType.data) as? DIoTDataInterfaceBluetoothServiceProtocol
        set(value) {}

    override var deviceIdentifierService: DIoTDeviceIdBluetoothServiceProtocol?
        get() = generalDevice.services.get(BluetoothServiceType.deviceIdentifier) as? DIoTDeviceIdBluetoothServiceProtocol
        set(value) {}

    override var debugService: DIoTDebugBluetoothServiceProtocol?
        get() = generalDevice.services.get(BluetoothServiceType.debug) as? DIoTDebugBluetoothServiceProtocol
        set(value) {}
}