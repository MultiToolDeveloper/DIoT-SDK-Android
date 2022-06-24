package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.general_battery

import java.util.*

enum class GeneralBatteryBluetoothServiceCharacteristics(val characteristicString: String) {
    level("00002a19-0000-1000-8000-00805f9b34fb");

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