package com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.command_interface

import com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.parser_errors.BluetoothCharacteristicParserType
import com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.parser_errors.BluetoothCharacteristicValueParserError
import com.daatrics.diotdemoapp.diotsdk.support.diotextensions.byteArrayToInt
import com.daatrics.diotdemoapp.diotsdk.support.diotextensions.intToByteArray
import com.daatrics.diotdemoapp.diotsdk.data.diotfeature.DIoTFeatureCode
import com.daatrics.diotdemoapp.diotsdk.data.diotfeature.DIoTFeatureData

class CommandInterfaceFeaturesParser {
    companion object {
        fun valueFromByteArray(array: ByteArray): ArrayList<DIoTFeatureData> {
            val features: ArrayList<DIoTFeatureData> = ArrayList()

            if (array.size == 0 || array.size % 5 != 0)
                throw BluetoothCharacteristicValueParserError.cannotParse(BluetoothCharacteristicParserType.commandFeatures)

            for (i in 0 until array.size step 5) {
                val featureCode = DIoTFeatureCode.fromInt(array[i].toInt())
                val data = ByteArray(4)
                for (j in 0..3) {
                    data[j] = array[i + j + 1]
                }
                features.add(DIoTFeatureData.getFeature(featureCode,data))
            }
            return features
        }

        fun byteArrayFromValue(value: DIoTFeatureData): ByteArray {
            return DIoTFeatureData.getData(value)
        }
    }
}

class CommandInterfaceChannelsParser {
    companion object {
        fun valueFromByteArray(array: ByteArray): ArrayList<DIoTChannelData> {
            val channels: ArrayList<DIoTChannelData> = ArrayList()

            if (array.size == 0 || array.size % 2 != 0)
                throw BluetoothCharacteristicValueParserError.cannotParse(BluetoothCharacteristicParserType.commandChannels)

            for (i in 0 until array.size step 2) {
                channels.add(DIoTChannelData(array[i].toInt(), DIoTFeatureCode.fromInt(array[i + 1].toInt())))
            }

            return channels
        }

        fun byteArrayFromValue(value: DIoTChannelData): ByteArray {
            val array = ByteArray(2)
            array[0] = value.channelNumber.toByte()
            array[1] = value.feature.code.toByte()
            return array
        }
    }
}

class CommandInterfaceRatesParser {
    companion object {
        fun valueFromByteArray(array: ByteArray): ArrayList<DIoTRateData> {
            val rates: ArrayList<DIoTRateData> = ArrayList()

            if (array.size == 0 || array.size % 5 != 0)
                throw BluetoothCharacteristicValueParserError.cannotParse(BluetoothCharacteristicParserType.commandFeatures)

            for (i in 0 until array.size step 5) {
                val data = ByteArray(4)
                for (j in 0..3) {
                    data[j] = array[i + j + 1]
                }
                rates.add(DIoTRateData(array[i].toInt(), data.byteArrayToInt()))
            }
            return rates
        }

        fun byteArrayFromValue(value: DIoTRateData): ByteArray {
            val array = ByteArray(5)
            array[0] = value.channelNumber.toByte()
            array[1] = value.rate.intToByteArray()?.let { it[0] } ?: 0x00
            array[2] = value.rate.intToByteArray()?.let { it[1] } ?: 0x00
            array[3] = value.rate.intToByteArray()?.let { it[2] } ?: 0x00
            array[4] = value.rate.intToByteArray()?.let { it[3] } ?: 0x00
            return array
        }
    }
}

data class DIoTChannelData (var channelNumber: Int,
                            var feature: DIoTFeatureCode)

data class DIoTRateData (var channelNumber: Int,
                         var rate: Int)
