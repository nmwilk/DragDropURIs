package com.nmwilkinson.dragndrop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UriListAdapter() : RecyclerView.Adapter<UriViewHolder>() {
    private var data: MutableList<DropDropItems> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UriViewHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return UriViewHolder(row as ViewGroup)
    }

    override fun onBindViewHolder(holder: UriViewHolder, position: Int) {
        holder.uri.text = data[position].uri
        holder.mime.text = data[position].mimeType
    }

    override fun getItemCount(): Int = data.size

    fun addResults(result: List<DropDropItems>) {
        val previousSize = data.size
        data.addAll(result)
        for (i: Int in previousSize until data.size) {
            notifyItemInserted(i)
        }
    }
}

class UriViewHolder(row: ViewGroup) : RecyclerView.ViewHolder(row) {
    val uri: TextView = row.findViewById(R.id.uri)
    val mime: TextView = row.findViewById(R.id.mime)
}