package org.example.sudokugame4x4

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import gameLogic.SudokuViewModel
import gameLogic.TestApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
//            App()
            TestApp(viewModel = SudokuViewModel())
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}