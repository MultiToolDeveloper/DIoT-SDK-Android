package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_data_interface

import com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.parser_errors.BluetoothCharacteristicValueParserError
import java.lang.Exception

sealed class DIoTDataInterfaceBluetoothServiceError: Exception() {
    data class cannotParse(val characteristicError: BluetoothCharacteristicValueParserError): DIoTDataInterfaceBluetoothServiceError()
}