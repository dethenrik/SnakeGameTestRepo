package com.example.snakegame

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MenuScreen(
    onStartGame: () -> Unit,
    onViewHighScores: () -> Unit,
    onAsyncExample: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to Snake!", modifier = Modifier.padding(16.dp))
        Button(onClick = onStartGame, modifier = Modifier.padding(8.dp)) {
            Text("Start Game")
        }
        Button(onClick = onViewHighScores, modifier = Modifier.padding(8.dp)) {
            Text("High Scores")
        }
        Button(onClick = onAsyncExample, modifier = Modifier.padding(8.dp)) {
            Text("Async Example")
        }
    }
}