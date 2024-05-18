package gameLogic

data class Cell (var value: Int)

class Sudoku {
    private var sudokuGrid = MutableList<Cell>(16) {
        Cell(0)
    }

    // get the n-th row of a sudoku grid
    fun getRow(n: Int): List<Cell>{
        val li = mutableListOf<Cell>()
        for (i in 0..3){
            li.add(sudokuGrid[(n*4)+i])
        }
        return li
    }
}