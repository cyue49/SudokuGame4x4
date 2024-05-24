
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cafe.adriel.voyager.navigator.Navigator

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "SudokuGame4x4",
    ) {
        MaterialTheme {
            Surface {
                Navigator(screen = HomeScreen)
            }
        }
    }
}
