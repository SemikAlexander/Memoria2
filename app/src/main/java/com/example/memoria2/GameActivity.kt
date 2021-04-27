package com.example.memoria2

import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.memoria2.adapters.GameFieldAdapter
import com.example.memoria2.databinding.ActivityGameBinding


class GameActivity : AppCompatActivity(), GameFieldAdapter.OnItemClickListener {
    lateinit var binding: ActivityGameBinding

    private var size = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref = getSharedPreferences("setting", Context.MODE_PRIVATE)
        if (pref.getString("mode", null).toString() == "night")
            setTheme(R.style.Theme_Memoria2Night)
        else
            setTheme(R.style.Theme_Memoria2)

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        binding.apply {
            when (intent.getStringExtra("difficult")) {
                "easy" -> size = 4
                "medium" -> size = 6
                "hard" -> size = 8
            }

            val topicGame = intent.getStringExtra("topic").toString()

            chronometer.base = SystemClock.elapsedRealtime()
            chronometer.start()

            val data = mutableListOf<String>()

            for (i in 0 until (size * size))
                data.add("?")

            gameField.layoutManager = GridLayoutManager(this@GameActivity, size)
            gameField.adapter = GameFieldAdapter(data.toTypedArray(), this@GameActivity)
        }
    }

    override fun onItemClick(position: Int) {
        toast("$position")
    }
}