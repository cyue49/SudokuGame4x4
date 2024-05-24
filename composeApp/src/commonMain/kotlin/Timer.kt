import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import gameLogic.SudokuViewModel
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun timer(viewModel: SudokuViewModel){
    var seconds by remember { mutableStateOf( 0 ) }
    LaunchedEffect(Unit) {
        while(true) {
            delay(1.seconds)
            seconds = viewModel.getTimeElapsed().toInt()
        }
    }
    Text(
        text = "${seconds/60} min ${seconds%60} sec"
    )
}