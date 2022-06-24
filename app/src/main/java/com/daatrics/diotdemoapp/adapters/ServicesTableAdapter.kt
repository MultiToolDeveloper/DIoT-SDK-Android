package com.daatrics.diotdemoapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daatrics.diotdemoapp.R
import com.daatrics.diotdemoapp.diotsdk.data.diotfeature.DIoTFeatureData

class ServicesTableAdapter: RecyclerView.Adapter<ServicesTableAdapterViewHolder>() {

    var features: ArrayList<DIoTFeatureData> = ArrayList()
    var connectionDelegate: ((DIoTFeatureData) -> Unit)? = null
    var writeDelegate: ((DIoTFeatureData) -> Unit)? = null
    var resetDelegate: ((DIoTFeatureData) -> Unit)? = null
    var updateDelegate: ((DIoTFeatureData) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ServicesTableAdapterViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView: View = inflater.inflate(R.layout.item_service, parent, false)
        return ServicesTableAdapterViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ServicesTableAdapterViewHolder, position: Int) {
        val feature = features.get(position)

        holder.serviceName.text = feature.getNameString()
        holder.serviceData.text = feature.getDataString()

        holder.updateButton.setOnClickListener {
            updateDelegate?.let{ it(feature) }
        }

        holder.connectButton.setOnClickListener {
            connectionDelegate?.let{ it(feature) }
        }

        holder.writeButton.setOnClickListener {
            writeDelegate?.let{ it(feature) }
        }

        holder.resetButton.setOnClickListener {
            resetDelegate?.let{ it(feature) }
        }
    }

    override fun getItemCount(): Int {
        return features.size
    }
}

class ServicesTableAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var updateButton: Button
    var connectButton: Button
    var writeButton: Button
    var resetButton: Button
    var serviceName: TextView
    var serviceData: TextView

    init {
        updateButton = itemView.findViewById<View>(R.id.updateButton) as Button
        connectButton = itemView.findViewById<View>(R.id.connectButton) as Button
        writeButton = itemView.findViewById<View>(R.id.writeButton) as Button
        resetButton = itemView.findViewById<View>(R.id.resetButton) as Button
        serviceName = itemView.findViewById<View>(R.id.serviceName) as TextView
        serviceData = itemView.findViewById<View>(R.id.serviceData) as TextView
    }
}