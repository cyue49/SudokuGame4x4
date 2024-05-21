package gameLogic

enum class SudokuValue(val num: Int?) {
    EMPTY(null),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4)
}

data class Cell (var value: SudokuValue)

data class Sudoku (
    private var sudokuGrid: List<Cell> = List<Cell>(16) {
        Cell(SudokuValue.EMPTY)
    }
) {
    init {
        // get a new random sudoku puzzle
        getNewGameGrid()
    }

    /**
     * @return the list of all cells representing the grid of the sudoku game
     */
    fun getGrid(): List<Cell>{
        return sudokuGrid
    }

    /**
     * @param n the number n for the n-th row of the sudoku grid
     * @return a list of the cells of the n-th row
     */
    fun getRow(n: Int): List<Cell>{
        val li = mutableListOf<Cell>()
        for (i in 0..3){
            li.add(sudokuGrid[(n*4)+i])
        }
        return li
    }

    /**
     * @param n the number n for the n-th column of the sudoku grid
     * @return a list of the cells of the n-th column
     */
    fun getCol(n: Int): List<Cell>{
        val li = mutableListOf<Cell>()
        for (i in 0..3){
            li.add(sudokuGrid[(i*4)+n])
        }
        return li
    }

    /**
     * @param n the number n for the n-th sub-grid of the sudoku grid
     * @return a list of the cells of the n-th sub-grid
     */
    fun getSubGrid(n: Int): List<Cell>{
        val li = mutableListOf<Cell>()
        when(n) {
            0 -> {
                li.addAll(getRow(0).slice(0..1))
                li.addAll(getRow(1).slice(0..1))
            }
            1 -> {
                li.addAll(getRow(0).slice(2..3))
                li.addAll(getRow(1).slice(2..3))
            }
            2 -> {
                li.addAll(getRow(2).slice(0..1))
                li.addAll(getRow(3).slice(0..1))
            }
            3 -> {
                li.addAll(getRow(2).slice(2..3))
                li.addAll(getRow(3).slice(2..3))
            }
        }
        return li
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

    // helper function: given a grid row and col, return the array index at that position
    private fun rowColToIdx(row: Int, col: Int): Int {
        return (row*4)+col
    }

    // helper function: given an array index representing a grid position, return the row and col at that position
    private fun idxToRowCol(idx: Int): Pair<Int, Int> {
        val row = idx/4
        val col = idx%4
        return Pair(row, col)
    }

    fun getNewGameGrid(){
        /*
        * ==> PART 1: Generate a random valid filled board
        * 1. while board is not filled
        * 2. iterate board
        * 3. choose random number 1-4
        * 4. check if valid, if yes put in cell, if not choose another random number between the remaining 3
        * 5. if reached cell where no valid number can be put, go back to previous cell and choose another number
        * 6. when board filled, return board
        *
        * ==> PART 2 (sudoku with unique answers only): randomly remove some cells
        * 1. start with filled board
        * 2. randomly shuffle a list of 16 numbers (all cell positions of the 4x4 sudoku)
        * 3. while board not empty
        * 4. remove number at a position following the randomly shuffled positions
        * 5. check if board has unique solution (by using a backtracking sudoku solver)
        * 6. if yes go to next position to remove, if no, undo this remove then go to next position to remove
        * 7. once iterated through all 16 positions, return board
        *
        * ==> OR (sudoku without guarantee that only has one unique answer): Randomly remove any 11 positions
        */
    }

    // Generate a random valid completed 4x4 sudoku grid
    private fun getRandomValidGrid() {
        // todo
    }

    // Creates a list of cells representing a sudoku grid given a list of int representing the values of the grid
    private fun createGrid(intList: List<Int>): List<Cell>{
        val li = mutableListOf<Cell>()
        for (i in intList){
            when(i) {
                0 -> li.add(Cell(SudokuValue.EMPTY))
                1 -> li.add(Cell(SudokuValue.ONE))
                2 -> li.add(Cell(SudokuValue.TWO))
                3 -> li.add(Cell(SudokuValue.THREE))
                4 -> li.add(Cell(SudokuValue.FOUR))
            }
        }
        return li
    }

    /**
     * Validates whether the current sudoku grid is valid. A valid board is fully filled and contains the numbers 1-4 on all rows, columns, and sub-grids exactly one time
     * @return Returns true if the grid is valid, and returns false otherwise
     */
    fun validateGrid(): Boolean {
        var valid = true
        for (i in 0..3){
            val row = getRow(i)
            val col = getCol(i)
            val subGrid = getSubGrid(i)

            if (!validateSection(row) || !validateSection(col) || !validateSection(subGrid)) {
                valid = false
                break
            }
        }
        return valid
    }

    // Helper function: given a list of cells representing a section (a row, a column, or a subgrid) in the sudoku, validate whether it contains all four numbers 1-4
    private fun validateSection(line: List<Cell>, ignoreEmpty: Boolean = false): Boolean{
        var valid = true
        val allNums: MutableList<Int> = MutableList<Int>(4) {0}
        var emptyCount = 0

        for (cell in line){
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