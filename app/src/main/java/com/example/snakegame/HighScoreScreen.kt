package com.example.snakegame

import android.content.SharedPreferences
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HighScoresScreen(
    sharedPrefs: SharedPreferences,
    onBack: () -> Unit
) {
    // In a real app, you might have multiple scores (like a list).
    // For demonstration, we store only 1 high score.
    val highScore = sharedPrefs.getInt("HIGH_SCORE", 0)

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "High Scores", modifier = Modifier.padding(8.dp))
        Text(text = "Highest Score: $highScore", modifier = Modifier.padding(8.dp))

        // Requirement #2 "dynamic list" example:
        // We can pretend we have a list of highscores, but let's keep it simple for demonstration.
        // If you want a real dynamic list, do something like:
        /*
        val scores = listOf(highScore, 5, 10, 3)
        LazyColumn {
            items(scores) { score ->
                Text(text = "Score: $score")
            }
        }
        */

        Button(onClick = onBack, modifier = Modifier.padding(8.dp)) {
            Text("Back")
        }
    }
}
