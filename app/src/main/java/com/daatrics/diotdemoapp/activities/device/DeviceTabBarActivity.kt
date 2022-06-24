package com.daatrics.diotdemoapp.activities.device

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import com.daatrics.diotdemoapp.R
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.diot.DIoTBluetoothDevice
import com.daatrics.diotdemoapp.activities.device.fragments.ChannelsFragment
import com.daatrics.diotdemoapp.activities.device.fragments.RatesFragment
import com.daatrics.diotdemoapp.activities.device.fragments.ServicesFragment
import com.daatrics.diotdemoapp.activities.device.fragments.SystemFragment
import com.daatrics.diotdemoapp.dialogs.waiting.ProgressDialog
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.diot.DIoTBluetoothDeviceConnectionServiceProtocol
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.general.*
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class DeviceTabBarActivity: AppCompatActivity(),
    DIoTBluetoothDeviceConnectionServiceDelegate
{

    lateinit var bottomNavigationView: TabLayout
    lateinit var container: FragmentContainerView
    lateinit var backButton: Button

    val servicesFragment by lazy { ServicesFragment() }
    val channelsFragment by lazy { ChannelsFragment() }
    val ratesFragment by lazy { RatesFragment() }
    val systemFragment by lazy { SystemFragment() }

    var progressDialog: ProgressDialog = ProgressDialog()

    companion object {
        var device: DIoTBluetoothDevice? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_tab_bar)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        container = findViewById(R.id.container)
        backButton = findViewById(R.id.backButton)

        backButton.setOnClickListener { finish() }

        //preset first fragment
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, servicesFragment, "Services")
            .commit()

        bottomNavigationView.addOnTabSelectedListener( object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.text) {
                    "Services" -> {
                        //change a fragment
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.container, servicesFragment, "Services")
                            .commit()
                    }
                    "Channels" -> {
                        //change a fragment
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.container, channelsFragment, "Channels")
                            .commit()
                    }
                    "Rates" -> {
                        //change a fragment
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.container, ratesFragment, "Rates")
                            .commit()
                    }
                    "System" -> {
                        //change a fragment
                        supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.container, systemFragment, "System")
                            .commit()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        //setup connection event and launch BLE connection flow
        device?.connectionService?.subscribe(this)
        device?.connectionService?.connect()

        //show progress bar
        progressDialog.show(supportFragmentManager, "ProgressDialog");
        progressDialog.isCancelable = false
    }

    override fun onDestroy() {
        super.onDestroy()

        device?.connectionService?.disconnect()
        device?.connectionService?.unsubscribe(this)
        device?.connectionService?.unsubscribe(systemFragment)
        device?.commandInterfaceService?.unsubscribe(servicesFragment)
        device?.commandInterfaceService?.unsubscribe(channelsFragment)
        device?.commandInterfaceService?.unsubscribe(ratesFragment)
        device?.batteryService?.unsubscribe(systemFragment)
        device?.deviceInformationService?.unsubscribe(systemFragment)
    }

    //DIoTBluetoothDeviceConnectionServiceDelegate
    override fun didReceiveRSSI(service: DIoTBluetoothDeviceConnectionServiceProtocol, rssi: Int) {
        MainScope().launch {
            if (this@DeviceTabBarActivity.isDestroyed) return@launch
            Toast.makeText(this@DeviceTabBarActivity, "DIoT Device RSSI received: $rssi", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("MissingPermission")
    override fun didConnect(service: DIoTBluetoothDeviceConnectionServiceProtocol) {
        MainScope().launch {
            if (this@DeviceTabBarActivity.isDestroyed) return@launch
            Toast.makeText(this@DeviceTabBarActivity, "Setup finished", Toast.LENGTH_SHORT).show()
            progressDialog.dismiss()

            //subscribe fragments
            device?.commandInterfaceService?.subscribe(servicesFragment)
            device?.commandInterfaceService?.subscribe(channelsFragment)
            device?.commandInterfaceService?.subscribe(ratesFragment)
            device?.batteryService?.subscribe(systemFragment)
            device?.deviceInformationService?.subscribe(systemFragment)
            device?.connectionService?.subscribe(systemFragment)

            //initiate first data request
            servicesFragment.updateButton.callOnClick()
        }
    }

    override fun didDisconnect(service: DIoTBluetoothDeviceConnectionServiceProtocol) {
        MainScope().launch {
            if (this@DeviceTabBarActivity.isDestroyed) return@launch
            Toast.makeText(this@DeviceTabBarActivity, "DIoT Device disconnected", Toast.LENGTH_SHORT).show()
            this@DeviceTabBarActivity.finish()
        }
    }

    override fun didFailToConnect(service: DIoTBluetoothDeviceConnectionServiceProtocol) {
        MainScope().launch {
            if (this@DeviceTabBarActivity.isDestroyed) return@launch
            Toast.makeText(this@DeviceTabBarActivity, "DIoT Device connect fail", Toast.LENGTH_SHORT).show()
            this@DeviceTabBarActivity.finish()
        }
    }

    override fun didReceiveError(
        service: DIoTBluetoothDeviceConnectionServiceProtocol,
        error: DIoTBluetoothDeviceConnectionError
    ) {
        MainScope().launch {
            if (this@DeviceTabBarActivity.isDestroyed) return@launch
            Toast.makeText(this@DeviceTabBarActivity, "DIoT Device error", Toast.LENGTH_SHORT).show()
        }
    }

}