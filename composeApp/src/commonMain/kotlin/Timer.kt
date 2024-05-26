import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import gameLogic.SudokuViewModel
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun timer(viewModel: SudokuViewModel){
    var timer by remember { mutableStateOf( 0 ) }

    LaunchedEffect(Unit) {
        while(true) {
            delay(1.seconds)
            timer = viewModel.getTimeElapsed().toInt()
        }
    }

    val min = timer/60
    val sec = timer%60

    Text(
        text = (if (min<10) "0" else "")  + min + ":" + (if (sec<10) "0" else "") + sec,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF2878FF)
    )
}