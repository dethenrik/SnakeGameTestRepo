package com.example.snakegame

import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
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

    // Observe state from the ViewModel
    val snakeBody by viewModel.snakeBody.collectAsState()
    val foodPosition by viewModel.foodPosition.collectAsState()
    val score by viewModel.score.collectAsState()
    val isGameOver by viewModel.isGameOver.collectAsState()

    // If the game is over, store high score and navigate back
    if (isGameOver) {
        viewModel.saveHighScore(score)
        onGameOver()
    }

    // Retrieve the user’s movement type preference
    val MOVEMENT_TYPE_KEY = "MOVEMENT_TYPE"
    val movementType = remember {
        sharedPrefs.getString(MOVEMENT_TYPE_KEY, "Buttons") ?: "Buttons"
    }

    // The main game board
    val gridSize = 20

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Score: $score")

        // If "Swipe", wrap the Box in a pointerInput to detect swipes
        if (movementType == "Swipe") {
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .background(Color.Gray)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume() // consume the gesture so it won't propagate
                            val (dx, dy) = dragAmount
                            if (kotlin.math.abs(dx) > kotlin.math.abs(dy)) {
                                // Horizontal swipe
                                if (dx > 0) {
                                    viewModel.changeDirectionToRight()
                                } else {
                                    viewModel.changeDirectionToLeft()
                                }
                            } else {
                                // Vertical swipe
                                if (dy > 0) {
                                    viewModel.changeDirectionToDown()
                                } else {
                                    viewModel.changeDirectionToUp()
                                }
                            }
                        }
                    },
                contentAlignment = Alignment.TopStart
            ) {
                RenderGameElements(snakeBody, foodPosition, gridSize)
            }
        } else {
            // movementType == "Buttons"
            // No pointerInput, just show the Box
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .background(Color.Gray),
                contentAlignment = Alignment.TopStart
            ) {
                RenderGameElements(snakeBody, foodPosition, gridSize)
            }

            // Show arrow buttons
            Spacer(modifier = Modifier.height(16.dp))
            MovementButtons(
                onUp = { viewModel.changeDirectionToUp() },
                onDown = { viewModel.changeDirectionToDown() },
                onLeft = { viewModel.changeDirectionToLeft() },
                onRight = { viewModel.changeDirectionToRight() }
            )
        }
    }
}

@Composable
fun RenderGameElements(
    snakeBody: List<Position>,
    foodPosition: Position?,
    gridSize: Int
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

// A simple row/column of arrow buttons to control the snake direction
@Composable
fun MovementButtons(
    onUp: () -> Unit,
    onDown: () -> Unit,
    onLeft: () -> Unit,
    onRight: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Up button
        Button(onClick = onUp, modifier = Modifier.padding(8.dp)) {
            Text(text = "Up")
        }
        Row {
            Button(onClick = onLeft, modifier = Modifier.padding(8.dp)) {
                Text(text = "Left")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = onRight, modifier = Modifier.padding(8.dp)) {
                Text(text = "Right")
            }
        }
        Button(onClick = onDown, modifier = Modifier.padding(8.dp)) {
            Text(text = "Down")
        }
    }
}
