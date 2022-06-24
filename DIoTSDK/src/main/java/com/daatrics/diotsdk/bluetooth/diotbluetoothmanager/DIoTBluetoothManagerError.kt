package com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothmanager

import com.daatrics.diotdemoapp.diotsdk.data.diotdeviceid.enums.DeviceIdError
import java.lang.Exception

sealed class DIoTBluetoothManagerScanningError: Exception()  {
    data class cannotParseAdvertisementId(val result: DeviceIdError) : DIoTBluetoothManagerScanningError()
    data class anotherScanError(val error: Int) : DIoTBluetoothManagerScanningError()
}
