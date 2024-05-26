import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gameLogic.SudokuViewModel

@Composable
fun SudokuGrid(
    viewModel: SudokuViewModel
) {
    var selectedCell by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    val cellBackgroundColor = Color.White
    val selectedCellColor = Color(0xFFFFF176)
    val cellBorderColor = Color.Black
    val cellTextColor = Color.Black

    Box(
    ) {
        Column {
            for (row in 0 until 4) {
                Row {
                    for (col in 0 until 4) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .border(1.dp, cellBorderColor)
                                .background(if (selectedCell == Pair(row, col)) selectedCellColor else cellBackgroundColor)
                                .clickable {
                                    selectedCell = Pair(row, col)
                                    viewModel.selectedRow = row
                                    viewModel.selectedCol = col
                                }
                        ) {
                            Text(
                                text = if ( viewModel.getCellValueAt(row, col) == null) "" else viewModel.getCellValueAt(row, col).toString(),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = (if (viewModel.checkCellIsInput(row, col)) cellTextColor else Color.Blue),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}