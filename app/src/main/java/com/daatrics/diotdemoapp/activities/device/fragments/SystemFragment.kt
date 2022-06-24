package com.daatrics.diotdemoapp.activities.device.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.daatrics.diotdemoapp.R
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.general_battery.GeneralBatteryBluetoothServiceDelegate
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.general_battery.GeneralBatteryBluetoothServiceProtocol
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.general_devece_information.GeneralDeviceInformationBluetoothServiceDelegate
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.general_devece_information.GeneralDeviceInformationBluetoothServiceProtocol
import com.daatrics.diotdemoapp.activities.DebugActivity
import com.daatrics.diotdemoapp.activities.device.DeviceTabBarActivity
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.diot.DIoTBluetoothDeviceConnectionServiceProtocol
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.general.DIoTBluetoothDeviceConnectionError
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.general.DIoTBluetoothDeviceConnectionServiceDelegate
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class SystemFragment : Fragment(), GeneralDeviceInformationBluetoothServiceDelegate,
    GeneralBatteryBluetoothServiceDelegate,
    DIoTBluetoothDeviceConnectionServiceDelegate {

    lateinit var softwareText: TextView
    lateinit var updateInfoButton: Button
    lateinit var firmwareText: TextView
    lateinit var hardwareText: TextView
    lateinit var deviceText: TextView
    lateinit var manufacturerText: TextView
    lateinit var updateBatteryButton: Button
    lateinit var debugButton: Button
    lateinit var updateSoftwareButton: Button
    lateinit var batteryText: TextView
    lateinit var debugText: TextView
    lateinit var firmwareUpdateText: TextView
    lateinit var rssiText: TextView
    lateinit var updateRSSIButton: Button

    var isActive = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val fragment = inflater.inflate(R.layout.fragment_system, container, false)

        softwareText = fragment.findViewById(R.id.softwareText)
        updateInfoButton = fragment.findViewById(R.id.updateInfoButton)
        firmwareText = fragment.findViewById(R.id.firmwareText)
        hardwareText = fragment.findViewById(R.id.hardwareText)
        deviceText = fragment.findViewById(R.id.deviceText)
        manufacturerText = fragment.findViewById(R.id.manufacturerText)
        updateBatteryButton = fragment.findViewById(R.id.updateBatteryButton)
        debugButton = fragment.findViewById(R.id.debugButton)
        updateSoftwareButton = fragment.findViewById(R.id.updateSoftwareButton)
        batteryText = fragment.findViewById(R.id.batteryText)
        debugText = fragment.findViewById(R.id.debugText)
        firmwareUpdateText = fragment.findViewById(R.id.firmwareUpdateText)
        rssiText = fragment.findViewById(R.id.rssiText)
        updateRSSIButton = fragment.findViewById(R.id.updateRSSIButton)

        return fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateInfoButton.setOnClickListener {
            DeviceTabBarActivity.device?.deviceInformationService?.fetchSoftwareVersion()
            DeviceTabBarActivity.device?.deviceInformationService?.fetchHardwareVersion()
            DeviceTabBarActivity.device?.deviceInformationService?.fetchModelNumber()
            DeviceTabBarActivity.device?.deviceInformationService?.fetchFirmwareVersion()
            DeviceTabBarActivity.device?.deviceInformationService?.fetchManufactureName()
        }
        updateBatteryButton.setOnClickListener {
            DeviceTabBarActivity.device?.batteryService?.fetchBatteryLevel()
        }

        debugButton.setOnClickListener {
            val intent = Intent(requireContext(), DebugActivity::class.java)
            requireContext().startActivity(intent)
        }

        updateRSSIButton.setOnClickListener {
            DeviceTabBarActivity.device?.connectionService?.readRSSI()
        }
    }

    override fun onPause() {
        super.onPause()
        isActive = false
    }

    override fun onResume() {
        super.onResume()
        isActive = true
    }

    //GeneralDeviceInformationBluetoothServiceDelegate
    override fun didReceiveFirmwareRevision(
        service: GeneralDeviceInformationBluetoothServiceProtocol,
        firmwareRevision: String
    ) {
        MainScope().launch {
            if (!this@SystemFragment.isActive) return@launch
            firmwareText.text = "Firmware: " + firmwareRevision
        }
    }

    override fun didReceiveHardwareRevision(
        service: GeneralDeviceInformationBluetoothServiceProtocol,
        hardwareRevision: String
    ) {
        MainScope().launch {
            if (!this@SystemFragment.isActive) return@launch
            hardwareText.text = "Hardware: " + hardwareRevision
        }
    }

    override fun didReceiveSoftwareRevision(
        service: GeneralDeviceInformationBluetoothServiceProtocol,
        softwareRevision: String
    ) {
        MainScope().launch {
            if (!this@SystemFragment.isActive) return@launch
            softwareText.text = "Software: " + softwareRevision
        }
    }

    override fun didReceiveManufactureName(
        service: GeneralDeviceInformationBluetoothServiceProtocol,
        manufactureName: String
    ) {
        MainScope().launch {
            if (!this@SystemFragment.isActive) return@launch
            manufacturerText.text = "Manufacturer: " + manufactureName
        }
    }

    override fun didReceiveModelNumber(
        service: GeneralDeviceInformationBluetoothServiceProtocol,
        modelNumber: String
    ) {
        MainScope().launch {
            if (!this@SystemFragment.isActive) return@launch
            deviceText.text = "Model: " + modelNumber
        }
    }

    //GeneralBatteryBluetoothServiceDelegate
    override fun didReceiveLevel(service: GeneralBatteryBluetoothServiceProtocol, level: Int) {
        MainScope().launch {
            if (!this@SystemFragment.isActive) return@launch
            batteryText.text = "Battery: " + level.toString()
        }
    }

    override fun subscriptionStatusChange(
        service: GeneralBatteryBluetoothServiceProtocol,
        enabled: Boolean
    ) {
        MainScope().launch {
            if (!this@SystemFragment.isActive) return@launch
            Toast.makeText(requireContext(), "Battery subscription: $enabled", Toast.LENGTH_SHORT).show()
        }
    }

    //DIoTBluetoothDeviceConnectionServiceDelegate
    override fun didConnect(service: DIoTBluetoothDeviceConnectionServiceProtocol) {
        //do nothing
    }

    override fun didDisconnect(service: DIoTBluetoothDeviceConnectionServiceProtocol) {
        //do nothing
    }

    override fun didFailToConnect(service: DIoTBluetoothDeviceConnectionServiceProtocol) {
        //do nothing
    }

    override fun didReceiveError(
        service: DIoTBluetoothDeviceConnectionServiceProtocol,
        error: DIoTBluetoothDeviceConnectionError
    ) {
        //do nothing
    }

    override fun didReceiveRSSI(service: DIoTBluetoothDeviceConnectionServiceProtocol, rssi: Int) {
        MainScope().launch {
            if (!this@SystemFragment.isActive) return@launch
            rssiText.text = "RSSI: $rssi"
        }
    }

}