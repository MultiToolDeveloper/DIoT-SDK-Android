package com.daatrics.diotdemoapp.diotsdk.data.diotfeature

import com.daatrics.diotdemoapp.diotsdk.support.diotextensions.*
import java.util.*

sealed class DIoTFeatureData {
    class empty() : DIoTFeatureData()
    data class signalRedLed(val value: Float) : DIoTFeatureData()
    data class signalGreenLed(val value: Float) : DIoTFeatureData()
    data class signalIRLed(val value: Float) : DIoTFeatureData()
    data class heartRate(val value: Int) : DIoTFeatureData()
    data class oxigen(val value: Float) : DIoTFeatureData()
    data class respiratory(val value: Float) : DIoTFeatureData()
    data class temperature(val value: Float) : DIoTFeatureData()
    data class humidity(val value: Float) : DIoTFeatureData()
    data class accX(val value: Float) : DIoTFeatureData()
    data class accY(val value: Float) : DIoTFeatureData()
    data class accZ(val value: Float) : DIoTFeatureData()
    data class gyroX(val value: Float) : DIoTFeatureData()
    data class gyroY(val value: Float) : DIoTFeatureData()
    data class gyroZ(val value: Float) : DIoTFeatureData()
    data class alertFallDetection(val value: Boolean) : DIoTFeatureData()
    data class awakeLastFallAsleepDate(val value: Date) : DIoTFeatureData()
    data class awakeLastAwakeDate(val value: Date) : DIoTFeatureData()
    data class activityStepCounter(val value: Int) : DIoTFeatureData()
    data class noiseLevel(val value: Float) : DIoTFeatureData()
    data class activityLevel(val value: Int) : DIoTFeatureData()
    data class activityLevelDate(val value: Date) : DIoTFeatureData()
    data class activityDistanceTraveled(val value: Float) : DIoTFeatureData()
    data class activityCaloriesBurn(val value: Float) : DIoTFeatureData()
    data class longitude(val value: Float) : DIoTFeatureData()
    data class latitude(val value: Float) : DIoTFeatureData()
    data class altitude(val value: Float) : DIoTFeatureData()
    data class hrAlert(val value: Boolean) : DIoTFeatureData()
    data class hrAlertTriggerTop(val value: Int) : DIoTFeatureData()
    data class hrAlertTriggerBottom(val value: Int) : DIoTFeatureData()
    data class oxygenAlert(val value: Boolean) : DIoTFeatureData()
    data class oxygenAlertTriggerBottom(val value: Float) : DIoTFeatureData()
    data class temperatureAlert(val value: Boolean) : DIoTFeatureData()
    data class temperatureAlertTriggerTop(val value: Float) : DIoTFeatureData()
    data class temperatureAlertTriggerBottom(val value: Float) : DIoTFeatureData()
    data class humidityAlert(val value: Boolean) : DIoTFeatureData()
    data class humidityAlertTriggerTop(val value: Float) : DIoTFeatureData()
    data class humidityAlertTriggerBottom(val value: Float) : DIoTFeatureData()
    data class awakeAlert(val value: Boolean) : DIoTFeatureData()
    data class personAge(val value: Int) : DIoTFeatureData()
    data class personHeight(val value: Float) : DIoTFeatureData()
    data class personWeight(val value: Float) : DIoTFeatureData()
    data class personGender(val value: Boolean) : DIoTFeatureData()
    data class currentDate(val value: Date) : DIoTFeatureData()

