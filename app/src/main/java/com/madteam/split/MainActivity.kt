package com.madteam.split

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.madteam.split.ui.screens.welcome.ui.WelcomeScreen
import com.madteam.split.ui.theme.SplitTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SplitTheme {
                WelcomeScreen()
            }
        }
    }
}