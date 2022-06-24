package com.daatrics.diotdemoapp.diotsdk.support.diotlogger.delegates

import com.daatrics.diotdemoapp.diotsdk.support.diotlogger.entities.Log
import com.daatrics.diotdemoapp.diotsdk.support.diotlogger.interfaces.LoggerServiceProtocol

interface LoggerServiceDelegate {
    fun didReceive(logger: LoggerServiceProtocol, log: Log)
}