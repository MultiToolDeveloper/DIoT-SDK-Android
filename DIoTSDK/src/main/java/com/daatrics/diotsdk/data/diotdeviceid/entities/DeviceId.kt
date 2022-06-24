package com.daatrics.diotdemoapp.diotsdk.data.diotdeviceid.entities

import java.util.*

class DeviceId(val uuid: UUID) {
    val description: String
    get() = "uuid: " + uuid.toString()
}