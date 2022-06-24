package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.general_devece_information

import java.util.*

enum class GeneralDeviceInformationBluetoothServiceCharacteristics(val characteristicString: String) {
    manufacturerName("00002a29-0000-1000-8000-00805f9b34fb"),
    modelNumber("00002a24-0000-1000-8000-00805f9b34fb"),
    hardwareRevision("00002a27-0000-1000-8000-00805f9b34fb"),
    firmwareRevision("00002a26-0000-1000-8000-00805f9b34fb"),
    softwareRevision("00002a28-0000-1000-8000-00805f9b34fb");

    val rawValue: String
        get() = this.characteristicString

    val uuid: UUID
        get() = UUID.fromString(this.characteristicString)

    companion object {
        fun contents(uuid: UUID): Boolean {
            for (value in values())
                if (value.characteristicString.lowercase() == uuid.toString().lowercase())
                    return true
            return false
        }
    }
}