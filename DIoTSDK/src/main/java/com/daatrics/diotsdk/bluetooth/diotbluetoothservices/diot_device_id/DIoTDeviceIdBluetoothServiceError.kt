package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_device_id

import com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.parser_errors.BluetoothCharacteristicValueParserError
import java.lang.Exception

class DIoTDeviceIdBluetoothServiceError: Exception() {
    data class cannotParse(val characteristicError: BluetoothCharacteristicValueParserError)
}