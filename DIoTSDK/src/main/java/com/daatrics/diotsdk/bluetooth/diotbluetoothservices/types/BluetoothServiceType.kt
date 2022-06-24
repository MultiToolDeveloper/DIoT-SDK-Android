package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.types

import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.general.GeneralBluetoothDevice
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_command_interface.DIoTCommandInterfaceBluetoothService
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_data_interface.DIoTDataInterfaceBluetoothService
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_debug.DIoTDebugBluetoothService
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_device_id.DIoTDeviceIdBluetoothService
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.general_battery.GeneralBatteryBluetoothService
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.general_devece_information.GeneralDeviceInformationBluetoothService
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.service.BluetoothServiceProtocol

import java.util.*


enum class BluetoothServiceType(val serviceString: String) {
    deviceInformation("0000180a-0000-1000-8000-00805f9b34fb"),
    battery("0000180f-0000-1000-8000-00805f9b34fb"),
    command("0000c000-0000-1000-8000-00805f9b34fb"),
    `data`("0000d000-0000-1000-8000-00805f9b34fb"),
    deviceIdentifier("0000ffd0-0000-1000-8000-00805f9b34fb"),
    debug("0000ff70-0000-1000-8000-00805f9b34fb");

    fun service(generalDevice: GeneralBluetoothDevice): BluetoothServiceProtocol {
        when (this){
            deviceInformation -> { return GeneralDeviceInformationBluetoothService(generalDevice)}
            battery -> { return GeneralBatteryBluetoothService(generalDevice) }
            command -> { return DIoTCommandInterfaceBluetoothService(generalDevice) }
            `data` -> { return DIoTDataInterfaceBluetoothService(generalDevice) }
            deviceIdentifier -> { return DIoTDeviceIdBluetoothService(generalDevice) }
            debug -> { return DIoTDebugBluetoothService(generalDevice) }
        }
    }

    val uuid: UUID
        get() = UUID.fromString(this.serviceString)

    companion object {
        fun contents(uuid: UUID): Boolean {
            for (value in values())
                if (value.serviceString.lowercase() == uuid.toString().lowercase())
                    return true
            return false
        }
    }
}