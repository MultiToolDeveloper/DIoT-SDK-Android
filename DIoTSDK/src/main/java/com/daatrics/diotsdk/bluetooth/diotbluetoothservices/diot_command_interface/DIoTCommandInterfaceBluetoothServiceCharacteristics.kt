package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_command_interface

import java.util.*

enum class DIoTCommandInterfaceBluetoothServiceCharacteristics(val characteristicString: String) {
    commandFeatures("0000c001-0000-1000-8000-00805f9b34fb"),
    commandChannels("0000c002-0000-1000-8000-00805f9b34fb"),
    commandRate("0000c003-0000-1000-8000-00805f9b34fb");

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