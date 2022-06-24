package com.daatrics.diotdemoapp.diotsdk.support.diotlogger.entities

import android.graphics.Color
import com.daatrics.diotdemoapp.diotsdk.support.diotlogger.enums.LogType
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

typealias LogParameters = HashMap<String, Any>

class Log (
    val uid: String,
    val createdAt: Date,
    val message: String,
    val unit: String,
    val type: LogType,
    val additionalParameters: LogParameters?){

    val logMessageString: String
        get() = type.string() + " | " + unit + " ] -> " + message

    fun logAttributedString() : String {
        val timeString = dateString() + " " + timeString()
        var string = "< " + timeString + " >" + " " + logMessageString

        var stringOut = ""
        when (type.color()){
            Color.valueOf(Color.RED) -> { stringOut = ANSI_RED + string + ANSI_RESET}
            Color.valueOf(Color.YELLOW) -> { stringOut = ANSI_YELLOW + string + ANSI_RESET}
            Color.valueOf(Color.BLACK) -> { stringOut = ANSI_BLACK+ string + ANSI_RESET}
            Color.valueOf(Color.MAGENTA) -> { stringOut = ANSI_PURPLE+ string + ANSI_RESET}
            else -> { stringOut = ANSI_BLACK + string + ANSI_RESET}
        }

        return stringOut
    }

    fun internalDebugLogString(): String {
        val timeString = dateString() + " " + timeString()
        var string = "< " + timeString + " >" + " " + logMessageString

        additionalParameters?.let { string += additionalParameters }

        return string
    }

    fun debugLogStringToHumanFormatExport(): String {
        val timeZone = TimeZone.getTimeZone("UTC")

        val dateString = dateString(timeZone)
        val timeString = timeString(timeZone)
        val timeZoneString = timeZoneString(timeZone)

        return debugLogString(dateString, timeString, timeZoneString, type, unit, message, additionalParameters)
    }

    fun debugLogStringToExport(): HashMap<String, Any> {
        var diffGMT = TimeZone.getDefault()

        var parameters: HashMap<String, Any> = HashMap()
        parameters.put("createdAt", createdAt)
        parameters.put("diffGMT", diffGMT)
        parameters.put("message", message)
        parameters.put("type", type.string())
        parameters.put("unit", unit)

        additionalParameters?.let{ parameters.putAll(it)}

        return parameters
    }

    fun dateString(timeZone: TimeZone = TimeZone.getDefault()): String {
        val dateFormatter = SimpleDateFormat(dateStringFormat, Locale.getDefault())

        dateFormatter.setTimeZone(timeZone)

        return dateFormatter.format(createdAt)
    }

    fun timeString(timeZone: TimeZone = TimeZone.getDefault()): String {
        val timeFormatter = SimpleDateFormat(timeStringFormat, Locale.getDefault())

        timeFormatter.setTimeZone(timeZone)

        return timeFormatter.format(createdAt)
    }

    fun timeZoneString(timeZone: TimeZone = TimeZone.getDefault()): String {
        val timeFormatter = SimpleDateFormat(timeZoneStringFormat, Locale.getDefault())

        timeFormatter.setTimeZone(timeZone)

        return timeFormatter.format(createdAt)
    }

    fun debugLogString(dateString: String, timeString: String, timeZoneString: String, type: LogType, unit: String, message: String, additionalParameters: LogParameters?): String {
        var string = dateString + ", " + timeString + ", " + timeZoneString + " > [" + type.string() + ", " + unit + " ] -> " + message

        additionalParameters?.let{
            string += ", " + it
        }

        return string
    }

    companion object {
        val dateStringFormat = "dd.MM.yy"
        val timeStringFormat = "HH:mm:ss"
        val timeZoneStringFormat = "z"

        val empty = Log( "",
                        Date(),
                        "",
                        "",
                            LogType.warning,
                        null)

        const val ANSI_RESET = "\u001B[0m"
        const val ANSI_BLACK = "\u001B[30m"
        const val ANSI_RED = "\u001B[31m"
        const val ANSI_GREEN = "\u001B[32m"
        const val ANSI_YELLOW = "\u001B[33m"
        const val ANSI_BLUE = "\u001B[34m"
        const val ANSI_PURPLE = "\u001B[35m"
        const val ANSI_CYAN = "\u001B[36m"
        const val ANSI_WHITE = "\u001B[37m"
    }
}