package com.daatrics.diotdemoapp.diotsdk.support.diotlogger.enums

import android.graphics.Color

enum class LogType {
    important,
    info,
    warning,
    critical;

    fun string(): String {
        when (this) {
            LogType.critical -> { return "CRITICAL"}
            LogType.info -> { return "INFO"}
            LogType.warning -> { return "WARNING"}
            LogType.important -> { return "IMPROTANT"}
        }
    }

    fun color(): Color {
        when (this) {
            LogType.critical -> { return Color.valueOf(Color.RED) }
            LogType.info -> { return Color.valueOf(Color.BLACK)}
            LogType.warning -> { return Color.valueOf(Color.YELLOW)}
            LogType.important -> { return Color.valueOf(Color.MAGENTA)}
        }
    }
}