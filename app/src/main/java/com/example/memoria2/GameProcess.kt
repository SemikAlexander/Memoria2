package com.example.memoria2

import android.content.Context
import android.content.res.Resources

class GameProcess (
        private val context: Context,
        private val gameCols: Int,
        private val gameRows: Int,
        topic: String
) {
    private val pictureArray: ArrayList<String> = ArrayList()
    private val pictureCollection: String = topic
    private val gameResources: Resources = context.resources

    enum class Status {
        OPEN, CLOSE, DELETE
    }

    private val cardsStatus: ArrayList<Status> = ArrayList()

    fun createGameField(): ArrayList<String> {
        pictureArray.clear()

        for (i in 0 until gameCols * gameRows / 2) {
            pictureArray.add(/*pictureCollection + */i.toString())
            pictureArray.add(/*pictureCollection + */i.toString())
        }

        pictureArray.shuffle()

        return pictureArray
    }

    fun closeCells(cardsStatus: ArrayList<Status>): ArrayList<Status> {
        cardsStatus.clear()
        for (i in 0 until gameCols * gameRows)
            cardsStatus.add(Status.CLOSE)

        return cardsStatus
    }

    fun checkOpenCells(
            cardsStatus: ArrayList<Status>,
            pictureArray:ArrayList<String>
    ): ArrayList<Status> {
        if (cardsStatus.indexOf(Status.OPEN) > -1 && cardsStatus.lastIndexOf(Status.OPEN) > -1){
            val firstImage: Int = cardsStatus.indexOf(Status.OPEN)
            val secondImage: Int = cardsStatus.lastIndexOf(Status.OPEN)
            if (firstImage == secondImage)
                return cardsStatus
            if (pictureArray[firstImage] == pictureArray[secondImage]) {
                cardsStatus[firstImage] = Status.DELETE
                cardsStatus[secondImage] = Status.DELETE
            } else {
                cardsStatus[firstImage] = Status.CLOSE
                cardsStatus[secondImage] = Status.CLOSE
            }
        }
        return cardsStatus
    }

    fun openCell(cardsStatus: ArrayList<Status>, position: Int) {
        if (cardsStatus[position] !== Status.DELETE)
            cardsStatus[position] = Status.OPEN
        return
    }

    fun isGameOver(cardsStatus: ArrayList<Status>): Boolean =
            cardsStatus.indexOf(Status.CLOSE) < 0
}