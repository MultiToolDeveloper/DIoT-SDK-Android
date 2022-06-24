package com.daatrics.diotdemoapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daatrics.diotdemoapp.R
import com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.command_interface.DIoTChannelData

class ChannelsTableAdapter: RecyclerView.Adapter<ChannelsTableAdapterViewHolder>() {

    var channels: ArrayList<DIoTChannelData> = ArrayList()

    var dataDelegate: ((Int) -> Unit)? = null
    var rateDelegate: ((Int) -> Unit)? = null
    var cleanDelegate: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChannelsTableAdapterViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView: View = inflater.inflate(R.layout.item_channel, parent, false)
        return ChannelsTableAdapterViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ChannelsTableAdapterViewHolder, position: Int) {
        val channelData = channels.get(position)

        holder.serviceName.text = channelData.feature.getNameString()
        holder.channelNum.text = "Channel " + channelData.channelNumber.toString()

        holder.dataButton.setOnClickListener {
            dataDelegate?.let{ it(channelData.channelNumber) }
        }

        holder.rateButton.setOnClickListener {
            rateDelegate?.let{ it(channelData.channelNumber) }
        }

        holder.cleanButton.setOnClickListener {
            cleanDelegate?.let{ it(channelData.channelNumber) }
        }
    }

    override fun getItemCount(): Int {
        return channels.size
    }
}

class ChannelsTableAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var dataButton: Button
    var cleanButton: Button
    var rateButton: Button
    var channelNum: TextView
    var serviceName: TextView

    init {
        dataButton = itemView.findViewById<View>(R.id.dataButton) as Button
        cleanButton = itemView.findViewById<View>(R.id.cleanButton) as Button
        rateButton = itemView.findViewById<View>(R.id.rateButton) as Button
        channelNum = itemView.findViewById<View>(R.id.channelNum) as TextView
        serviceName = itemView.findViewById<View>(R.id.serviceName) as TextView
    }
}