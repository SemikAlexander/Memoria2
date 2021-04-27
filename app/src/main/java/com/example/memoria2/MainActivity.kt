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
    private var level = ""
    private var userTopic = ""
    private var appModeTheme = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref = getSharedPreferences(PrefsKeys.SETTING, Context.MODE_PRIVATE)
        if (pref.getString(PrefsKeys.MODE, null).toString() == "night") {
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
            appMode.setImageResource(if (appModeTheme == "day") R.drawable.moon else R.drawable.sun)

            level = pref.getString(PrefsKeys.LEVEL, null).toString()
            userTopic = pref.getString(PrefsKeys.TOPIC, null).toString()
        }
    }

    @SuppressLint("ResourceType")
    override fun onStart() {
        super.onStart()

        val pref = getSharedPreferences(PrefsKeys.SETTING, Context.MODE_PRIVATE)
        val editor = pref.edit()

        setLevel(level = level)
        setTopic(userTopic = userTopic)

        binding.apply {
            startGame.setOnClickListener {
                startActivity<GameActivity> {
                    putExtra("difficult", level)
                    putExtra(PrefsKeys.TOPIC, userTopic)
                }
            }

            easyButton.setOnClickListener{
                level = easyButton.tag.toString()

                editor.putString(PrefsKeys.LEVEL, level)
                editor.apply()

                setLevel(level)
            }

            football.setOnClickListener{
                userTopic = football.tag.toString()

                editor.putString(PrefsKeys.TOPIC, userTopic)
                editor.apply()

                setTopic(userTopic)
            }

            mediumButton.setOnClickListener{
                level = mediumButton.tag.toString()

                editor.putString(PrefsKeys.LEVEL, level)
                editor.apply()

                setLevel(level)
            }

            race.setOnClickListener{
                userTopic = race.tag.toString()

                editor.putString(PrefsKeys.TOPIC, userTopic)
                editor.apply()

                setTopic(userTopic)
            }

            hardButton.setOnClickListener{
                level = hardButton.tag.toString()

                editor.putString(PrefsKeys.LEVEL, level)
                editor.apply()

                setLevel(level)
            }

            animals.setOnClickListener{
                userTopic = animals.tag.toString()

                editor.putString(PrefsKeys.TOPIC, userTopic)
                editor.apply()

                setTopic(userTopic)
            }

            appMode.setOnClickListener{
                if (appModeTheme == "day") {
                    appMode.setImageResource(R.drawable.moon)

                    appModeTheme = "night"
                    editor.putString(PrefsKeys.MODE, "night")
                }
                else {
                    appMode.setImageResource(R.drawable.sun)

                    appModeTheme = "day"
                    editor.putString(PrefsKeys.MODE, "day")
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