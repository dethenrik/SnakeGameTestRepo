package com.example.snakegame

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import com.example.snakegame.ui.theme.SnakeGameTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPrefs = getSharedPreferences("snake_prefs", Context.MODE_PRIVATE)

        setContent {
            SnakeGameTheme {
                var currentScreen by remember { mutableStateOf<AppScreen>(AppScreen.Menu) }

                Scaffold { paddingValues ->
                    when (currentScreen) {
                        AppScreen.Menu -> {
                            MenuScreen(
                                onStartGame = { currentScreen = AppScreen.Game },
                                onViewHighScores = { currentScreen = AppScreen.HighScores },
                                onAsyncExample = { currentScreen = AppScreen.AsyncExample },
                                onSettings = { currentScreen = AppScreen.Settings }  // NEW
                            )
                        }
                        AppScreen.Game -> {
                            GameScreen(
                                sharedPrefs = sharedPrefs,
                                onGameOver = { currentScreen = AppScreen.Menu }
                            )
                        }
                        AppScreen.HighScores -> {
                            HighScoresScreen(
                                sharedPrefs = sharedPrefs,
                                onBack = { currentScreen = AppScreen.Menu }
                            )
                        }
                        AppScreen.AsyncExample -> {
                            AsyncExampleScreen(
                                onBack = { currentScreen = AppScreen.Menu }
                            )
                        }
                        AppScreen.Settings -> {
                            SettingsScreen(
                                sharedPrefs = sharedPrefs,
                                onBack = { currentScreen = AppScreen.Menu }
                            )
                        }
                    }
                }
            }
        }
    }
}

// Add the new Settings screen in the sealed class:
sealed class AppScreen {
    object Menu : AppScreen()
    object Game : AppScreen()
    object HighScores : AppScreen()
    object AsyncExample : AppScreen()
    object Settings : AppScreen()  // NEW
}
