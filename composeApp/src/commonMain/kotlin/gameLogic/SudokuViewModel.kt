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
     * Given a row and column, get the value of the cell at that position
     * @param row the row number
     * @param col the column number
     * @return The value of the cell at the given row and column (null, 1, 2, 3, or 4)
     */
    fun getCellValueAt(row: Int, col: Int): Int? {
        return sudoku.getCellAt(row, col).value.num
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

    /**
     * Clear all user inputs for the sudoku grid
     */
    fun clearAllCells() {
        for (i in 0..3){
            for (j in 0..3){
                selectedRow = i
                selectedCol = j
                if (sudoku.getCellAt(selectedRow, selectedCol).isInputField) updateCell(0)
            }
        }
    }

    fun getNewGame(){
        update(List<Cell>(16) { Cell(SudokuValue.EMPTY) })
    }
}