package com.daatrics.diotdemoapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daatrics.diotdemoapp.R
import com.daatrics.diotdemoapp.diotsdk.data.diotfeature.DIoTFeatureData

class DataTableAdapter: RecyclerView.Adapter<DataTableAdapterViewHolder>() {

    var features: ArrayList<DIoTFeatureData> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataTableAdapterViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView: View = inflater.inflate(R.layout.item_data, parent, false)
        return DataTableAdapterViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: DataTableAdapterViewHolder, position: Int) {
        val feature = features.get(position)

        holder.serviceName.text = feature.getNameString()
        holder.serviceData.text = feature.getDataString()

    }

    override fun getItemCount(): Int {
        return features.size
    }
}

class DataTableAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var serviceName: TextView
    var serviceData: TextView

    init {
        serviceName = itemView.findViewById<View>(R.id.serviceName) as TextView
        serviceData = itemView.findViewById<View>(R.id.serviceData) as TextView
    }
}