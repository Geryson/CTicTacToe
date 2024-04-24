package com.ggantycc.ctictactoe.model

object TicTacToeModel {

    public val EMPTY: Short = 0
    public val CIRCLE: Short = 1
    public val CROSS: Short = 2

    private val model = arrayOf(
        shortArrayOf(EMPTY, EMPTY, EMPTY),
        shortArrayOf(EMPTY, EMPTY, EMPTY),
        shortArrayOf(EMPTY, EMPTY, EMPTY)
    )

    private var nextPlayer = CIRCLE

    fun resetModel() {
        for (i in 0..2) {
            for (j in 0..2) {
                model[i][j] = EMPTY
            }
        }
        nextPlayer = CIRCLE
    }

    fun getFieldContent(x: Int, y: Int) = model[x][y]

    fun setFieldContent(x: Int, y: Int, content: Short) {
        model[x][y] = content
    }

    fun getNextPlayer() = nextPlayer

    fun changeNextPlayer() {
        nextPlayer = if (nextPlayer == CIRCLE) CROSS else CIRCLE
    }

    fun checkWinner(): Boolean {
        val firstColumnFilled =  model[0][0] != EMPTY && listOf(model[0][1], model[0][2]).all { it == model[0][0] }
        val secondColumnFilled =  model[1][0] != EMPTY && listOf(model[1][1], model[1][2]).all { it == model[1][0] }
        val thirdColumnFilled = model[2][0] != EMPTY && listOf(model[2][1], model[2][2]).all { it == model[2][0] }
        val firstRowFilled = model[0][0] != EMPTY && listOf(model[1][0], model[2][0]).all { it == model[0][0] }
        val secondRowFilled = model[0][1] != EMPTY && listOf(model[1][1], model[2][1]).all { it == model[0][1] }
        val thirdRowFilled = model[0][2] != EMPTY && listOf(model[1][2], model[2][2]).all { it == model[0][2] }
        val upperDiagonalFilled = model[0][0] != EMPTY && listOf(model[1][1], model[2][2]).all { it == model[0][0] }
        val lowerDiagonalFilled = model[0][2] != EMPTY && listOf(model[1][1], model[2][0]).all { it == model[0][2] }

        val boolResult = (firstColumnFilled || secondColumnFilled || thirdColumnFilled
                || firstRowFilled || secondRowFilled || thirdRowFilled
                || upperDiagonalFilled || lowerDiagonalFilled)

        return boolResult
    }

    fun checkTie(): Boolean {
        var allCellsFilled = true
        for (column in model) {
            if (column.any { it == EMPTY }) {
                allCellsFilled = false
                break
            }
        }

        return allCellsFilled
    }

}