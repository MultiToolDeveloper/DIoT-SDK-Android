package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_data_interface

import java.util.*

enum class DIoTDataInterfaceBluetoothServiceCharacteristics(val characteristicString: String) {
    dataChannel1("0000d001-0000-1000-8000-00805f9b34fb"),
    dataChannel2("0000d002-0000-1000-8000-00805f9b34fb"),
    dataChannel3("0000d003-0000-1000-8000-00805f9b34fb"),
    dataChannel4("0000d004-0000-1000-8000-00805f9b34fb"),
    dataChannel5("0000d005-0000-1000-8000-00805f9b34fb"),
    dataChannel6("0000d006-0000-1000-8000-00805f9b34fb"),
    dataChannel7("0000d007-0000-1000-8000-00805f9b34fb"),
    dataChannel8("0000d008-0000-1000-8000-00805f9b34fb"),
    dataChannel9("0000d009-0000-1000-8000-00805f9b34fb");

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