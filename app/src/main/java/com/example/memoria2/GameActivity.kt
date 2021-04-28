package com.example.memoria2

import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.memoria2.adapters.GameFieldAdapter
import com.example.memoria2.databinding.ActivityGameBinding


class GameActivity : AppCompatActivity() {
    lateinit var binding: ActivityGameBinding

    private var size = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref = getSharedPreferences(PrefsKeys.SETTING, Context.MODE_PRIVATE)
        if (pref.getString(PrefsKeys.MODE, null).toString() == "night")
            setTheme(R.style.Theme_Memoria2Night)
        else
            setTheme(R.style.Theme_Memoria2)

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        binding.apply {
            size = when (intent.getStringExtra("difficult")) {
                "easy" -> 4
                "medium" -> 6
                else -> 8
            }

            val topicGame = intent.getStringExtra("topic") ?: ""

            chronometer.base = SystemClock.elapsedRealtime()
            chronometer.start()

            val gameProcess = GameProcess(this@GameActivity, size, size, topicGame)

            val gameFieldArray = gameProcess.createGameField()
            var cardsStatus: ArrayList<GameProcess.Status> = ArrayList()

            cardsStatus = gameProcess.closeCells(cardsStatus)

            gameField.apply {
                layoutManager = GridLayoutManager(context, size)
                adapter = GameFieldAdapter(gameFieldArray, cardsStatus, gameProcess) {
                    if (gameProcess.isGameOver(cardsStatus)) {
                        gameField.visibility = View.GONE
                        gameResult.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}