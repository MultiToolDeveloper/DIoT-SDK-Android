package com.daatrics.diotdemoapp.diotsdk.data.diotfeature

enum class DIoTFeatureCode(val code: Int) {
    empty(0),
    signalRedLed(1),
    signalGreenLed(2),
    signalIRLed(3),
    heartRate(4),
    oxigen(5),
    respiratory(6),
    temperature(7),
    humidity(8),
    accX(9),
    accY(10),
    accZ(11),
    gyroX(12),
    gyroY(13),
    gyroZ(14),
    alertFallDetection(15),
    awakeLastFallAsleepDate(16),
    awakeLastAwakeDate(17),
    activityStepCounter(18),
    noiseLevel(19),
    activityLevel(20),
    activityLevelDate(21),
    activityDistanceTraveled(22),
    activityCaloriesBurn(23),
    longitude(24),
    latitude(25),
    altitude(26),
    hrAlert(27),
    hrAlertTriggerTop(28),
    hrAlertTriggerBottom(29),
    oxygenAlert(30),
    oxygenAlertTriggerBottom(31),
    temperatureAlert(32),
    temperatureAlertTriggerTop(33),
    temperatureAlertTriggerBottom(34),
    humidityAlert(35),
    humidityAlertTriggerTop(36),
    humidityAlertTriggerBottom(37),
    awakeAlert(38),
    personAge(39),
    personHeight(40),
    personWeight(41),
    personGender(42),
    currentDate(43);

    fun getNameString(): String{
        when (this) {
            empty -> return "empty"
            signalRedLed -> return "signalRedLed"
            signalGreenLed -> return "signalGreenLed"
            signalIRLed -> return "signalIRLed"
            heartRate -> return "heartRate"
            oxigen -> return "oxigen"
            respiratory -> return "respiratory"
            temperature -> return "temperature"
            humidity -> return "humidity"
            accX -> return "accX"
            accY -> return "accY"
            accZ -> return "accZ"
            gyroX -> return "gyroX"
            gyroY -> return "gyroY"
            gyroZ -> return "gyroZ"
            alertFallDetection -> return "alertFallDetection"
            awakeLastFallAsleepDate -> return "awakeLastFallAsleepDate"
            awakeLastAwakeDate -> return "awakeLastAwakeDate"
            activityStepCounter -> return "activityStepCounter"
            noiseLevel -> return "noiseLevel"
            activityLevel -> return "activityLevel"
            activityLevelDate -> return "activityLevelDate"
            activityDistanceTraveled -> return "activityDistanceTraveled"
            activityCaloriesBurn -> return "activityCaloriesBurn"
            longitude -> return "longitude"
            latitude -> return "latitude"
            altitude -> return "altitude"
            hrAlert -> return "hrAlert"
            hrAlertTriggerTop -> return "hrAlertTriggerTop"
            hrAlertTriggerBottom -> return "hrAlertTriggerBottom"
            oxygenAlert -> return "oxygenAlert"
            oxygenAlertTriggerBottom -> return "oxygenAlertTriggerBottom"
            temperatureAlert -> return "temperatureAlert"
            temperatureAlertTriggerTop -> return "temperatureAlertTriggerTop"
            temperatureAlertTriggerBottom -> return "temperatureAlertTriggerBottom"
            humidityAlert -> return "humidityAlert"
            humidityAlertTriggerTop -> return "humidityAlertTriggerTop"
            humidityAlertTriggerBottom -> return "humidityAlertTriggerBottom"
            awakeAlert -> return "awakeAlert"
            personAge -> return "personAge"
            personHeight -> return "personHeight"
            personWeight -> return "personWeight"
            personGender -> return "personGender"
            currentDate -> return "currentDate"
        }
    }

    companion object {
        fun fromInt(value: Int): DIoTFeatureCode {
            try {
                return DIoTFeatureCode.values().first { it.code == value }
            } catch (e: NoSuchElementException) {
                return DIoTFeatureCode.empty
            }
        }
    }
}