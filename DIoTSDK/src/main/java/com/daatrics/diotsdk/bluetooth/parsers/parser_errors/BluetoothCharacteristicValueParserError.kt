package com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.parser_errors

import java.lang.Exception

sealed class BluetoothCharacteristicValueParserError: Exception() {
    data class cannotParse(val type: BluetoothCharacteristicParserType): BluetoothCharacteristicValueParserError()
    class empty(): BluetoothCharacteristicValueParserError()
}