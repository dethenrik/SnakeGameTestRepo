package com.example.snakegame

import android.content.SharedPreferences
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    sharedPrefs: SharedPreferences,
    onBack: () -> Unit
) {
    // We define a key for the movement type:
    val MOVEMENT_TYPE_KEY = "MOVEMENT_TYPE"

    // Read the current preference from SharedPreferences.
    // Default to "Buttons" if not set.
    var selectedMovement by remember {
        mutableStateOf(
            sharedPrefs.getString(MOVEMENT_TYPE_KEY, "Buttons") ?: "Buttons"
        )
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Settings", modifier = Modifier.padding(bottom = 16.dp))

        // Radio buttons or any UI to pick "Buttons" or "Swipe"
        RowWithRadioButton(
            label = "Use Buttons",
            isSelected = (selectedMovement == "Buttons"),
            onSelect = {
                selectedMovement = "Buttons"
                // Save to SharedPreferences
                sharedPrefs.edit().putString(MOVEMENT_TYPE_KEY, "Buttons").apply()
            }
        )
        RowWithRadioButton(
            label = "Use Swipe",
            isSelected = (selectedMovement == "Swipe"),
            onSelect = {
                selectedMovement = "Swipe"
                // Save to SharedPreferences
                sharedPrefs.edit().putString(MOVEMENT_TYPE_KEY, "Swipe").apply()
            }
        )

        Button(
            onClick = onBack,
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text("Back")
        }
    }
}

@Composable
fun RowWithRadioButton(
    label: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    androidx.compose.foundation.layout.Row(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onSelect
        )
        Text(
            text = label,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
