package com.daatrics.diotdemoapp.diotsdk.bluetooth.extensions

fun String.addCharAtIndex(char: Char, index: Int) =
    StringBuilder(this).apply { insert(index, char) }.toString()