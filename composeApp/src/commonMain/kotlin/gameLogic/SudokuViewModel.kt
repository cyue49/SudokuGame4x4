package gameLogic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SudokuViewModel: ViewModel() {
    var sudoku by mutableStateOf(Sudoku())

    private fun update(cells: List<Cell>){
        sudoku = sudoku.copy(
            sudokuGrid = cells
        )
    }

    fun updateCell(row: Int, col: Int, newVal: Int){
        val newCells = sudoku.updateCell(row, col, newVal)
        update(newCells)
    }
}