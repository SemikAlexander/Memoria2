package com.example.memoria2

import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.memoria2.adapters.GameFieldAdapter
import com.example.memoria2.databinding.ActivityGameBinding
import com.example.memoria2.game.CellGameField
import com.example.memoria2.game.GameProcess
import com.example.memoria2.game.toast


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

            var gameFieldArray: ArrayList<CellGameField> = ArrayList()
            gameFieldArray = gameProcess.createGameField(gameFieldArray)

            gameField.apply {
                layoutManager = GridLayoutManager(context, size)
                adapter = GameFieldAdapter(
                        gameFieldArray,
                        gameProcess,
                        onGameOver = {
                            gameField.visibility = View.GONE
                            gameResult.visibility = View.VISIBLE
                        },
                        onItemClick = {
                            if (gameProcess.isGameOver) {
                                gameField.visibility = View.GONE
                                gameResult.visibility = View.VISIBLE
                            }
                        })
            }

            replayButton.setOnClickListener {
                gameResult.visibility = View.GONE
                recreate()
            }

            menuButton.setOnClickListener {
                chronometer.stop()
                finish()
            }
        }
    }
}