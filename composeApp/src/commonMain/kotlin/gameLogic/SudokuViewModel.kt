package gameLogic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SudokuViewModel: ViewModel() {
    var sudoku by mutableStateOf(Sudoku())

    // update the sudoku object
    private fun update(cells: List<Cell>){
        sudoku = sudoku.copy(
            sudokuGrid = cells
        )
    }

    /**
     * Given a row, a colum, and a new value, update the cell at that position with the new value
     * @param row the number for the row of the sudoku grid
     * @param col the number for the column of the sudoku grid
     * @param newVal the new value for the cell
     */
    fun updateCell(row: Int, col: Int, newVal: Int){
        val newCells = sudoku.updateCell(row, col, newVal)
        update(newCells)
    }
}