    fun getNameString(): String{
        when (this) {
            is empty -> return "empty"
            is signalRedLed -> return "signalRedLed"
            is signalGreenLed -> return "signalGreenLed"
            is signalIRLed -> return "signalIRLed"
            is heartRate -> return "heartRate"
            is oxigen -> return "oxigen"
            is respiratory -> return "respiratory"
            is temperature -> return "temperature"
            is humidity -> return "humidity"
            is accX -> return "accX"
            is accY -> return "accY"
            is accZ -> return "accZ"
            is gyroX -> return "gyroX"
            is gyroY -> return "gyroY"
            is gyroZ -> return "gyroZ"
            is alertFallDetection -> return "alertFallDetection"
            is awakeLastFallAsleepDate -> return "awakeLastFallAsleepDate"
            is awakeLastAwakeDate -> return "awakeLastAwakeDate"
            is activityStepCounter -> return "activityStepCounter"
            is noiseLevel -> return "noiseLevel"
            is activityLevel -> return "activityLevel"
            is activityLevelDate -> return "activityLevelDate"
            is activityDistanceTraveled -> return "activityDistanceTraveled"
            is activityCaloriesBurn -> return "activityCaloriesBurn"
            is longitude -> return "longitude"
            is latitude -> return "latitude"
            is altitude -> return "altitude"
            is hrAlert -> return "hrAlert"
            is hrAlertTriggerTop -> return "hrAlertTriggerTop"
            is hrAlertTriggerBottom -> return "hrAlertTriggerBottom"
            is oxygenAlert -> return "oxygenAlert"
            is oxygenAlertTriggerBottom -> return "oxygenAlertTriggerBottom"
            is temperatureAlert -> return "temperatureAlert"
            is temperatureAlertTriggerTop -> return "temperatureAlertTriggerTop"
            is temperatureAlertTriggerBottom -> return "temperatureAlertTriggerBottom"
            is humidityAlert -> return "humidityAlert"
            is humidityAlertTriggerTop -> return "humidityAlertTriggerTop"
            is humidityAlertTriggerBottom -> return "humidityAlertTriggerBottom"
            is awakeAlert -> return "awakeAlert"
            is personAge -> return "personAge"
            is personHeight -> return "personHeight"
            is personWeight -> return "personWeight"
            is personGender -> return "personGender"
            is currentDate -> return "currentDate"
        }
    }

    fun getDataString(): String{
        when (this) {
            is empty -> return ""
            is signalRedLed -> return "${this.value}"
            is signalGreenLed -> return "${this.value}"
            is signalIRLed -> return "${this.value}"
            is heartRate -> return "${this.value}"
            is oxigen -> return "${this.value}"
            is respiratory -> return "${this.value}"
            is temperature -> return "${this.value}"
            is humidity -> return "${this.value}"
            is accX -> return "${this.value}"
            is accY -> return "${this.value}"
            is accZ -> return "${this.value}"
            is gyroX -> return "${this.value}"
            is gyroY -> return "${this.value}"
            is gyroZ -> return "${this.value}"
            is alertFallDetection -> return "${this.value}"
            is awakeLastFallAsleepDate -> return "${this.value}"
            is awakeLastAwakeDate -> return "${this.value}"
            is activityStepCounter -> return "${this.value}"
            is noiseLevel -> return "${this.value}"
            is activityLevel -> return "${this.value}"
            is activityLevelDate -> return "${this.value}"
            is activityDistanceTraveled -> return "${this.value}"
            is activityCaloriesBurn -> return "${this.value}"
            is longitude -> return "${this.value}"
            is latitude -> return "${this.value}"
            is altitude -> return "${this.value}"
            is hrAlert -> return "${this.value}"
            is hrAlertTriggerTop -> return "${this.value}"
            is hrAlertTriggerBottom -> return "${this.value}"
            is oxygenAlert -> return "${this.value}"
            is oxygenAlertTriggerBottom -> return "${this.value}"
            is temperatureAlert -> return "${this.value}"
            is temperatureAlertTriggerTop -> return "${this.value}"
            is temperatureAlertTriggerBottom -> return "${this.value}"
            is humidityAlert -> return "${this.value}"
            is humidityAlertTriggerTop -> return "${this.value}"
            is humidityAlertTriggerBottom -> return "${this.value}"
            is awakeAlert -> return "${this.value}"
            is personAge -> return "${this.value}"
            is personHeight -> return "${this.value}"
            is personWeight -> return "${this.value}"
            is personGender -> return "${this.value}"
            is currentDate -> return "${this.value}"
        }
    }

