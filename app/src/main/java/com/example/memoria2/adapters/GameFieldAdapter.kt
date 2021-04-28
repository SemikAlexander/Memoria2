package com.example.memoria2.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.memoria2.GameProcess
import com.example.memoria2.R


class GameFieldAdapter(
        private val cardsArray: ArrayList<String>,
        private val cardsStatus: ArrayList<GameProcess.Status>,
        private val gameProcess: GameProcess,
        private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<GameFieldAdapter.GameViewHolder>() {

    override fun getItemCount() = cardsArray.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return GameViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val closeCardsArray = mutableListOf<String>()

        for (i in 0..cardsArray.size)
            closeCardsArray.add("?")

        holder.bind(closeCardsArray[position])
    }

    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var myTextView: TextView = itemView.findViewById(R.id.info_text)

         //todo use binding

        @SuppressLint("SetTextI18n")
        fun bind(item: String) {
            myTextView.text = item
            itemView.setOnClickListener {

                gameProcess.checkOpenCells(cardsStatus, cardsArray)
                gameProcess.openCell(cardsStatus, adapterPosition)

                myTextView.text = cardsArray[adapterPosition]

                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick.invoke(position)
                }
            }
        }
    }
}