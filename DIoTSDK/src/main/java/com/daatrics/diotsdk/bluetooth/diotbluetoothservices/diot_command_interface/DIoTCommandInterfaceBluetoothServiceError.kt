package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_command_interface

import com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.parser_errors.BluetoothCharacteristicValueParserError
import java.lang.Exception

sealed class DIoTCommandInterfaceBluetoothServiceError: Exception() {
    data class cannotParse(val characteristicError: BluetoothCharacteristicValueParserError): DIoTCommandInterfaceBluetoothServiceError()
}