package gameLogic

/**
 * Enum class representing the five possible states of a 4x4 sudoku cell: EMPTY, ONE, TWO, THREE, or FOUR
 */
enum class SudokuValue(val num: Int?) {
    EMPTY(null),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4)
}

/**
 * Data class representing a cell on a sudoku grid
 */
data class Cell (
    var value: SudokuValue, // the value of the cell
    var isInputField: Boolean = true // type of the cell, true if cell is a user input cell, false if it is a question cell with a set value
)

/**
 * Class that serves as a counter
 */
class Counter (var count: Int = 0)

/**
 * Data class representing a Sudoku game
 * @property sudokuGrid a List of Cell representing the sudoku grid
 */
data class Sudoku (
    private var sudokuGrid: List<Cell> = List<Cell>(16) {
        Cell(SudokuValue.EMPTY)
    }
) {
    init {
        // initialize sudoku game with a random grid if the sudoku grid is empty
        if (sudokuGridIsEmpty()) {
            sudokuGrid = getNewGameGrid()
        }
    }

    /**
     * Get the current sudoku grid
     * @return the list of all cells representing the grid of the sudoku game
     */
    fun getGrid(): List<Cell>{
        return sudokuGrid
    }

    /**
     * Returns a list of cells representing a row of a sudoku grid
     * @param n the number n for the n-th row of the sudoku grid
     * @return a list of the cells of the n-th row
     */
    fun getRow(n: Int, grid: List<Cell> = sudokuGrid): List<Cell>{
        val li = mutableListOf<Cell>()
        for (i in 0..3){
            li.add(grid[(n*4)+i])
        }
        return li
    }

    /**
     * Returns a list of cells representing a column of a sudoku grid
     * @param n the number n for the n-th column of the sudoku grid
     * @return a list of the cells of the n-th column
     */
    fun getCol(n: Int,grid: List<Cell> = sudokuGrid): List<Cell>{
        val li = mutableListOf<Cell>()
        for (i in 0..3){
            li.add(grid[(i*4)+n])
        }
        return li
    }

    /**
     * Returns a list of cells representing a sub-grid of a sudoku grid
     * @param n the number n for the n-th sub-grid of the sudoku grid
     * @return a list of the cells of the n-th sub-grid
     */
    fun getSubGrid(n: Int, grid: List<Cell> = sudokuGrid): List<Cell>{
        val li = mutableListOf<Cell>()
        when(n) {
            0 -> {
                li.addAll(getRow(0, grid).slice(0..1))
                li.addAll(getRow(1, grid).slice(0..1))
            }
            1 -> {
                li.addAll(getRow(0, grid).slice(2..3))
                li.addAll(getRow(1, grid).slice(2..3))
            }
            2 -> {
                li.addAll(getRow(2, grid).slice(0..1))
                li.addAll(getRow(3, grid).slice(0..1))
            }
            3 -> {
                li.addAll(getRow(2, grid).slice(2..3))
                li.addAll(getRow(3, grid).slice(2..3))
            }
        }
        return li
    }

    /**
     * Checks if the current sudoku grid is empty
     * @return true if the sudoku grid is empty, and false otherwise
     */
    private fun sudokuGridIsEmpty(): Boolean {
        for (i in sudokuGrid.indices){
            if (sudokuGrid[i].value != SudokuValue.EMPTY){
                return false
            }
        }
        return true
    }

    /**
     * Given a row and a colum, returns the cell at that position.
     * @param row the number for the row of the sudoku grid
     * @param col the number for the column of the sudoku grid
     * @return the Cell at the row and column
     */
    fun getCellAt(row: Int, col: Int): Cell {
        return sudokuGrid[rowColToIdx(row, col)]
    }

    /**
     * Given a row, a colum, and a new value, return the list of cells with the value at row and colum updated to the new value
     * @param row the number for the row of the sudoku grid
     * @param col the number for the column of the sudoku grid
     * @param newVal the new value for the cell
     * @return a list of all the cells of the updated sudoku grid
     */
    fun updateCell(row: Int, col: Int, newVal: Int): List<Cell>{
        val idx = rowColToIdx(row, col)
        val newList = mutableListOf<Cell>()
        for (i in sudokuGrid.indices) {
            if (i == idx){
                val newCell = Cell(SudokuValue.EMPTY)
                when(newVal) {
                    1 -> newCell.value = SudokuValue.ONE
                    2 -> newCell.value = SudokuValue.TWO
                    3 -> newCell.value = SudokuValue.THREE
                    4 -> newCell.value = SudokuValue.FOUR
                    else -> newCell.value = SudokuValue.EMPTY
                }
                newList.add(newCell)
            } else {
                newList.add(Cell(sudokuGrid[i].value))
            }
        }
        return newList
    }

    /**
     * Given a grid row and col, return the list index at that position
     * @param row the index for the row of a sudoku grid
     * @param col the index for the column of a sudoku grid
     * @return The list index of that row and column position
     */
    private fun rowColToIdx(row: Int, col: Int): Int {
        return (row*4)+col
    }

    /**
     * Given a list index representing a sudoku grid position, return the row and column indices of that position
     * @param idx index of a list representing a sudoku grid
     * @return A pair of integers representing the row and column indices of the sudoku grid
     */
    private fun idxToRowCol(idx: Int): Pair<Int, Int> {
        val row = idx/4
        val col = idx%4
        return Pair(row, col)
    }

    /**
     * Get a new random 4x4 sudoku puzzle that has a unique solution
     * @return A list of cells representing the sudoku grid
     */
    private fun getNewGameGrid(): List<Cell> {
        // Get a random valid and completed sudoku grid using backtracking algorithm on an empty grid
        val randomValuesList = mutableListOf(1,2,3,4)
        val li: MutableList<Cell> = MutableList<Cell>(16) { Cell(SudokuValue.EMPTY) }
        backtrack(li, 0, randomValuesList.shuffled())

        // Remove values from cells in a random order until the sudoku grid has a unique solvable solution
        val cellsRemoveOrder: MutableList<Int> = MutableList<Int>(16) { it } // list containing the order in which values will be removed from cells
        cellsRemoveOrder.shuffle()

        while (cellsRemoveOrder.isNotEmpty()){
            val nextCell = cellsRemoveOrder.removeAt(0) // get next cell position to try to remove
            val cellValue = li[nextCell].value // remember the cell's value in case this cell's value should not be removed
            li[nextCell].value = SudokuValue.EMPTY // remove value at this cell

            // check if grid still has unique solution
            val liCopy: MutableList<Cell> = MutableList<Cell>(16) { Cell(SudokuValue.EMPTY) }
            for (i in li.indices){
                liCopy[i] = li[i].copy()
                if (liCopy[i].value != SudokuValue.EMPTY) liCopy[i].isInputField = false
            }

            // if no, put back value & change cell type to not input cell
            if (backtrack(liCopy, 0, Counter(0)) != 1){
                li[nextCell].value = cellValue
                li[nextCell].isInputField = false
            }
        }

        return li
    }

    /**
     * Backtracking algorithm that solves a 4x4 sudoku puzzle
     * @param cellList the list of cell representing the sudoku grid
     * @param nextEmptyCell the index of the next cell to be solved in the list
     * @param randomValuesList a shuffled list of values 1 to 4 following which the backtracking algorithm will attempt to fill in the values
     * @return true if the sudoku grid is solved successfully, false otherwise
     */
    private fun backtrack(cellList: MutableList<Cell>, nextEmptyCell: Int, randomValuesList: List<Int>): Boolean{
        if (nextEmptyCell == cellList.size) { // if filled in all cells, return
            return true
        }

        // if this cell is not an input cell go next
        if (!cellList[nextEmptyCell].isInputField) {
            return backtrack(cellList, nextEmptyCell+1, randomValuesList.shuffled())
        }

        for (i in randomValuesList){ // for each values 1 to 4
            cellList[nextEmptyCell].value = SudokuValue.entries[i] // try a value
            if (validateGrid(cellList,true)) { // if currently valid value, recursive call with next empty cell + 1
                if (backtrack(cellList, nextEmptyCell+1, randomValuesList.shuffled())){
                    return true
                }
                cellList[nextEmptyCell].value = SudokuValue.EMPTY
            } else { // else reset cell value back to empty
                cellList[nextEmptyCell].value = SudokuValue.EMPTY
            }
        }
        return false
    }

    /**
     * Backtracking algorithm that checks if a 4x4 sudoku puzzle has a unique solution or not
     * @param cellList the list of cell representing the sudoku grid
     * @param nextEmptyCell the index of the next cell to be solved in the list
     * @param counter counter that keeps track of the current number of solutions
     * @return 0 if no solution, 1 if unique solution, 2 if more than one solution
     */
    private fun backtrack(cellList: MutableList<Cell>, nextEmptyCell: Int, counter: Counter): Int{
        if (nextEmptyCell == cellList.size) {
            return counter.count + 1 // +1 to counter when found a solution
        }

        if (!cellList[nextEmptyCell].isInputField) {
            return backtrack(cellList, nextEmptyCell+1, counter)
        }

        for (i in 1..4){
            if (counter.count > 1) break // if more than 1 solution, sudoku grid does not have a unique solution, break
            cellList[nextEmptyCell].value = SudokuValue.entries[i]
            if (validateGrid(cellList,true)) {
                counter.count = backtrack(cellList, nextEmptyCell+1, counter)
            }
            cellList[nextEmptyCell].value = SudokuValue.EMPTY
        }
        return counter.count
    }

    /**
     * Create a list of cells representing a sudoku grid given a list of integers
     * @param intList a list of integer values representing the values of each cells of a sudoku grid
     * @return A list of Cell representing the sudoku grid
     */
    private fun createGrid(intList: List<Int>): MutableList<Cell>{
        val li = mutableListOf<Cell>()
        for (i in intList){
            when(i) {
                0 -> li.add(Cell(SudokuValue.EMPTY))
                1 -> li.add(Cell(SudokuValue.ONE, false))
                2 -> li.add(Cell(SudokuValue.TWO, false))
                3 -> li.add(Cell(SudokuValue.THREE, false))
                4 -> li.add(Cell(SudokuValue.FOUR, false))
            }
        }
        return li
    }

    /**
     * Validates whether the current sudoku grid is valid. A valid board is fully filled and contains the numbers 1-4 on all rows, columns, and sub-grids exactly one time
     * @return Returns true if the grid is valid, and returns false otherwise
     */
    fun validateGrid(grid: List<Cell> = sudokuGrid, ignoreEmpty: Boolean = false): Boolean {
        var valid = true
        for (i in 0..3){
            val row = getRow(i, grid)
            val col = getCol(i, grid)
            val subGrid = getSubGrid(i, grid)

            if (!validateSection(row, ignoreEmpty) || !validateSection(col, ignoreEmpty) || !validateSection(subGrid, ignoreEmpty)) {
                valid = false
                break
            }
        }
        return valid
    }

    /**
     * Validates whether a section of the sudoku grid (a row, a column, or a sub-grid) is valid, thus containing all four numbers 1,2,3, and 4 exactly once.
     * @param cellList a list of cells representing a section of the sudoku grid (a row, a column, or a sub-grid)
     * @return true if the section is valid, and false otherwise
     */
    private fun validateSection(cellList: List<Cell>, ignoreEmpty: Boolean = false): Boolean{
        var valid = true
        val allNums: MutableList<Int> = MutableList<Int>(4) {0}
        var emptyCount = 0

        for (cell in cellList){
            when (cell.value) {
                SudokuValue.EMPTY -> {
                    if (ignoreEmpty) {
                        emptyCount++
                        continue
                    }
                    valid = false
                    break
                }
                SudokuValue.ONE -> allNums[0] = 1
                SudokuValue.TWO -> allNums[1] = 1
                SudokuValue.THREE -> allNums[2] = 1
                SudokuValue.FOUR -> allNums[3] = 1
            }
        }

        if (!ignoreEmpty) {
            if (allNums.contains(0)) valid = false
        } else {
            var zeroCount = 0
            for (num in allNums){
                if (num == 0) zeroCount ++
            }

            if (zeroCount != emptyCount) valid = false
        }

        return valid
    }
}