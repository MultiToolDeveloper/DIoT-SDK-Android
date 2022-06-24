package com.daatrics.diotdemoapp.diotsdk.data.diotdeviceid.interfaces

import com.daatrics.diotdemoapp.diotsdk.data.diotdeviceid.entities.DeviceId

interface DeviceIdParserProtocol {
    fun parseDeviceId(hexString: String): DeviceId
    fun parseHexString(deviceId: DeviceId): String
}