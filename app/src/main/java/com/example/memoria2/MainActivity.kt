package com.example.memoria2

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import com.example.memoria2.databinding.ActivityMainBinding
import com.example.memoria2.game.startActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var level = ""
    private var userTopic = ""
    private var appModeTheme = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref = getSharedPreferences(PrefsKeys.SETTING, Context.MODE_PRIVATE)
        if (pref.getString(PrefsKeys.MODE, null).toString() == PrefsKeys.NIGHT_MODE) {
            appModeTheme = PrefsKeys.NIGHT_MODE
            setTheme(R.style.Theme_Memoria2Night)
        }
        else{
            appModeTheme = PrefsKeys.DAY_MODE
            setTheme(R.style.Theme_Memoria2)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            appMode.setImageResource(if (appModeTheme == PrefsKeys.DAY_MODE) R.drawable.moon else R.drawable.sun)

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
                    putExtra(PrefsKeys.DIFFICULT, level)
                    putExtra(PrefsKeys.TOPIC, userTopic)
                }
            }

            easyButton.setOnClickListener{
                level = PrefsKeys.EASY_GAME_MODE

                editor.putString(PrefsKeys.LEVEL, level)
                editor.apply()

                setLevel(level)
            }

            football.setOnClickListener{
                userTopic = PrefsKeys.FOOTBALL_TOPIC

                editor.putString(PrefsKeys.TOPIC, userTopic)
                editor.apply()

                setTopic(userTopic)
            }

            mediumButton.setOnClickListener{
                level = PrefsKeys.MEDIUM_GAME_MODE

                editor.putString(PrefsKeys.LEVEL, level)
                editor.apply()

                setLevel(level)
            }

            race.setOnClickListener{
                userTopic = PrefsKeys.RACE_TOPIC

                editor.putString(PrefsKeys.TOPIC, userTopic)
                editor.apply()

                setTopic(userTopic)
            }

            hardButton.setOnClickListener{
                level = PrefsKeys.HARD_GAME_MODE

                editor.putString(PrefsKeys.LEVEL, level)
                editor.apply()

                setLevel(level)
            }

            /*animals.setOnClickListener{
                userTopic = PrefsKeys.ANIMALS_TOPIC

                editor.putString(PrefsKeys.TOPIC, userTopic)
                editor.apply()

                setTopic(userTopic)
            }*/

            appMode.setOnClickListener{
                if (appModeTheme == PrefsKeys.DAY_MODE) {
                    appMode.setImageResource(R.drawable.moon)

                    appModeTheme = PrefsKeys.NIGHT_MODE
                    editor.putString(PrefsKeys.MODE, PrefsKeys.NIGHT_MODE)
                }
                else {
                    appMode.setImageResource(R.drawable.sun)

                    appModeTheme = PrefsKeys.DAY_MODE
                    editor.putString(PrefsKeys.MODE, PrefsKeys.DAY_MODE)
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
            //animals.setBackgroundColor(getColorFromAttr(R.attr.colorSecondary))

            when (userTopic) {
                PrefsKeys.FOOTBALL_TOPIC -> football
                        .setBackgroundColor(getColorFromAttr(R.attr.colorSecondaryVariant))
                PrefsKeys.RACE_TOPIC -> race
                        .setBackgroundColor(getColorFromAttr(R.attr.colorSecondaryVariant))
                /*PrefsKeys.ANIMALS_TOPIC -> animals
                        .setBackgroundColor(getColorFromAttr(R.attr.colorSecondaryVariant))*/
            }
        }
    }

    private fun setLevel(level: String) {
        binding.apply {
            easyButton.setBackgroundColor(getColorFromAttr(R.attr.colorSecondary))
            mediumButton.setBackgroundColor(getColorFromAttr(R.attr.colorSecondary))
            hardButton.setBackgroundColor(getColorFromAttr(R.attr.colorSecondary))

            when (level) {
                PrefsKeys.EASY_GAME_MODE -> easyButton
                        .setBackgroundColor(getColorFromAttr(R.attr.colorSecondaryVariant))
                PrefsKeys.MEDIUM_GAME_MODE -> mediumButton
                        .setBackgroundColor(getColorFromAttr(R.attr.colorSecondaryVariant))
                PrefsKeys.HARD_GAME_MODE -> hardButton
                        .setBackgroundColor(getColorFromAttr(R.attr.colorSecondaryVariant))
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