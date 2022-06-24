package com.daatrics.diotdemoapp.activities.device.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daatrics.diotdemoapp.R
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_command_interface.DIoTCommandInterfaceBluetoothServiceDelegate
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_command_interface.DIoTCommandInterfaceBluetoothServiceError
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothservices.diot_command_interface.DIoTCommandInterfaceBluetoothServiceProtocol
import com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.command_interface.DIoTChannelData
import com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.command_interface.DIoTRateData
import com.daatrics.diotdemoapp.diotsdk.data.diotfeature.DIoTFeatureData
import com.daatrics.diotdemoapp.activities.DataActivity
import com.daatrics.diotdemoapp.activities.device.DeviceTabBarActivity
import com.daatrics.diotdemoapp.adapters.ChannelsTableAdapter
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class ChannelsFragment : Fragment(), DIoTCommandInterfaceBluetoothServiceDelegate {

    lateinit var updateButton: Button
    lateinit var channelsRecycler: RecyclerView

    var adapter = ChannelsTableAdapter()
    var isActive = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_channels, container, false)

        updateButton = view.findViewById(R.id.updateButton)
        channelsRecycler = view.findViewById(R.id.channelsRecycler)
        channelsRecycler.adapter = adapter
        channelsRecycler.setLayoutManager(LinearLayoutManager(requireContext()))

        updateButton.setOnClickListener {
            DeviceTabBarActivity.device?.commandInterfaceService?.fetchChannels()
        }

        adapter.cleanDelegate = { number ->
            DeviceTabBarActivity.device?.commandInterfaceService?.cleanChannel(number)
        }

        adapter.dataDelegate = { number ->
            DataActivity.channel = number
            val mainIntent = Intent(requireContext(), DataActivity::class.java)
            requireContext().startActivity(mainIntent)
        }

        adapter.rateDelegate = { number ->
            val alert: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            val edittext = EditText(requireContext())
            alert.setMessage("Enter a number in ms")
            alert.setTitle("Set rate")
            edittext.setText("100")

            alert.setView(edittext)

            alert.setPositiveButton("Ok", { dialog, whichButton ->
                val rate = edittext.text.toString().toInt()
                DeviceTabBarActivity.device?.commandInterfaceService?.setRate(number, rate)
            })

            alert.setNegativeButton("Cancel", { dialog, whichButton ->
            })

            alert.show()
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
        //do nothing
    }

    override fun didReceiveCommandChannels(
        service: DIoTCommandInterfaceBluetoothServiceProtocol,
        dataChannels: ArrayList<DIoTChannelData>
    ) {
        MainScope().launch {
            if (!this@ChannelsFragment.isActive) return@launch
            adapter.channels = dataChannels
            adapter.notifyDataSetChanged()
        }
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
            if (!this@ChannelsFragment.isActive) return@launch
            Toast.makeText(requireContext(), "Command interface error or nothing", Toast.LENGTH_SHORT).show()
            adapter.channels.clear()
            adapter.notifyDataSetChanged()
        }
    }

    override fun didWriteCommandFeatures(service: DIoTCommandInterfaceBluetoothServiceProtocol) {
        MainScope().launch {
            if (!this@ChannelsFragment.isActive) return@launch
            Toast.makeText(requireContext(), "Command interface feature write completed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun didWriteCommandChannels(service: DIoTCommandInterfaceBluetoothServiceProtocol) {
        MainScope().launch {
            if (!this@ChannelsFragment.isActive) return@launch
            Toast.makeText(requireContext(), "Command interface channels write completed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun didWriteCommandRate(service: DIoTCommandInterfaceBluetoothServiceProtocol) {
        MainScope().launch {
            if (!this@ChannelsFragment.isActive) return@launch
            Toast.makeText(requireContext(), "Command interface rates write completed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun subscriptionFeaturesStatusChange(
        service: DIoTCommandInterfaceBluetoothServiceProtocol,
        enabled: Boolean
    ) {
        MainScope().launch {
            if (!this@ChannelsFragment.isActive) return@launch
            Toast.makeText(requireContext(), "Command interface features subscription: $enabled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun subscriptionChannelsStatusChange(
        service: DIoTCommandInterfaceBluetoothServiceProtocol,
        enabled: Boolean
    ) {
        MainScope().launch {
            if (!this@ChannelsFragment.isActive) return@launch
            Toast.makeText(requireContext(), "Command interface channels subscription: $enabled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun subscriptionRatesStatusChange(
        service: DIoTCommandInterfaceBluetoothServiceProtocol,
        enabled: Boolean
    ) {
        MainScope().launch {
            if (!this@ChannelsFragment.isActive) return@launch
            Toast.makeText(requireContext(), "Command interface rates subscription: $enabled", Toast.LENGTH_SHORT).show()
        }
    }

}