    fun getFeatureCode(): DIoTFeatureCode{
        when (this) {
            is empty -> return DIoTFeatureCode.empty
            is signalRedLed -> return DIoTFeatureCode.signalRedLed
            is signalGreenLed -> return DIoTFeatureCode.signalGreenLed
            is signalIRLed -> return DIoTFeatureCode.signalIRLed
            is heartRate -> return DIoTFeatureCode.heartRate
            is oxigen -> return DIoTFeatureCode.oxigen
            is respiratory -> return DIoTFeatureCode.respiratory
            is temperature -> return DIoTFeatureCode.temperature
            is humidity -> return DIoTFeatureCode.humidity
            is accX -> return DIoTFeatureCode.accX
            is accY -> return DIoTFeatureCode.accY
            is accZ -> return DIoTFeatureCode.accZ
            is gyroX -> return DIoTFeatureCode.gyroX
            is gyroY -> return DIoTFeatureCode.gyroY
            is gyroZ -> return DIoTFeatureCode.gyroZ
            is alertFallDetection -> return DIoTFeatureCode.alertFallDetection
            is awakeLastFallAsleepDate -> return DIoTFeatureCode.awakeLastFallAsleepDate
            is awakeLastAwakeDate -> return DIoTFeatureCode.awakeLastAwakeDate
            is activityStepCounter -> return DIoTFeatureCode.activityStepCounter
            is noiseLevel -> return DIoTFeatureCode.noiseLevel
            is activityLevel -> return DIoTFeatureCode.activityLevel
            is activityLevelDate -> return DIoTFeatureCode.activityLevelDate
            is activityDistanceTraveled -> return DIoTFeatureCode.activityDistanceTraveled
            is activityCaloriesBurn -> return DIoTFeatureCode.activityCaloriesBurn
            is longitude -> return DIoTFeatureCode.longitude
            is latitude -> return DIoTFeatureCode.latitude
            is altitude -> return DIoTFeatureCode.altitude
            is hrAlert -> return DIoTFeatureCode.hrAlert
            is hrAlertTriggerTop -> return DIoTFeatureCode.hrAlertTriggerTop
            is hrAlertTriggerBottom -> return DIoTFeatureCode.hrAlertTriggerBottom
            is oxygenAlert -> return DIoTFeatureCode.oxygenAlert
            is oxygenAlertTriggerBottom -> return DIoTFeatureCode.oxygenAlertTriggerBottom
            is temperatureAlert -> return DIoTFeatureCode.temperatureAlert
            is temperatureAlertTriggerTop -> return DIoTFeatureCode.temperatureAlertTriggerTop
            is temperatureAlertTriggerBottom -> return DIoTFeatureCode.temperatureAlertTriggerBottom
            is humidityAlert -> return DIoTFeatureCode.humidityAlert
            is humidityAlertTriggerTop -> return DIoTFeatureCode.humidityAlertTriggerTop
            is humidityAlertTriggerBottom -> return DIoTFeatureCode.humidityAlertTriggerBottom
            is awakeAlert -> return DIoTFeatureCode.awakeAlert
            is personAge -> return DIoTFeatureCode.personAge
            is personHeight -> return DIoTFeatureCode.personHeight
            is personWeight -> return DIoTFeatureCode.personWeight
            is personGender -> return DIoTFeatureCode.personGender
            is currentDate -> return DIoTFeatureCode.currentDate
        }
    }

