package com.example.memoria2

import android.content.Context

class GameProcess (
        private val context: Context,
        private val gameCols: Int,
        private val gameRows: Int,
        topic: String
) {
    enum class Status {
        OPEN, CLOSE, DELETE
    }

    fun createGameField(gameArray: ArrayList<CellGameField>): ArrayList<CellGameField> {
        gameArray.clear()

        for (i in 0 until (gameCols * gameRows / 2)) {
            val item = CellGameField(i, Status.CLOSE)
            gameArray.add(item)
        }

        for (i in 0 until (gameCols * gameRows / 2)) {
            val item = CellGameField(i, Status.CLOSE)
            gameArray.add(item)
        }

        gameArray.shuffle()

        return gameArray
    }

    fun closeCells(CellGameField: ArrayList<CellGameField>): ArrayList<CellGameField> {
        for (i in 0 until CellGameField.size)
            CellGameField[i].status = Status.CLOSE

        return CellGameField
    }

    //
    fun isCardsEqual(CellGameField: ArrayList<CellGameField>): ArrayList<CellGameField> {
        if (CellGameField.indexOfFirst { it.status == Status.OPEN } > -1
                && CellGameField.indexOfLast { it.status == Status.OPEN } > -1)
        {
            val firstImage: Int = CellGameField.indexOfFirst { it.status == Status.OPEN }
            val secondImage: Int = CellGameField.indexOfLast { it.status == Status.OPEN }
            if (firstImage == secondImage)
                return CellGameField
            if (CellGameField[firstImage].getTitle() == CellGameField[secondImage].getTitle()) {
                CellGameField[firstImage].status = Status.DELETE
                CellGameField[secondImage].status = Status.DELETE
                return CellGameField
            } else {
                CellGameField[firstImage].status = Status.CLOSE
                CellGameField[secondImage].status = Status.CLOSE
            }
        }
        return CellGameField
    }

    fun openCell(CellGameField: ArrayList<CellGameField>, position: Int): ArrayList<CellGameField> {
        if (CellGameField[position].status !== Status.DELETE)
            CellGameField[position].status = Status.OPEN
        return CellGameField
    }

    fun isGameOver(CellGameField: ArrayList<CellGameField>): Boolean =
            CellGameField.indexOfFirst { it.status == Status.CLOSE } < 0
}