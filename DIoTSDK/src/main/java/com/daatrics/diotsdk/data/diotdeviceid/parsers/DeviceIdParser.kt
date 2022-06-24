package com.daatrics.diotdemoapp.diotsdk.data.diotdeviceid.parsers

import com.daatrics.diotdemoapp.diotsdk.bluetooth.extensions.addCharAtIndex
import com.daatrics.diotdemoapp.diotsdk.data.diotdeviceid.entities.DeviceId
import com.daatrics.diotdemoapp.diotsdk.data.diotdeviceid.enums.DeviceIdError
import com.daatrics.diotdemoapp.diotsdk.data.diotdeviceid.enums.DeviceIdParserError
import java.util.*

class DeviceIdParser {

    fun parseDeviceId(hexString: String): DeviceId {
        val string = filter(hexString)
        return deviceIdFromString(string)
    }

    fun parseHexString(deviceId: DeviceId):String {
        return deviceUUIDHexStringFromUUID(deviceId.uuid)
    }

    fun filter(string: String): String {
        return string
            .removePrefix("0x")
            .filter { it.isLetterOrDigit() }
    }

    fun deviceIdFromString(string: String): DeviceId {
        if (string.length != deviceUUIDLength * 2)
            throw DeviceIdError.incorrectLength()

        val deviceUUID = deviceUUIDFromHEXString(string)
        return DeviceId(deviceUUID)
    }

    fun hexStringFromDeviceId(deviceId: DeviceId): String {
        return deviceUUIDHexStringFromUUID(deviceId.uuid)
    }

    fun deviceUUIDFromHEXString(string: String): UUID {
        var deviceUUIDString = string.substring(deviceUUIDRange)
        deviceUUIDString = deviceUUIDString.addCharAtIndex('-', 8)
        deviceUUIDString = deviceUUIDString.addCharAtIndex('-', 13)
        deviceUUIDString = deviceUUIDString.addCharAtIndex('-', 18)
        deviceUUIDString = deviceUUIDString.addCharAtIndex('-', 23)

        try {
            val uuid = UUID.fromString(deviceUUIDString)
            return uuid
        } catch (error: RuntimeException) {
            throw DeviceIdError.cannotBeParsed(DeviceIdParserError.uuid())
        }
    }

    fun deviceUUIDHexStringFromUUID(uuid: UUID): String {
        return uuid.toString().replace("-","").lowercase()
    }

    companion object {
//        val manufactureSpecificLength = 18 // Length of correct string to be parsed (in bytes) //16
        val deviceUUIDLength = 16 // Length of correct string to be parsed (in bytes)
        val deviceUUIDRange: IntRange = IntRange(0, deviceUUIDLength * 2 - 1)
    }

}