
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import cafe.adriel.voyager.navigator.Navigator

fun main() = application {
    val windowState = rememberWindowState(width = 800.dp, height = 700.dp)
    Window(
        onCloseRequest = ::exitApplication,
        title = "SudokuGame4x4",
        state = windowState
    ) {
        MaterialTheme {
            Surface {
                Navigator(screen = HomeScreen)
            }
        }
    }
}
