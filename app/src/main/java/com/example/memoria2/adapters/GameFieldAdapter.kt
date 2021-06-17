package com.example.memoria2.adapters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memoria2.game.CellGameField
import com.example.memoria2.game.GameProcess
import com.example.memoria2.databinding.RecyclerviewItemBinding
import com.example.memoria2.game.visible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.sqrt


class GameFieldAdapter(
        private var cells: ArrayList<CellGameField>,
        private val gameProcess: GameProcess,
        private val onItemClick: (Int) -> Unit,
        private val onGameOver: () -> Unit
) : RecyclerView.Adapter<GameFieldAdapter.GameViewHolder>() {

    override fun getItemCount() = cells.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GameViewHolder(RecyclerviewItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(cells[position])
    }

    inner class GameViewHolder(
            private val binding: RecyclerviewItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CellGameField) = binding.run {

            itemView.visible(item.isVisible())

            infoText.setImageResource(item.getImage())

            val cardSize = (Resources.getSystem()
                    .displayMetrics.widthPixels / (sqrt(cells.size.toDouble()))).toInt()

            val params: ViewGroup.LayoutParams = itemView.layoutParams
            params.width = cardSize
            params.height = cardSize
            itemView.layoutParams = params

            itemView.setOnClickListener {

                if (adapterPosition != RecyclerView.NO_POSITION)
                    onItemClick.invoke(adapterPosition)

                if (item.status == GameProcess.Status.CLOSE) {
                    cells[adapterPosition].status = GameProcess.Status.OPEN
                    notifyItemChanged(adapterPosition)

                    val openCount = cells
                            .filter { it.status == GameProcess.Status.OPEN }
                            .size

                    if (openCount < 2) return@setOnClickListener

                    GlobalScope.launch {
                        delay(1000)
                        launch(Dispatchers.Main) {
                            cells = gameProcess.checkOpenCells(cells)
                            if (gameProcess.isGameOver(cells))
                                onGameOver.invoke()
                            notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }
}