package com.example.snakegame

import android.content.SharedPreferences
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun GameScreen(
    sharedPrefs: SharedPreferences,
    onGameOver: () -> Unit
) {
    val viewModel = remember { GameViewModel(sharedPrefs) }

    // Start the game when this Composable enters composition
    LaunchedEffect(Unit) {
        viewModel.startGame()
    }

    // Observing changes to the game state
    val snakeBody by viewModel.snakeBody.collectAsState()
    val foodPosition by viewModel.foodPosition.collectAsState()
    val score by viewModel.score.collectAsState()
    val isGameOver by viewModel.isGameOver.collectAsState()

    // If the game is over, call onGameOver
    if (isGameOver) {
        // store high score, reset if needed
        viewModel.saveHighScore(score)
        onGameOver()
    }

    // The main game board
    // For simplicity, let's define a 20x20 grid
    val gridSize = 20
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Score: $score")

        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.Gray),
            contentAlignment = Alignment.TopStart
        ) {
            // Render the snake
            snakeBody.forEach { pos ->
                Box(
                    modifier = Modifier
                        .offset(
                            x = (pos.x * (300 / gridSize)).dp,
                            y = (pos.y * (300 / gridSize)).dp
                        )
                        .size((300 / gridSize).dp)
                        .background(Color.Green, shape = CircleShape)
                )
            }

            // Render the food
            foodPosition?.let { food ->
                Box(
                    modifier = Modifier
                        .offset(
                            x = (food.x * (300 / gridSize)).dp,
                            y = (food.y * (300 / gridSize)).dp
                        )
                        .size((300 / gridSize).dp)
                        .background(Color.Red, shape = CircleShape)
                )
            }
        }
    }

    // In a real game, you'd capture user input (e.g., gestures or arrow keys) to move the snake.
    // For demonstration, let's just move the snake automatically.
}
