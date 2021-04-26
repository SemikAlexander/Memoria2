package com.example.memoria2

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import com.example.memoria2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var level = "easy"
    private var userTopic = "football"
    private var appModeTheme = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref = getSharedPreferences("setting", Context.MODE_PRIVATE)
        if (pref.getString("mode", null).toString() == "night") {
            appModeTheme = "night"
            setTheme(R.style.Theme_Memoria2Night)
        }
        else{
            appModeTheme = "day"
            setTheme(R.style.Theme_Memoria2)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            if (appModeTheme == "day")
                appMode.setImageResource(R.drawable.moon)
            else
                appMode.setImageResource(R.drawable.sun)

            setLevel(pref.getString("level", null).toString())
            setTopic(pref.getString("topic", null).toString())
        }
    }

    @SuppressLint("ResourceType")
    override fun onStart() {
        super.onStart()

        val pref = getSharedPreferences("setting", Context.MODE_PRIVATE)
        val editor = pref.edit()

        binding.apply {
            startGame.setOnClickListener {
                startActivity<GameActivity> {
                    putExtra("difficult", level)
                    putExtra("topic", userTopic)
                }
            }

            easyButton.setOnClickListener{
                level = easyButton.tag.toString()
                editor.putString("level", level)
                setLevel(level)
            }

            football.setOnClickListener{
                userTopic = football.tag.toString()
                editor.putString("topic", userTopic)
                setTopic(userTopic)
            }

            mediumButton.setOnClickListener{
                level = mediumButton.tag.toString()
                editor.putString("level", level)
                setLevel(level)
            }

            race.setOnClickListener{
                userTopic = race.tag.toString()
                editor.putString("topic", userTopic)
                setTopic(userTopic)
            }

            hardButton.setOnClickListener{
                level = hardButton.tag.toString()
                editor.putString("level", level)
                setLevel(level)
            }

            animals.setOnClickListener{
                userTopic = animals.tag.toString()
                editor.putString("topic", userTopic)
                setTopic(userTopic)
            }

            appMode.setOnClickListener{
                if (appModeTheme == "day") {
                    appMode.setImageResource(R.drawable.moon)

                    appModeTheme = "night"
                    editor.putString("mode", "night")
                }
                else {
                    appMode.setImageResource(R.drawable.sun)

                    appModeTheme = "day"
                    editor.putString("mode", "day")
                }

                editor.apply()
                recreate()
            }
        }
    }

    private fun setTopic(userTopic: String) {
        binding.apply {
            football.setBackgroundColor(getColorFromAttr(R.attr.colorSecondary))
            race.setBackgroundColor(getColorFromAttr(R.attr.colorSecondary))
            animals.setBackgroundColor(getColorFromAttr(R.attr.colorSecondary))

            when (userTopic) {
                "football" -> football.setBackgroundColor(getColorFromAttr(R.attr.colorSecondaryVariant))
                "race" -> race.setBackgroundColor(getColorFromAttr(R.attr.colorSecondaryVariant))
                "animals" -> animals.setBackgroundColor(getColorFromAttr(R.attr.colorSecondaryVariant))
            }
        }
    }

    private fun setLevel(level: String) {
        binding.apply {
            easyButton.setBackgroundColor(getColorFromAttr(R.attr.colorSecondary))
            mediumButton.setBackgroundColor(getColorFromAttr(R.attr.colorSecondary))
            hardButton.setBackgroundColor(getColorFromAttr(R.attr.colorSecondary))

            when (level) {
                "easy" -> easyButton.setBackgroundColor(getColorFromAttr(R.attr.colorSecondaryVariant))
                "medium" -> mediumButton.setBackgroundColor(getColorFromAttr(R.attr.colorSecondaryVariant))
                "hard" -> hardButton.setBackgroundColor(getColorFromAttr(R.attr.colorSecondaryVariant))
            }
        }
    }

    @ColorInt
    fun Context.getColorFromAttr(
            @AttrRes attrColor: Int,
            typedValue: TypedValue = TypedValue(),
            resolveRefs: Boolean = true
    ): Int {
        theme.resolveAttribute(attrColor, typedValue, resolveRefs)
        return typedValue.data
    }
}