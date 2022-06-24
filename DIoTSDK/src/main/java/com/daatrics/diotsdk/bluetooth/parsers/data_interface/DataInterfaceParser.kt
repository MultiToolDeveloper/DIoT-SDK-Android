package com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.data_interface

import com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.parser_errors.BluetoothCharacteristicParserType
import com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.parser_errors.BluetoothCharacteristicValueParserError
import com.daatrics.diotdemoapp.diotsdk.data.diotfeature.DIoTFeatureCode
import com.daatrics.diotdemoapp.diotsdk.data.diotfeature.DIoTFeatureData

class DataInterfaceParser {
    companion object {
        fun valueFromByteArray(array: ByteArray): ArrayList<DIoTFeatureData> {
            val features: ArrayList<DIoTFeatureData> = ArrayList()

            if (array.size == 0 || array.size % 5 != 0)
                throw BluetoothCharacteristicValueParserError.cannotParse(
                    BluetoothCharacteristicParserType.commandFeatures)

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