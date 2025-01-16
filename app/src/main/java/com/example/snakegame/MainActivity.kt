package com.example.snakegame

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.snakegame.ui.theme.SnakeGameTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtain SharedPreferences for storing high scores, etc.
        val sharedPrefs = getSharedPreferences("snake_prefs", Context.MODE_PRIVATE)

        setContent {
            SnakeGameTheme {
                var currentScreen by remember { mutableStateOf<AppScreen>(AppScreen.Menu) }

                // The main layout with top-app-bar, bottom-nav, etc. if desired
                Scaffold { paddingValues ->
                    when (currentScreen) {
                        AppScreen.Menu -> {
                            MenuScreen(
                                onStartGame = { currentScreen = AppScreen.Game },
                                onViewHighScores = { currentScreen = AppScreen.HighScores },
                                onAsyncExample = { currentScreen = AppScreen.AsyncExample }
                            )
                        }
                        AppScreen.Game -> {
                            // Pass SharedPreferences so we can store high score
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
                            AsyncExampleScreen(onBack = { currentScreen = AppScreen.Menu })
                        }
                    }
                }
            }
        }
    }
}

sealed class AppScreen {
    object Menu : AppScreen()
    object Game : AppScreen()
    object HighScores : AppScreen()
    object AsyncExample : AppScreen()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SnakeGameTheme {
        MenuScreen({}, {}, {})
    }
}
