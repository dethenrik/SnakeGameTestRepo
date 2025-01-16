package com.example.snakegame

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

data class Position(val x: Int, val y: Int)

class GameViewModel(private val sharedPrefs: SharedPreferences) : ViewModel() {

    private val _snakeBody = MutableStateFlow<List<Position>>(emptyList())
    val snakeBody = _snakeBody

    private val _foodPosition = MutableStateFlow<Position?>(null)
    val foodPosition = _foodPosition

    private val _score = MutableStateFlow(0)
    val score = _score

    private val _isGameOver = MutableStateFlow(false)
    val isGameOver = _isGameOver

    private var direction = Direction.RIGHT
    private val gridSize = 20

    private enum class Direction { UP, DOWN, LEFT, RIGHT }

    fun startGame() {
        viewModelScope.launch {
            // Initialize snake
            _snakeBody.value = listOf(Position(10, 10))
            spawnFood()
            _score.value = 0
            _isGameOver.value = false
            direction = Direction.RIGHT

            // Basic game loop
            while (!_isGameOver.value) {
                delay(300) // slow down snake movement

                moveSnake()
                checkCollision()
            }
        }
    }

    private fun moveSnake() {
        val currentSnake = _snakeBody.value
        if (currentSnake.isEmpty()) return

        val head = currentSnake.first()
        val newHead = when (direction) {
            Direction.UP -> head.copy(y = head.y - 1)
            Direction.DOWN -> head.copy(y = head.y + 1)
            Direction.LEFT -> head.copy(x = head.x - 1)
            Direction.RIGHT -> head.copy(x = head.x + 1)
        }

        val newBody = mutableListOf<Position>(newHead)
        newBody.addAll(currentSnake.dropLast(1)) // move forward
        _snakeBody.value = newBody
    }

    private fun checkCollision() {
        val currentSnake = _snakeBody.value
        val head = currentSnake.first()

        // Check wall collision
        if (head.x < 0 || head.x >= gridSize || head.y < 0 || head.y >= gridSize) {
            _isGameOver.value = true
            return
        }

        // Check self collision
        if (currentSnake.drop(1).contains(head)) {
            _isGameOver.value = true
            return
        }

        // Check food collision
        val currentFood = _foodPosition.value
        if (currentFood != null && head == currentFood) {
            _score.update { it + 1 }
            // grow snake
            val newBody = _snakeBody.value.toMutableList()
            newBody.add(currentFood) // add new segment at tail
            _snakeBody.value = newBody
            spawnFood()
        }
    }

    private fun spawnFood() {
        val x = Random.nextInt(gridSize)
        val y = Random.nextInt(gridSize)
        _foodPosition.value = Position(x, y)
    }

    fun saveHighScore(newScore: Int) {
        // Retrieve existing high score
        val highScore = sharedPrefs.getInt("HIGH_SCORE", 0)
        if (newScore > highScore) {
            sharedPrefs.edit().putInt("HIGH_SCORE", newScore).apply()
        }
    }
}
