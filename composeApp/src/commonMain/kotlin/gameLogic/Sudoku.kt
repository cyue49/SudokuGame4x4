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
     * @return the grid of the sudoku game
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
}