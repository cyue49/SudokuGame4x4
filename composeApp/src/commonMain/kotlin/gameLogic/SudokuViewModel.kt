package gameLogic

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.time.Duration
import java.time.LocalDateTime

class SudokuViewModel: ViewModel() {
    // The current Sudoku object
    var sudoku by mutableStateOf(Sudoku())

    // The currently selected row and column
    var selectedRow by mutableStateOf( -1 )
    var selectedCol by mutableStateOf( -1 )

    // Update the sudoku object with a new list of cells representing the grid
    private fun update(cells: List<Cell>){
        sudoku = sudoku.copy(
            sudokuGrid = cells,
            startTime = sudoku.startTime,
            completeTime = sudoku.completeTime
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
     * Check whether the cell at a position is an input cell or not
     * @return true if the cell is and input cell, false otherwise
     */
    fun checkCellIsInput(row: Int, col: Int): Boolean {
        return sudoku.getCellAt(row,col).isInputField
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

        // set complete time if sudoku grid valid and complete
        if (sudoku.validateGrid()) sudoku.setCompleteTime()
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
        selectedRow = -1
        selectedCol = -1
    }

    /**
     * Reset the sudoku puzzle to a new one
     */
    fun getNewGame(){
        update(List<Cell>(16) { Cell(SudokuValue.EMPTY) })
    }

    /**
     * Validate whether the player's move respect the sudoku's rules
     * @return true if the current state of the sudoku grid respect the sudoku's rules, false otherwise
     */
    fun validateMove(): Boolean {
        return sudoku.validateGrid(ignoreEmpty = true)
    }

    /**
     * Validate whether the current sudoku game is complete and valid
     * @return true if the current sudoku game is complete and valid, false otherwise
     */
    fun validateGame(): Boolean {
        return sudoku.validateGrid()
    }

    /**
     * Get a string of the time taken to solve the sudoku game
     * @return a string of the time taken to solve the sudoku if the start time and complete time have been set, or null otherwise
     */
    fun getSolvingTime(): String? {
        if ((sudoku.startTime != null) && (sudoku.completeTime != null)){
            val timeTaken = Duration.between(sudoku.startTime, sudoku.completeTime).seconds
            val min = timeTaken/60
            val sec = timeTaken%60
            return (if (min<10) "0" else "")  + min + ":" + (if (sec<10) "0" else "") + sec
        }
        return null
    }

    /**
     * Get the time elapsed in seconds since the start of the game
     * @return a long of the time elapsed in seconds since the start of the game
     */
    fun getTimeElapsed(): Long {
        return Duration.between(sudoku.startTime, LocalDateTime.now()).seconds
    }
}