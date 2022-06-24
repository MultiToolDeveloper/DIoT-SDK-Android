package com.daatrics.diotdemoapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daatrics.diotdemoapp.R
import com.daatrics.diotdemoapp.diotsdk.bluetooth.parsers.command_interface.DIoTRateData

class RatesTableAdapter: RecyclerView.Adapter<RatesTableAdapterViewHolder>() {

    var rates: ArrayList<DIoTRateData> = ArrayList()

    var dataDelegate: ((Int) -> Unit)? = null
    var rateDelegate: ((Int) -> Unit)? = null
    var cleanDelegate: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RatesTableAdapterViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView: View = inflater.inflate(R.layout.item_rate, parent, false)
        return RatesTableAdapterViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: RatesTableAdapterViewHolder, position: Int) {
        val rateData = rates.get(position)

        holder.rateNum.text = rateData.rate.toString()
        holder.channelNum.text = "Channel " + rateData.channelNumber.toString()

        holder.dataButton.setOnClickListener {
            dataDelegate?.let{ it(rateData.channelNumber) }
        }

        holder.cleanButton.setOnClickListener {
            cleanDelegate?.let{ it(rateData.channelNumber) }
        }

        holder.rateButton.setOnClickListener {
            rateDelegate?.let{ it(rateData.channelNumber) }
        }
    }

    override fun getItemCount(): Int {
        return rates.size
    }
}

class RatesTableAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var dataButton: Button
    var cleanButton: Button
    var rateButton: Button
    var channelNum: TextView
    var rateNum: TextView

    init {
        dataButton = itemView.findViewById<View>(R.id.dataButton) as Button
        cleanButton = itemView.findViewById<View>(R.id.cleanButton) as Button
        rateButton = itemView.findViewById<View>(R.id.rateButton) as Button
        channelNum = itemView.findViewById<View>(R.id.channelNum) as TextView
        rateNum = itemView.findViewById<View>(R.id.rateNum) as TextView
    }
}