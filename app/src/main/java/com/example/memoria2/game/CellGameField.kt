package com.example.memoria2.game

import com.example.memoria2.R

data class CellGameField (
        var number: Int,
        var status: GameProcess.Status
) {

    fun getTitle(): String =
            if (status == GameProcess.Status.CLOSE) "?" else "$number"

    fun getImage(): Int =
            if (status == GameProcess.Status.CLOSE) R.drawable.unknow else number

    fun isVisible(): Boolean =
            status != GameProcess.Status.DELETE

}