package com.daatrics.diotdemoapp.activities.device.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daatrics.diotdemoapp.R
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_command_interface.DIoTCommandInterfaceBluetoothServiceDelegate
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_command_interface.DIoTCommandInterfaceBluetoothServiceError
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_command_interface.DIoTCommandInterfaceBluetoothServiceProtocol
import com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.command_interface.DIoTChannelData
import com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.command_interface.DIoTRateData
import com.daatrics.diotdemoapp.diotsdk.data.diotfeature.DIoTFeatureData
import com.daatrics.diotdemoapp.diotsdk.support.diotextensions.decodeHex
import com.daatrics.diotdemoapp.activities.device.DeviceTabBarActivity
import com.daatrics.diotdemoapp.adapters.ServicesTableAdapter
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class ServicesFragment : Fragment(), DIoTCommandInterfaceBluetoothServiceDelegate {

    lateinit var updateButton: Button
    lateinit var servicesRecycler: RecyclerView

    var adapter = ServicesTableAdapter()
    var isActive = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_services, container, false)

        updateButton = view.findViewById(R.id.updateButton)
        servicesRecycler = view.findViewById(R.id.servicesRecycler)
        servicesRecycler.adapter = adapter
        servicesRecycler.setLayoutManager(LinearLayoutManager(requireContext()))

        updateButton.setOnClickListener {
            DeviceTabBarActivity.device?.commandInterfaceService?.fetchFeatures()
        }

        adapter.connectionDelegate = { featureData ->
            val alert: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            val edittext = EditText(requireContext())
            alert.setMessage("Enter a number 1-9")
            alert.setTitle("Set channel")
            edittext.setText("1")

            alert.setView(edittext)

            alert.setPositiveButton("Ok", { dialog, whichButton ->
                    val number = edittext.text.toString().toInt()
                    DeviceTabBarActivity.device?.commandInterfaceService?.setChannel(number, featureData.getFeatureCode())
                })

            alert.setNegativeButton("Cancel", { dialog, whichButton ->
                })

            alert.show()
        }

        adapter.writeDelegate = { featureData ->
            val alert: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            val edittext = EditText(requireContext())
            alert.setMessage("Enter 4 bytes number in hex")
            alert.setTitle("Set data")
            edittext.setText("00000000")

            alert.setView(edittext)

            alert.setPositiveButton("Ok", { dialog, whichButton ->
                val data = edittext.text.toString().decodeHex()
                if (data.size != 4) return@setPositiveButton
                val featureDataNew = DIoTFeatureData.getFeature(featureData.getFeatureCode(),data)
                DeviceTabBarActivity.device?.commandInterfaceService?.setFeature(featureDataNew)
            })

            alert.setNegativeButton("Cancel", { dialog, whichButton ->
            })

            alert.show()
        }

        adapter.resetDelegate = { featureData ->
            DeviceTabBarActivity.device?.commandInterfaceService?.cleanFeature(featureData.getFeatureCode())
        }

        adapter.updateDelegate = { featureData ->
            DeviceTabBarActivity.device?.commandInterfaceService?.fetchFeature(featureData.getFeatureCode())
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        updateButton.callOnClick()
        isActive = true
    }

    override fun onPause() {
        super.onPause()
        isActive = false
    }

    //DIoTCommandInterfaceBluetoothServiceDelegate
    override fun didReceiveCommandFeatures(
        service: DIoTCommandInterfaceBluetoothServiceProtocol,
        dataFeatures: ArrayList<DIoTFeatureData>
    ) {
        MainScope().launch {
            if (!this@ServicesFragment.isActive) return@launch
            adapter.features = dataFeatures
            adapter.notifyDataSetChanged()
        }
    }

    override fun didReceiveCommandChannels(
        service: DIoTCommandInterfaceBluetoothServiceProtocol,
        dataChannels: ArrayList<DIoTChannelData>
    ) {
        //do nothing
    }

    override fun didReceiveCommandRate(
        service: DIoTCommandInterfaceBluetoothServiceProtocol,
        dataRates: ArrayList<DIoTRateData>
    ) {
        //do nothing
    }

    override fun didReceiveError(
        service: DIoTCommandInterfaceBluetoothServiceProtocol,
        error: DIoTCommandInterfaceBluetoothServiceError
    ) {
        MainScope().launch {
            if (!this@ServicesFragment.isActive) return@launch
            Toast.makeText(requireContext(), "Command interface error or nothing", Toast.LENGTH_SHORT).show()
            adapter.features.clear()
            adapter.notifyDataSetChanged()
        }
    }

    override fun didWriteCommandFeatures(service: DIoTCommandInterfaceBluetoothServiceProtocol) {
        MainScope().launch {
            if (!this@ServicesFragment.isActive) return@launch
            Toast.makeText(requireContext(), "Command interface feature write completed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun didWriteCommandChannels(service: DIoTCommandInterfaceBluetoothServiceProtocol) {
        MainScope().launch {
            if (!this@ServicesFragment.isActive) return@launch
            Toast.makeText(requireContext(), "Command interface channels write completed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun didWriteCommandRate(service: DIoTCommandInterfaceBluetoothServiceProtocol) {
        MainScope().launch {
            if (!this@ServicesFragment.isActive) return@launch
            Toast.makeText(requireContext(), "Command interface rates write completed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun subscriptionFeaturesStatusChange(
        service: DIoTCommandInterfaceBluetoothServiceProtocol,
        enabled: Boolean
    ) {
        MainScope().launch {
            if (!this@ServicesFragment.isActive) return@launch
            Toast.makeText(requireContext(), "Command interface features subscription: $enabled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun subscriptionChannelsStatusChange(
        service: DIoTCommandInterfaceBluetoothServiceProtocol,
        enabled: Boolean
    ) {
        MainScope().launch {
            if (!this@ServicesFragment.isActive) return@launch
            Toast.makeText(requireContext(), "Command interface channels subscription: $enabled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun subscriptionRatesStatusChange(
        service: DIoTCommandInterfaceBluetoothServiceProtocol,
        enabled: Boolean
    ) {
        MainScope().launch {
            if (!this@ServicesFragment.isActive) return@launch
            Toast.makeText(requireContext(), "Command interface rates subscription: $enabled", Toast.LENGTH_SHORT).show()
        }
    }


}