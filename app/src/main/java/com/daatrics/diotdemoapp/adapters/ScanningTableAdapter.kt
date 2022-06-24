package com.daatrics.diotdemoapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daatrics.diotdemoapp.R
import com.daatrics.diotdemoapp.diotsdk.bluetooth.diotbluetoothdevices.diot.DIoTBluetoothDevice

class ScanningTableAdapter: RecyclerView.Adapter<ScanningTableAdapterViewHolder>() {

    var devices: ArrayList<DIoTBluetoothDevice> = ArrayList()
    var scanRssi:ArrayList<Int> = ArrayList()
    var delegate: ((DIoTBluetoothDevice) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScanningTableAdapterViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView: View = inflater.inflate(R.layout.item_scanning, parent, false)
        return ScanningTableAdapterViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ScanningTableAdapterViewHolder, position: Int) {

        val device = devices.get(position)

        holder.textDeviceName.text = device.name + "      rssi: " + scanRssi[position]
        holder.textDeviceId.text = device.deviceId.uuid.toString()
        holder.buttonConnect.setOnClickListener {
            delegate?.let{ it(device) }
        }
    }

    override fun getItemCount(): Int {
        return devices.count()
    }
}

class ScanningTableAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var buttonConnect: Button
    var textDeviceName: TextView
    var textDeviceId: TextView

    init {
        buttonConnect = itemView.findViewById<View>(R.id.buttonConnect) as Button
        textDeviceName = itemView.findViewById<View>(R.id.textDeviceName) as TextView
        textDeviceId = itemView.findViewById<View>(R.id.textDeviceId) as TextView
    }
}