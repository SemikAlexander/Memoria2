package com.example.memoria2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.memoria2.R


class GameFieldAdapter(private val values: Array<String>,
                       private val listener: OnItemClickListener) :
        RecyclerView.Adapter<GameFieldAdapter.GameViewHolder>() {

    override fun getItemCount() = values.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return GameViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.myTextView.text = values[position]
    }

    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {
        var myTextView: TextView = itemView.findViewById(R.id.info_text)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}