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

    val min = seconds/60
    val sec = seconds%60

    Text(
        text = (if (min<10) "0" else "")  + min + ":" + (if (sec<10) "0" else "") + sec
    )
}