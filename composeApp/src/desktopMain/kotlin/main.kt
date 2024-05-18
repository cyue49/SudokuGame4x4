import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import gameLogic.SudokuViewModel
import gameLogic.TestApp

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "SudokuGame4x4",
    ) {
//        App()
        TestApp(
            viewModel = SudokuViewModel()
        )
    }
}