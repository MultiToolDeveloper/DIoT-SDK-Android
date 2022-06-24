package com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.debug

class DebugParser {
    companion object {
        fun valueFromByteArray(array: ByteArray): String {
            return array.toString(Charsets.US_ASCII)
        }

        fun byteArrayFromValue(value: String): ByteArray {
            return value.toByteArray(Charsets.US_ASCII)
        }
    }
}