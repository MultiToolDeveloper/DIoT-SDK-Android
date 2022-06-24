package com.daatrics.diotdemoapp.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daatrics.diotdemoapp.R
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.diot.DIoTBluetoothDevice
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothmanager.*
import com.daatrics.diotdemoapp.activities.device.DeviceTabBarActivity
import com.daatrics.diotdemoapp.adapters.ScanningTableAdapter
import com.daatrics.diotdemoapp.diotsdk.bluetooth.DIoTSDK
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ScanningActivity : AppCompatActivity(),
    DIoTBluetoothManagerScanningDelegate,
    DIoTBluetoothManagerStateDelegate {

    lateinit var filterEditText: EditText
    lateinit var scanButton: Button
    lateinit var devicesRecyclerView: RecyclerView

    var adapter = ScanningTableAdapter()
    var isScanning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanning)

        filterEditText = findViewById(R.id.filterEditText)
        scanButton = findViewById(R.id.scanButton)
        devicesRecyclerView = findViewById(R.id.devicesRecyclerView)
        devicesRecyclerView.adapter = adapter
        devicesRecyclerView.setLayoutManager(LinearLayoutManager(this))

        adapter.delegate = {

            DeviceTabBarActivity.device = it

            isScanning = false
            scanButton.text = "Scan"
            DIoTSDK.bluetoothManager?.stopScan()
            adapter.devices.clear()
            adapter.notifyDataSetChanged()

            val mainIntent = Intent(this@ScanningActivity, DeviceTabBarActivity::class.java)
            this@ScanningActivity.startActivity(mainIntent)
        }

        scanButton.setOnClickListener {

            DIoTSDK.bluetoothManager?.fetchBluetoothPowerState()

            isScanning = !isScanning
            if (isScanning) {
                scanButton.text = "Stop"
                DIoTSDK.bluetoothManager?.startScan(null, null)
            } else {
                scanButton.text = "Scan"
                DIoTSDK.bluetoothManager?.stopScan()
                adapter.devices.clear()
                adapter.notifyDataSetChanged()
            }
        }

        requestPermissions()
    }

    override fun onPause() {
        super.onPause()
        DIoTSDK.bluetoothManager?.unsubscribe(this, DIoTBluetoothManagerSubscriptionType.scan)
        DIoTSDK.bluetoothManager?.subscribe(this, DIoTBluetoothManagerSubscriptionType.state)
    }

    override fun onResume() {
        super.onResume()
        DIoTSDK.bluetoothManager?.subscribe(this, DIoTBluetoothManagerSubscriptionType.scan)
        DIoTSDK.bluetoothManager?.subscribe(this, DIoTBluetoothManagerSubscriptionType.state)
    }

    //DIoTBluetoothManagerScanningDelegate
    override fun didDiscoverDevice(
        manager: DIoTBluetoothManagerProtocol,
        device: DIoTBluetoothDevice,
        rssi: Int
    ) {
        MainScope().launch {
            if (this@ScanningActivity.isDestroyed) return@launch

            for (i in 0 until adapter.devices.size)
                if (adapter.devices[i].address == device.address){
                    adapter.scanRssi[i] = rssi
                    adapter.notifyDataSetChanged()
                    return@launch
                }

            val filter = this@ScanningActivity.filterEditText.text.toString()
            if (filter != ""){
                if (!device.deviceId.uuid.toString().contains(filter) && !device.name.contains(filter))
                    return@launch
            }

            Toast.makeText(this@ScanningActivity, "BT scan found", Toast.LENGTH_SHORT).show()

            adapter.devices.add(device)
            adapter.scanRssi.add(rssi)
            adapter.notifyDataSetChanged()
        }
    }

    override fun didReceiveScanningError(
        manager: DIoTBluetoothManagerProtocol,
        error: DIoTBluetoothManagerScanningError
    ) {
        //do nothing
    }

    //Subscribe on ble hardware actions
    override fun bluetoothManagerEnabledBluetooth(manager: DIoTBluetoothManagerProtocol) {
        MainScope().launch {
            if (this@ScanningActivity.isDestroyed) return@launch
            Toast.makeText(this@ScanningActivity, "BT enabled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun bluetoothManagerDisabledBluetooth(manager: DIoTBluetoothManagerProtocol) {
        MainScope().launch {
            if (this@ScanningActivity.isDestroyed) return@launch
            Toast.makeText(this@ScanningActivity, "BT disabled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun bluetoothManagerNotAllowedBluetooth(manager: DIoTBluetoothManagerProtocol) {
        MainScope().launch {
            if (this@ScanningActivity.isDestroyed) return@launch
            Toast.makeText(this@ScanningActivity, "BT not allowed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun bluetoothManagerNoBLESupport(manager: DIoTBluetoothManagerProtocol) {
        MainScope().launch {
            if (this@ScanningActivity.isDestroyed) return@launch
            Toast.makeText(this@ScanningActivity, "BT has no BLE support", Toast.LENGTH_SHORT).show()
        }
    }

    //request permission block shoud be implemented by customer himself
    fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "Permissions BT error", Toast.LENGTH_SHORT).show()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 1)
            }
        } else {
            // Permission has already been granted
            //Toast.makeText(this, "BT Permissions granted", Toast.LENGTH_SHORT).show()
        }
    }
}

