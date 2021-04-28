package com.example.memoria2

data class CellGameField (
        var number: Int,
        var status: GameProcess.Status
) {

    fun getTitle(): String =
            if (status == GameProcess.Status.CLOSE) "?" else "$number"

    fun isVisible(): Boolean =
            status != GameProcess.Status.DELETE

}