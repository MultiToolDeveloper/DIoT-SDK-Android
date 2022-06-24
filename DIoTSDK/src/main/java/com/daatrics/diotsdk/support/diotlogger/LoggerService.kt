package com.daatrics.diotdemoapp.diotsdk.support.diotlogger

import android.content.Context
import android.content.SharedPreferences
import com.daatrics.diotdemoapp.diotsdk.support.diothashtable.DIoTHashTable
import com.daatrics.diotdemoapp.diotsdk.support.diotlogger.delegates.LoggerServiceDelegate
import com.daatrics.diotdemoapp.diotsdk.support.diotlogger.entities.Log
import com.daatrics.diotdemoapp.diotsdk.support.diotlogger.entities.LogParameters
import com.daatrics.diotdemoapp.diotsdk.support.diotlogger.enums.LogType
import com.daatrics.diotdemoapp.diotsdk.support.diotlogger.interfaces.LoggerServiceProtocol
import java.util.*

class LoggerService(val context: Context): LoggerServiceProtocol {

    var subscribers = DIoTHashTable()

    var sharedPreferences: SharedPreferences = context.getSharedPreferences(
        enabledKey,
        Context.MODE_PRIVATE
    )
    var preferencesEditor = sharedPreferences.edit()

    override var isEnabled: Boolean = enabledDefaultValue
        get() {
            return sharedPreferences.getBoolean(enabledKey, enabledDefaultValue)
        }
        set(value) {
            if (field == value) { return }
            field = value

            if (!value) {
                critical("[Logger Service] app logs disabled", null, this.javaClass.name)
                critical("[Logger Service] app logs will be cleaned",null, this.javaClass.name)
            } else {
                critical("[Logger Service] app logs enabled", null, this.javaClass.name)
            }

            preferencesEditor.putBoolean(enabledKey, value)
            preferencesEditor.apply()
        }

    override fun subscribe(subscriber: LoggerServiceDelegate) {
        subscribers.add(subscriber)
    }

    override fun unsubscribe(subscriber: LoggerServiceDelegate) {
        subscribers.remove(subscriber)
    }

    override fun warning(message: String, additionalParameters: LogParameters?, file: String?) {
        add(log(message, file?: "", LogType.warning, additionalParameters))
    }

    override fun critical(message: String, additionalParameters: LogParameters?, file: String?) {
        add(log(message, file?: "", LogType.critical, additionalParameters))
    }

    override fun info(message: String, additionalParameters: LogParameters?, file: String?) {
        add(log(message, file?: "", LogType.info, additionalParameters))
    }

    override fun important(message: String, additionalParameters: LogParameters?, file: String?) {
        add(log(message, file?: "", LogType.important, additionalParameters))
    }

    fun add(log: Log){
        System.out.println(log.logAttributedString())
        subscribers.forEachTyped<LoggerServiceDelegate> { it.didReceive(this, log) }
    }

    fun log(message: String, file: String, type: LogType, additionalParameters: LogParameters?): Log {
        return Log(UUID.randomUUID().toString(), Date(), message, file, type, additionalParameters)
    }

    companion object {
        val enabledKey = "LoggerService.Defaults.isEnabled"
        val enabledDefaultValue = true
    }
}