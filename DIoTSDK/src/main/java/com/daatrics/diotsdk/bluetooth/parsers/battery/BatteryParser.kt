package com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.battery

import com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.parser_errors.BluetoothCharacteristicParserType
import com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.parser_errors.BluetoothCharacteristicValueParserError

class BatteryParser {
    companion object {
        const val lenght = 1

        fun valueFromByteArray(array: ByteArray): Int {
            if (array.size < lenght)
                throw BluetoothCharacteristicValueParserError.cannotParse(BluetoothCharacteristicParserType.battery)

            return array[0].toInt()
        }

        fun byteArrayFromValue(value: Int): ByteArray {
            val array = ByteArray(1)
            array[0] = value.toByte()
            return array
        }
    }
}