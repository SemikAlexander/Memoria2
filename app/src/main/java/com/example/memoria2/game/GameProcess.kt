package com.example.memoria2.game

import android.content.Context
import com.example.memoria2.R.drawable.*

class GameProcess (
        private val context: Context,
        private val gameCols: Int,
        private val gameRows: Int,
        topic: String
) {
    enum class Status {
        OPEN, CLOSE, DELETE
    }

    var isGameOver = false

    fun createGameField(gameArray: ArrayList<CellGameField>): ArrayList<CellGameField> {
        gameArray.clear()

        val list = listOf(
                football0, football1, football2, football3,
                football4, football5, football6, football7,
                football8, football9, football10, football11,
                football12, football13, football14, football15,
                football16, football17, football18, football19,
                football20, football21, football22, football23,
                football24, football25, football26, football27,
                football28, football29, football30, football31
        )

        for (i in 0 until (gameCols * gameRows / 2)) {
            val item = CellGameField(list[i], Status.CLOSE)
            gameArray.add(item)
        }

        for (i in 0 until (gameCols * gameRows / 2)) {
            val item = CellGameField(list[i], Status.CLOSE)
            gameArray.add(item)
        }

        gameArray.shuffle()

        return gameArray
    }

    fun checkOpenCells(CellGameField: ArrayList<CellGameField>): ArrayList<CellGameField> {
        if (CellGameField.indexOfFirst { it.status == Status.OPEN } > -1
                && CellGameField.indexOfLast { it.status == Status.OPEN } > -1)
        {
            val firstImage: Int = CellGameField.indexOfFirst { it.status == Status.OPEN }
            val secondImage: Int = CellGameField.indexOfLast { it.status == Status.OPEN }
            if (firstImage == secondImage)
                return CellGameField
            if (CellGameField[firstImage].getImage() == CellGameField[secondImage].getImage()) {
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

    fun isGameOver(CellGameField: ArrayList<CellGameField>): Boolean {
        isGameOver = CellGameField.indexOfFirst { it.status == Status.CLOSE } < 0
        return isGameOver
    }
}