package com.daatrics.diotdemoapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daatrics.diotdemoapp.R
import com.daatrics.diotdemoapp.activities.device.DeviceTabBarActivity
import com.daatrics.diotdemoapp.adapters.DataTableAdapter
import com.daatrics.diotdemoapp.dialogs.waiting.ProgressDialog
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_data_interface.DIoTDataInterfaceBluetoothServiceDelegate
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_data_interface.DIoTDataInterfaceBluetoothServiceError
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_data_interface.DIoTDataInterfaceBluetoothServiceProtocol
import com.daatrics.diotdemoapp.diotsdk.data.diotfeature.DIoTFeatureData
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class DataActivity : AppCompatActivity(), DIoTDataInterfaceBluetoothServiceDelegate {

    lateinit var dataRecycler: RecyclerView
    lateinit var backButton: Button
    lateinit var channelName: TextView

    var adapter = DataTableAdapter()

    var progressDialog: ProgressDialog = ProgressDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)

        backButton = findViewById(R.id.backButton)
        channelName = findViewById(R.id.channelName)
        dataRecycler = findViewById(R.id.dataRecycler)
        dataRecycler.adapter = adapter
        dataRecycler.setLayoutManager(LinearLayoutManager(this))

        channelName.setText("Channel $channel data:")

        backButton.setOnClickListener { finish() }

        //show progress bar
        progressDialog.show(supportFragmentManager, "ProgressDialog");
        progressDialog.isCancelable = false
    }

    override fun onPause() {
        super.onPause()
        DeviceTabBarActivity.device?.dataInterfaceService?.notifyData(channel, false)
        DeviceTabBarActivity.device?.dataInterfaceService?.unsubscribe(this)
    }

    override fun onResume() {
        super.onResume()
        DeviceTabBarActivity.device?.dataInterfaceService?.subscribe(this)
        DeviceTabBarActivity.device?.dataInterfaceService?.notifyData(channel, true)
    }

    companion object {
        var channel: Int = 0
    }

    //DIoTDataInterfaceBluetoothServiceDelegate
    override fun didReceiveDataChannel(
        service: DIoTDataInterfaceBluetoothServiceProtocol,
        dataFeatures: ArrayList<DIoTFeatureData>,
        channelNumber: Int
    ) {
        MainScope().launch {
            if (this@DataActivity.isDestroyed) return@launch
            adapter.features = dataFeatures
            adapter.notifyDataSetChanged()
        }
    }

    override fun didReceiveError(
        service: DIoTDataInterfaceBluetoothServiceProtocol,
        error: DIoTDataInterfaceBluetoothServiceError
    ) {
        MainScope().launch {
            if (this@DataActivity.isDestroyed) return@launch
            Toast.makeText(this@DataActivity, "Data interface error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun subscriptionDataChannelStatusChange(
        service: DIoTDataInterfaceBluetoothServiceProtocol,
        enabled: Boolean,
        channelNumber: Int
    ) {
        MainScope().launch {
            if (this@DataActivity.isDestroyed) return@launch
            Toast.makeText(this@DataActivity, "Data subscription: $enabled on channel $channelNumber", Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()
        }
    }
}