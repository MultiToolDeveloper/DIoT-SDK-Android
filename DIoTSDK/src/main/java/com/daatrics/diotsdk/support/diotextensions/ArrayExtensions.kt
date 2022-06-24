package com.daatrics.diotdemoapp.diotsdk.support.diotextensions

import java.nio.ByteBuffer

fun ByteArray.toHexString(): String {
    var string = ""
    for (byte in this)
        string += java.lang.String.format("%02X", byte)
    return string
}

fun ByteArray.toText(): String {
    var string = ""
    for (byte in this)
        string += java.lang.String.format("%c", byte)
    return string
}

fun Float.floatToByteArray(): ByteArray? {
    var array = ByteBuffer.allocate(4).putFloat(this).array()
    array.reverse()
    return array
}

fun ByteArray.byteArrayToFloat(): Float {
    this.reverse()
    return ByteBuffer.wrap(this).getFloat()
}

fun Int.intToByteArray(): ByteArray? {
    var array = ByteBuffer.allocate(4).putInt(this).array()
    array.reverse()
    return array
}

fun ByteArray.byteArrayToInt(): Int {
    this.reverse()
    return ByteBuffer.wrap(this).getInt()
}

fun ByteArray.byteArrayToBool(): Boolean? {
    if (this.size != 4)
        return null
    val _true: Byte = 0x01
    if (this[0] == _true)
        return true
    return false
}

fun Boolean.boolToByteArray(): ByteArray {
    val array = ByteArray(4)
    val _true: Byte = 0x01
    if (this == true) array[0] = _true
    return array
}

fun String.decodeHex(): ByteArray {
    check(length % 2 == 0) { "Must have an even length" }
    return chunked(2)
        .map { it.toInt(16).toByte() }
        .toByteArray()
}