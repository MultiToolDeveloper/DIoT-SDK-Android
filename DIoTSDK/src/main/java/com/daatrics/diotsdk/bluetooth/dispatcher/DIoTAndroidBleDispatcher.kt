package com.daatrics.diotdemoapp.diotsdk.bluetooth.dispatcher

import com.daatrics.diotdemoapp.diotsdk.support.diotlogger.interfaces.LoggerServiceProtocol
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class DIoTAndroidBleDispatcher(
    private val logger: LoggerServiceProtocol?,
    var delegate: CommandExecutedCallback?
) {
    private val executorService: ExecutorService
    var commandQueue = 0
        private set
    private var enabled = true
    private var timeDelayMs = 2100L //delay due to terrible ble device works in Android 2sec is min

    interface CommandExecutedCallback {
        fun onExecuted(commandQueue: Int?)
    }

    init {
        executorService = Executors.newFixedThreadPool(1)
        logger?.info("Executor created.", null, this.javaClass.name)
    }

    fun destroy() {
        enabled = false
        executorService.shutdown()
        logger?.info("Executor destroyed.", null, this.javaClass.name)
    }

    fun addTask(task: () -> (Unit)): Boolean {

        if (!enabled) return false

        executorService.execute(task)
        commandQueue++
        logger?.info("Added task to executor. Queue size is $commandQueue", null, this.javaClass.name)

        executorService.execute {
            commandQueue--
            logger?.info("Executed task from executor. Queue size is $commandQueue. Waiting for delay $timeDelayMs.", null, this.javaClass.name)

            delegate?.onExecuted(commandQueue)

            TimeUnit.MILLISECONDS.sleep(timeDelayMs)
        }

        return true
    }

    fun setExecutorEnabled(enable: Boolean) {
        enabled = enable
        timeDelayMs = if (enable) 2100 //to have delay
        else 1 //to skip all tasks
    }

    fun isExecutorEnabled(): Boolean {
        return enabled
    }
}