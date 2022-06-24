package com.daatrics.diotdemoapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.daatrics.diotdemoapp.R
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_debug.DIoTDebugBluetoothServiceDelegate
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_debug.DIoTDebugBluetoothServiceProtocol
import com.daatrics.diotdemoapp.activities.device.DeviceTabBarActivity
import com.daatrics.diotdemoapp.dialogs.waiting.ProgressDialog
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class DebugActivity : AppCompatActivity(), DIoTDebugBluetoothServiceDelegate {

    lateinit var textTerminal: TextView
    lateinit var commandInput: EditText
    lateinit var buttonSend: Button
    lateinit var backButton: Button

    var progressDialog: ProgressDialog = ProgressDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debug)

        textTerminal = findViewById(R.id.textTerminal)
        commandInput = findViewById(R.id.commandInput)
        buttonSend = findViewById(R.id.buttonSend)
        backButton = findViewById(R.id.backButton)

        backButton.setOnClickListener { finish() }

        buttonSend.setOnClickListener {
            DeviceTabBarActivity.device?.debugService?.sendDebug(commandInput.text.toString())
        }

        //show progress bar
        progressDialog.show(supportFragmentManager, "ProgressDialog");
        progressDialog.isCancelable = false
    }

    override fun onPause() {
        super.onPause()
        DeviceTabBarActivity.device?.debugService?.notifyDebug(false)
        DeviceTabBarActivity.device?.debugService?.unsubscribe(this)
    }

    override fun onResume() {
        super.onResume()
        DeviceTabBarActivity.device?.debugService?.subscribe(this)
        DeviceTabBarActivity.device?.debugService?.notifyDebug(true)
    }

    //DIoTDebugBluetoothServiceDelegate
    override fun didReceiveLogs(service: DIoTDebugBluetoothServiceProtocol, logs: String) {
        MainScope().launch {
            if (this@DebugActivity.isDestroyed) return@launch
            textTerminal.text = textTerminal.text.toString() + logs
        }
    }

    override fun subscriptionStatusChange(
        service: DIoTDebugBluetoothServiceProtocol,
        enabled: Boolean
    ) {
        MainScope().launch {
            if (this@DebugActivity.isDestroyed) return@launch
            Toast.makeText(this@DebugActivity, "Debug subscription: $enabled", Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
        }
    }

}