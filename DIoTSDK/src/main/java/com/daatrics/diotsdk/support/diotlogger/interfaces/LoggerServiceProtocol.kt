package com.daatrics.diotdemoapp.diotsdk.support.diotlogger.interfaces

import com.daatrics.diotdemoapp.diotsdk.support.diotlogger.delegates.LoggerServiceDelegate
import com.daatrics.diotdemoapp.diotsdk.support.diotlogger.entities.LogParameters

interface LoggerServiceProtocol {

    var isEnabled: Boolean

    fun subscribe(subscriber: LoggerServiceDelegate)
    fun unsubscribe(subscriber: LoggerServiceDelegate)

    fun warning(message: String, additionalParameters: LogParameters? = null, file: String? = null)
    fun critical(message: String, additionalParameters: LogParameters? = null, file: String? = null)
    fun info(message: String, additionalParameters: LogParameters? = null, file: String? = null)
    fun important(message: String, additionalParameters: LogParameters? = null, file: String? = null)

}