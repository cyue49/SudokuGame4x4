package gameLogic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SudokuViewModel: ViewModel() {
    // The current Sudoku object
    var sudoku by mutableStateOf(Sudoku())

    // The currently selected row and column
    var selectedRow by mutableStateOf( -1 )
    var selectedCol by mutableStateOf( -1 )

    // Update the sudoku object with a new list of cells representing the grid
    private fun update(cells: List<Cell>){
        sudoku = sudoku.copy(
            sudokuGrid = cells
        )
    }

    /**
     * Given a new value, update the currently selected cell with the new value
     * @param newVal the new value for the cell
     */
    fun updateCell(newVal: Int){
        if ((selectedRow in 0..3) && (selectedCol in 0..3)) {
            if (!sudoku.getCellAt(selectedRow, selectedCol).isInputField) return // if not input field don't update
            val newCells = sudoku.updateCell(selectedRow, selectedCol, newVal)
            update(newCells)
        }
    }
}