package com.example.snakegame

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun AsyncExampleScreen(onBack: () -> Unit) {
    var result by remember { mutableStateOf("No data yet...") }

    // Launch an async call once when entering this screen
    LaunchedEffect(Unit) {
        // Perform an async call to a public API
        result = fetchRandomData()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Async Example Screen", modifier = Modifier.padding(8.dp))
        Text(text = "Result: $result", modifier = Modifier.padding(8.dp))

        Button(onClick = onBack, modifier = Modifier.padding(8.dp)) {
            Text("Back")
        }
    }
}

/**
 * A simple function to fetch random data fro an API
 */
suspend fun fetchRandomData(): String = withContext(Dispatchers.IO) {
    // e.g., fetch a random dog fact
    val apiUrl = "https://dog.ceo/api/breeds/image/random"
    val url = URL(apiUrl)
    val conn = url.openConnection() as HttpURLConnection
    return@withContext try {
        conn.connect()
        if (conn.responseCode == 200) {
            conn.inputStream.bufferedReader().readText()
        } else {
            "Error: ${conn.responseCode}"
        }
    } catch (e: Exception) {
        "Exception: ${e.message}"
    } finally {
        conn.disconnect()
    }
}
