package gameLogic

enum class SudokuValue(val num: Int?) {
    EMPTY(null),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4)
}
data class Cell (var value: SudokuValue)

class Sudoku {
    private var sudokuGrid = MutableList<Cell>(16) {
        Cell(SudokuValue.EMPTY)
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
     * Given a row and a colum, returns the cell at that position.
     * @param row the number for the row of the sudoku grid
     * @param col the number for the column of the sudoku grid
     * @return the Cell at the row and column
     */
    fun getCellAt(row: Int, col: Int): Cell {
        return sudokuGrid[rowColToIdx(row, col)]
    }

    /**
     * Given a row, a colum, and a new value, update the cell at that position with the new value then return the updated list of cells
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
                }
                newList.add(newCell)
            } else {
                newList.add(Cell(sudokuGrid[i].value))
            }
        }
        sudokuGrid = newList
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
}