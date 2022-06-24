package com.daatrics.diotdemoapp.diotsdk.data.diotdeviceid.enums

import java.lang.Exception

sealed class DeviceIdParserError : Exception() {
    class uuid(): DeviceIdParserError()
}

sealed class DeviceIdError: Exception()  {
    data class cannotBeParsed(val result: DeviceIdParserError) : DeviceIdError()
    class incorrectLength() : DeviceIdError()
}
