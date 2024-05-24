import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "SudokuGame4x4",
    ) {
        MaterialTheme {
            Surface {
                HomeScreen { name ->
                    // handle the navigation
                    println("Play button clicked with name: $name")
                }
            }
        }
    }
}