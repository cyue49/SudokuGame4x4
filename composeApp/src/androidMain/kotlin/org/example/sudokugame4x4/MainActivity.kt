package org.example.sudokugame4x4

import App
import HomeScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
                HomeScreen { name ->
                    // handle navigation
                    println("Play button clicked with name: $name")
                }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    HomeScreen { name ->
        println("Play button clicked with name: $name")
    }
}