    companion object {
        fun getFeature(featureCode: DIoTFeatureCode, value: ByteArray): DIoTFeatureData {
            when (featureCode) {
                DIoTFeatureCode.empty -> {
                    return empty()
                }
                DIoTFeatureCode.signalRedLed -> {
                    return signalRedLed(value.byteArrayToFloat())
                }
                DIoTFeatureCode.signalGreenLed -> {
                    return signalGreenLed(value.byteArrayToFloat())
                }
                DIoTFeatureCode.signalIRLed -> {
                    return signalIRLed(value.byteArrayToFloat())
                }
                DIoTFeatureCode.heartRate -> {
                    return heartRate(value.byteArrayToInt())
                }
                DIoTFeatureCode.oxigen -> {
                    return oxigen(value.byteArrayToFloat())
                }
                DIoTFeatureCode.respiratory -> {
                    return respiratory(value.byteArrayToFloat())
                }
                DIoTFeatureCode.temperature -> {
                    return temperature(value.byteArrayToFloat())
                }
                DIoTFeatureCode.humidity -> {
                    return humidity(value.byteArrayToFloat())
                }
                DIoTFeatureCode.accX -> {
                    return accX(value.byteArrayToFloat())
                }
                DIoTFeatureCode.accY -> {
                    return accY(value.byteArrayToFloat())
                }
                DIoTFeatureCode.accZ -> {
                    return accZ(value.byteArrayToFloat())
                }
                DIoTFeatureCode.gyroX -> {
                    return gyroX(value.byteArrayToFloat())
                }
                DIoTFeatureCode.gyroY -> {
                    return gyroY(value.byteArrayToFloat())
                }
                DIoTFeatureCode.gyroZ -> {
                    return gyroZ(value.byteArrayToFloat())
                }
                DIoTFeatureCode.alertFallDetection -> {
                    return alertFallDetection(value.byteArrayToBool() ?: false)
                }
                DIoTFeatureCode.awakeLastFallAsleepDate -> {
                    val date = Date(java.time.format.DateTimeFormatter.ISO_INSTANT
                        .format(java.time.Instant.ofEpochSecond(value.byteArrayToInt().toLong())))
                    return awakeLastFallAsleepDate(date)
                }
                DIoTFeatureCode.awakeLastAwakeDate -> {
                    val date = Date(java.time.format.DateTimeFormatter.ISO_INSTANT
                        .format(java.time.Instant.ofEpochSecond(value.byteArrayToInt().toLong())))
                    return awakeLastAwakeDate(date)
                }
                DIoTFeatureCode.activityStepCounter -> {
                    return activityStepCounter(value.byteArrayToInt())
                }
                DIoTFeatureCode.noiseLevel -> {
                    return noiseLevel(value.byteArrayToFloat())
                }
                DIoTFeatureCode.activityLevel -> {
                    return activityLevel(value.byteArrayToInt())
                }
                DIoTFeatureCode.activityLevelDate -> {
                    val date = Date(java.time.format.DateTimeFormatter.ISO_INSTANT
                        .format(java.time.Instant.ofEpochSecond(value.byteArrayToInt().toLong())))
                    return activityLevelDate(date)
                }
                DIoTFeatureCode.activityDistanceTraveled -> {
                    return activityDistanceTraveled(value.byteArrayToFloat())
                }
                DIoTFeatureCode.activityCaloriesBurn -> {
                    return activityCaloriesBurn(value.byteArrayToFloat())
                }
                DIoTFeatureCode.longitude -> {
                    return longitude(value.byteArrayToFloat())
                }
                DIoTFeatureCode.latitude -> {
                    return latitude(value.byteArrayToFloat())
                }
                DIoTFeatureCode.altitude -> {
                    return altitude(value.byteArrayToFloat())
                }
                DIoTFeatureCode.hrAlert -> {
                    return hrAlert(value.byteArrayToBool() ?: false)
                }
                DIoTFeatureCode.hrAlertTriggerTop -> {
                    return hrAlertTriggerTop(value.byteArrayToInt())
                }
                DIoTFeatureCode.hrAlertTriggerBottom -> {
                    return hrAlertTriggerBottom(value.byteArrayToInt())
                }
                DIoTFeatureCode.oxygenAlert -> {
                    return oxygenAlert(value.byteArrayToBool() ?: false)
                }
                DIoTFeatureCode.oxygenAlertTriggerBottom -> {
                    return oxygenAlertTriggerBottom(value.byteArrayToFloat())
                }
                DIoTFeatureCode.temperatureAlert -> {
                    return temperatureAlert(value.byteArrayToBool() ?: false)
                }
                DIoTFeatureCode.temperatureAlertTriggerTop -> {
                    return temperatureAlertTriggerTop(value.byteArrayToFloat())
                }
                DIoTFeatureCode.temperatureAlertTriggerBottom -> {
                    return temperatureAlertTriggerBottom(value.byteArrayToFloat())
                }
                DIoTFeatureCode.humidityAlert -> {
                    return humidityAlert(value.byteArrayToBool() ?: false)
                }
                DIoTFeatureCode.humidityAlertTriggerTop -> {
                    return humidityAlertTriggerTop(value.byteArrayToFloat())
                }
                DIoTFeatureCode.humidityAlertTriggerBottom -> {
                    return humidityAlertTriggerBottom(value.byteArrayToFloat())
                }
                DIoTFeatureCode.awakeAlert -> {
                    return awakeAlert(value.byteArrayToBool() ?: false)
                }
                DIoTFeatureCode.personAge -> {
                    return personAge(value.byteArrayToInt())
                }
                DIoTFeatureCode.personHeight -> {
                    return personHeight(value.byteArrayToFloat())
                }
                DIoTFeatureCode.personWeight -> {
                    return personWeight(value.byteArrayToFloat())
                }
                DIoTFeatureCode.personGender -> {
                    return personGender(value.byteArrayToBool() ?: false)
                }
                DIoTFeatureCode.currentDate -> {
                    val date = Date(java.time.format.DateTimeFormatter.ISO_INSTANT
                        .format(java.time.Instant.ofEpochSecond(value.byteArrayToInt().toLong())))
                    return currentDate(date)
                }
            }
        }

        fun getData(featureData: DIoTFeatureData): ByteArray {

            var data = ByteArray(5)

            when (featureData){
                is empty -> {}
                is signalRedLed -> {
                    val arr = featureData.value.floatToByteArray()
                    data[0] = DIoTFeatureCode.signalRedLed.ordinal.toByte()
                    data[1] =  arr?.let {it[0]} ?: 0x00
                    data[2] =  arr?.let {it[1]} ?: 0x00
                    data[3] =  arr?.let {it[2]} ?: 0x00
                    data[4] =  arr?.let {it[3]} ?: 0x00
                }

                is signalGreenLed -> {
                    val arr = featureData.value.floatToByteArray()
                    data[0] = DIoTFeatureCode.signalGreenLed.ordinal.toByte()
                    data[1] =  arr?.let {it[0]} ?: 0x00
                    data[2] =  arr?.let {it[1]} ?: 0x00
                    data[3] =  arr?.let {it[2]} ?: 0x00
                    data[4] =  arr?.let {it[3]} ?: 0x00
                }

                is signalIRLed -> {
                    val arr = featureData.value.floatToByteArray()
                    data[0] = DIoTFeatureCode.signalIRLed.ordinal.toByte()
                    data[1] =  arr?.let {it[0]} ?: 0x00
                    data[2] =  arr?.let {it[1]} ?: 0x00
                    data[3] =  arr?.let {it[2]} ?: 0x00
                    data[4] =  arr?.let {it[3]} ?: 0x00
                }

                is heartRate -> {
                    val arr = featureData.value.intToByteArray()
                    data[0] = DIoTFeatureCode.heartRate.ordinal.toByte()
                    data[1] =  arr?.let {it[0]} ?: 0x00
                    data[2] =  arr?.let {it[1]} ?: 0x00
                    data[3] =  arr?.let {it[2]} ?: 0x00
                    data[4] =  arr?.let {it[3]} ?: 0x00
                }

                is oxigen -> {
                    val arr = featureData.value.floatToByteArray()
                    data[0] = DIoTFeatureCode.oxigen.ordinal.toByte()
                    data[1] =  arr?.let {it[0]} ?: 0x00
                    data[2] =  arr?.let {it[1]} ?: 0x00
                    data[3] =  arr?.let {it[2]} ?: 0x00
                    data[4] =  arr?.let {it[3]} ?: 0x00
                }

                is respiratory -> {
                    val arr = featureData.value.floatToByteArray()
                    data[0] = DIoTFeatureCode.respiratory.ordinal.toByte()
                    data[1] =  arr?.let {it[0]} ?: 0x00
                    data[2] =  arr?.let {it[1]} ?: 0x00
                    data[3] =  arr?.let {it[2]} ?: 0x00
                    data[4] =  arr?.let {it[3]} ?: 0x00
                }

                is temperature -> {
                    val arr = featureData.value.floatToByteArray()
                    data[0] = DIoTFeatureCode.temperature.ordinal.toByte()
                    data[1] =  arr?.let {it[0]} ?: 0x00
                    data[2] =  arr?.let {it[1]} ?: 0x00
                    data[3] =  arr?.let {it[2]} ?: 0x00
                    data[4] =  arr?.let {it[3]} ?: 0x00
                }

                is humidity -> {
                    val arr = featureData.value.floatToByteArray()
                    data[0] = DIoTFeatureCode.humidity.ordinal.toByte()
                    data[1] =  arr?.let {it[0]} ?: 0x00
                    data[2] =  arr?.let {it[1]} ?: 0x00
                    data[3] =  arr?.let {it[2]} ?: 0x00
                    data[4] =  arr?.let {it[3]} ?: 0x00
                }

                is accX -> {
                    val arr = featureData.value.floatToByteArray()
                    data[0] = DIoTFeatureCode.accX.ordinal.toByte()
                    data[1] =  arr?.let {it[0]} ?: 0x00
                    data[2] =  arr?.let {it[1]} ?: 0x00
                    data[3] =  arr?.let {it[2]} ?: 0x00
                    data[4] =  arr?.let {it[3]} ?: 0x00
                }

                is accY -> {
                    val arr = featureData.value.floatToByteArray()
                    data[0] = DIoTFeatureCode.accY.ordinal.toByte()
                    data[1] =  arr?.let {it[0]} ?: 0x00
                    data[2] =  arr?.let {it[1]} ?: 0x00
                    data[3] =  arr?.let {it[2]} ?: 0x00
                    data[4] =  arr?.let {it[3]} ?: 0x00
                }

                is accZ -> {
                    val arr = featureData.value.floatToByteArray()
                    data[0] = DIoTFeatureCode.accZ.ordinal.toByte()
                    data[1] =  arr?.let {it[0]} ?: 0x00
                    data[2] =  arr?.let {it[1]} ?: 0x00
                    data[3] =  arr?.let {it[2]} ?: 0x00
                    data[4] =  arr?.let {it[3]} ?: 0x00
                }

                is gyroX -> {
                    val arr = featureData.value.floatToByteArray()
                    data[0] = DIoTFeatureCode.gyroX.ordinal.toByte()
                    data[1] =  arr?.let {it[0]} ?: 0x00
                    data[2] =  arr?.let {it[1]} ?: 0x00
                    data[3] =  arr?.let {it[2]} ?: 0x00
                    data[4] =  arr?.let {it[3]} ?: 0x00
                }

                is gyroY -> {
                    val arr = featureData.value.floatToByteArray()
                    data[0] = DIoTFeatureCode.gyroY.ordinal.toByte()
                    data[1] =  arr?.let {it[0]} ?: 0x00
                    data[2] =  arr?.let {it[1]} ?: 0x00
                    data[3] =  arr?.let {it[2]} ?: 0x00
                    data[4] =  arr?.let {it[3]} ?: 0x00
                }

                is gyroZ -> {
                    val arr = featureData.value.floatToByteArray()
                    data[0] = DIoTFeatureCode.gyroZ.ordinal.toByte()
                    data[1] =  arr?.let {it[0]} ?: 0x00
                    data[2] =  arr?.let {it[1]} ?: 0x00
                    data[3] =  arr?.let {it[2]} ?: 0x00
                    data[4] =  arr?.let {it[3]} ?: 0x00
                }

                is alertFallDetection -> {
                    val arr = featureData.value.boolToByteArray()
                    data[0] = DIoTFeatureCode.alertFallDetection.ordinal.toByte()
                    data[1] =  arr[0]
                    data[2] =  arr[1]
                    data[3] =  arr[2]
                    data[4] =  arr[3]
                }

                is awakeLastFallAsleepDate -> {
                    val arr = featureData.value.time.toInt().intToByteArray()
                    data[0] = DIoTFeatureCode.awakeLastFallAsleepDate.ordinal.toByte()
                    data[1] =  arr?.let {it[0]} ?: 0x00
                    data[2] =  arr?.let {it[1]} ?: 0x00
                    data[3] =  arr?.let {it[2]} ?: 0x00
                    data[4] =  arr?.let {it[3]} ?: 0x00
                }

                is awakeLastAwakeDate -> {
                    val arr = featureData.value.time.toInt().intToByteArray()
                    data[0] = DIoTFeatureCode.awakeLastAwakeDate.ordinal.toByte()
                    data[1] =  arr?.let {it[0]} ?: 0x00
                    data[2] =  arr?.let {it[1]} ?: 0x00
                    data[3] =  arr?.let {it[2]} ?: 0x00
                    data[4] =  arr?.let {it[3]} ?: 0x00
                }

                is activityStepCounter -> {
                    val arr = featureData.value.intToByteArray()
                    data[0] = DIoTFeatureCode.activityStepCounter.ordinal.toByte()
                    data[1] =  arr?.let {it[0]} ?: 0x00
                    data[2] =  arr?.let {it[1]} ?: 0x00
                    data[3] =  arr?.let {it[2]} ?: 0x00
                    data[4] =  arr?.let {it[3]} ?: 0x00
                }

                is noiseLevel -> {
                    val arr = featureData.value.floatToByteArray()
                    data[0] = DIoTFeatureCode.noiseLevel.ordinal.toByte()
                    data[1] =  arr?.let {it[0]} ?: 0x00
                    data[2] =  arr?.let {it[1]} ?: 0x00
                    data[3] =  arr?.let {it[2]} ?: 0x00
                    data[4] =  arr?.let {it[3]} ?: 0x00
                }

                is activityLevel -> {
                    val arr = featureData.value.intToByteArray()
                    data[0] = DIoTFeatureCode.activityLevel.ordinal.toByte()
                    data[1] =  arr?.let {it[0]} ?: 0x00
                    data[2] =  arr?.let {it[1]} ?: 0x00
                    data[3] =  arr?.let {it[2]} ?: 0x00
                    data[4] =  arr?.let {it[3]} ?: 0x00
                }

                is activityLevelDate -> {
                    val arr = featureData.value.time.toInt().intToByteArray()
                    data[0] = DIoTFeatureCode.activityLevelDate.ordinal.toByte()
                    data[1] =  arr?.let {it[0]} ?: 0x00
                    data[2] =  arr?.let {it[1]} ?: 0x00
                    data[3] =  arr?.let {it[2]} ?: 0x00
                    data[4] =  arr?.let {it[3]} ?: 0x00
                }

                is activityDistanceTraveled -> {
                    val arr = featureData.value.floatToByteArray()
                    data[0] = DIoTFeatureCode.activityDistanceTraveled.ordinal.toByte()
                    data[1] =  arr?.let {it[0]} ?: 0x00
                    data[2] =  arr?.let {it[1]} ?: 0x00
                    data[3] =  arr?.let {it[2]} ?: 0x00
                    data[4] =  arr?.let {it[3]} ?: 0x00
                }

                is activityCaloriesBurn -> {
                    val arr = featureData.value.floatToByteArray()
                    data[0] = DIoTFeatureCode.activityCaloriesBurn.ordinal.toByte()
                    data[1] =  arr?.let {it[0]} ?: 0x00
                    data[2] =  arr?.let {it[1]} ?: 0x00
                    data[3] =  arr?.let {it[2]} ?: 0x00
                    data[4] =  arr?.let {it[3]} ?: 0x00
                }

                is longitude -> {
                    val arr = featureData.value.floatToByteArray()
                    data[0] = DIoTFeatureCode.longitude.ordinal.toByte()
                    data[1] =  arr?.let {it[0]} ?: 0x00
                    data[2] =  arr?.let {it[1]} ?: 0x00
                    data[3] =  arr?.let {it[2]} ?: 0x00
                    data[4] =  arr?.let {it[3]} ?: 0x00
                }

                is latitude -> {
                    val arr = featureData.value.floatToByteArray()
                    data[0] = DIoTFeatureCode.latitude.ordinal.toByte()
                    data[1] =  arr?.let {it[0]} ?: 0x00
                    data[2] =  arr?.let {it[1]} ?: 0x00
                    data[3] =  arr?.let {it[2]} ?: 0x00
                    data[4] =  arr?.let {it[3]} ?: 0x00
                }

                is altitude -> {
                    val arr = featureData.value.floatToByteArray()
                    data[0] = DIoTFeatureCode.altitude.ordinal.toByte()
                    data[1] = arr?.let { it[0] } ?: 0x00
                    data[2] = arr?.let { it[1] } ?: 0x00
                    data[3] = arr?.let { it[2] } ?: 0x00
                    data[4] = arr?.let { it[3] } ?: 0x00
                }

                is hrAlert -> {
                    val arr = featureData.value.boolToByteArray()
                    data[0] = DIoTFeatureCode.hrAlert.ordinal.toByte()
                    data[1] =  arr[0]
                    data[2] =  arr[1]
                    data[3] =  arr[2]
                    data[4] =  arr[3]
                }

                is hrAlertTriggerTop -> {
                    val arr = featureData.value.intToByteArray()
                    data[0] = DIoTFeatureCode.hrAlertTriggerTop.ordinal.toByte()
                    data[1] =  arr?.let {it[0]} ?: 0x00
                    data[2] =  arr?.let {it[1]} ?: 0x00
                    data[3] =  arr?.let {it[2]} ?: 0x00
                    data[4] =  arr?.let {it[3]} ?: 0x00
                }

                is hrAlertTriggerBottom -> {
                    val arr = featureData.value.intToByteArray()
                    data[0] = DIoTFeatureCode.hrAlertTriggerBottom.ordinal.toByte()
                    data[1] =  arr?.let {it[0]} ?: 0x00
                    data[2] =  arr?.let {it[1]} ?: 0x00
                    data[3] =  arr?.let {it[2]} ?: 0x00
                    data[4] =  arr?.let {it[3]} ?: 0x00
                }

                is oxygenAlert -> {
                    val arr = featureData.value.boolToByteArray()
                    data[0] = DIoTFeatureCode.oxygenAlert.ordinal.toByte()
                    data[1] =  arr[0]
                    data[2] =  arr[1]
                    data[3] =  arr[2]
                    data[4] =  arr[3]
                }

                is oxygenAlertTriggerBottom -> {
                    val arr = featureData.value.floatToByteArray()
                    data[0] = DIoTFeatureCode.oxygenAlertTriggerBottom.ordinal.toByte()
                    data[1] = arr?.let { it[0] } ?: 0x00
                    data[2] = arr?.let { it[1] } ?: 0x00
                    data[3] = arr?.let { it[2] } ?: 0x00
                    data[4] = arr?.let { it[3] } ?: 0x00
                }

                is temperatureAlert -> {
                    val arr = featureData.value.boolToByteArray()
                    data[0] = DIoTFeatureCode.temperatureAlert.ordinal.toByte()
                    data[1] =  arr[0]
                    data[2] =  arr[1]
                    data[3] =  arr[2]
                    data[4] =  arr[3]
                }

                is temperatureAlertTriggerTop -> {
                    val arr = featureData.value.floatToByteArray()
                    data[0] = DIoTFeatureCode.temperatureAlertTriggerTop.ordinal.toByte()
                    data[1] = arr?.let { it[0] } ?: 0x00
                    data[2] = arr?.let { it[1] } ?: 0x00
                    data[3] = arr?.let { it[2] } ?: 0x00
                    data[4] = arr?.let { it[3] } ?: 0x00
                }

                is temperatureAlertTriggerBottom -> {
                    val arr = featureData.value.floatToByteArray()
                    data[0] = DIoTFeatureCode.temperatureAlertTriggerBottom.ordinal.toByte()
                    data[1] = arr?.let { it[0] } ?: 0x00
                    data[2] = arr?.let { it[1] } ?: 0x00
                    data[3] = arr?.let { it[2] } ?: 0x00
                    data[4] = arr?.let { it[3] } ?: 0x00
                }

                is humidityAlert -> {
                    val arr = featureData.value.boolToByteArray()
                    data[0] = DIoTFeatureCode.humidityAlert.ordinal.toByte()
                    data[1] =  arr[0]
                    data[2] =  arr[1]
                    data[3] =  arr[2]
                    data[4] =  arr[3]
                }

                is humidityAlertTriggerTop -> {
                    val arr = featureData.value.floatToByteArray()
                    data[0] = DIoTFeatureCode.humidityAlertTriggerTop.ordinal.toByte()
                    data[1] = arr?.let { it[0] } ?: 0x00
                    data[2] = arr?.let { it[1] } ?: 0x00
                    data[3] = arr?.let { it[2] } ?: 0x00
                    data[4] = arr?.let { it[3] } ?: 0x00
                }

                is humidityAlertTriggerBottom -> {
                    val arr = featureData.value.floatToByteArray()
                    data[0] = DIoTFeatureCode.humidityAlertTriggerBottom.ordinal.toByte()
                    data[1] = arr?.let { it[0] } ?: 0x00
                    data[2] = arr?.let { it[1] } ?: 0x00
                    data[3] = arr?.let { it[2] } ?: 0x00
                    data[4] = arr?.let { it[3] } ?: 0x00
                }

                is awakeAlert -> {
                    val arr = featureData.value.boolToByteArray()
                    data[0] = DIoTFeatureCode.awakeAlert.ordinal.toByte()
                    data[1] =  arr[0]
                    data[2] =  arr[1]
                    data[3] =  arr[2]
                    data[4] =  arr[3]
                }

                is personAge -> {
                    val arr = featureData.value.intToByteArray()
                    data[0] = DIoTFeatureCode.personAge.ordinal.toByte()
                    data[1] =  arr?.let {it[0]} ?: 0x00
                    data[2] =  arr?.let {it[1]} ?: 0x00
                    data[3] =  arr?.let {it[2]} ?: 0x00
                    data[4] =  arr?.let {it[3]} ?: 0x00
                }

                is personHeight -> {
                    val arr = featureData.value.floatToByteArray()
                    data[0] = DIoTFeatureCode.personHeight.ordinal.toByte()
                    data[1] = arr?.let { it[0] } ?: 0x00
                    data[2] = arr?.let { it[1] } ?: 0x00
                    data[3] = arr?.let { it[2] } ?: 0x00
                    data[4] = arr?.let { it[3] } ?: 0x00
                }

                is personWeight -> {
                    val arr = featureData.value.floatToByteArray()
                    data[0] = DIoTFeatureCode.personWeight.ordinal.toByte()
                    data[1] = arr?.let { it[0] } ?: 0x00
                    data[2] = arr?.let { it[1] } ?: 0x00
                    data[3] = arr?.let { it[2] } ?: 0x00
                    data[4] = arr?.let { it[3] } ?: 0x00
                }

                is personGender -> {
                    val arr = featureData.value.boolToByteArray()
                    data[0] = DIoTFeatureCode.personGender.ordinal.toByte()
                    data[1] =  arr[0]
                    data[2] =  arr[1]
                    data[3] =  arr[2]
                    data[4] =  arr[3]
                }

                is currentDate -> {
                    val arr = featureData.value.time.toInt().intToByteArray()
                    data[0] = DIoTFeatureCode.currentDate.ordinal.toByte()
                    data[1] =  arr?.let {it[0]} ?: 0x00
                    data[2] =  arr?.let {it[1]} ?: 0x00
                    data[3] =  arr?.let {it[2]} ?: 0x00
                    data[4] =  arr?.let {it[3]} ?: 0x00
                }
            }

            return data
        }
    }
}
