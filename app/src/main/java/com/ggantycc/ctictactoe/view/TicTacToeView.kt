package com.ggantycc.ctictactoe.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.ggantycc.ctictactoe.MainActivity
import com.ggantycc.ctictactoe.model.TicTacToeModel

class TicTacToeView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var paintBackground: Paint = Paint()
    private var gridPaintLine: Paint = Paint()
    private var circlePaintLine: Paint = Paint()
    private var crossPaintLine: Paint = Paint()

    private var touchable: Boolean = true

//    var x = -1
//    var y = -1

    init {
        paintBackground.color = Color.BLACK
        paintBackground.style = Paint.Style.FILL

        gridPaintLine.color = Color.WHITE
        gridPaintLine.style = Paint.Style.STROKE
        gridPaintLine.strokeWidth = 5f

        circlePaintLine.color = Color.GREEN
        circlePaintLine.style = Paint.Style.STROKE
        circlePaintLine.strokeWidth = 25f

        crossPaintLine.color = Color.RED
        crossPaintLine.style = Paint.Style.STROKE
        crossPaintLine.strokeWidth = 25f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)
        drawGameArea(canvas)
//        canvas.drawLine(0f, 0f, width.toFloat(), height.toFloat(), paintLine)
//
//        canvas.drawCircle(x.toFloat(), y.toFloat(), 50f, paintLine)

        drawPlayers(canvas)
    }

    private fun drawGameArea(canvas: Canvas) {
        //border
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), gridPaintLine)
        //two horizontal lines
        canvas.drawLine(0f, (height / 3).toFloat(), width.toFloat(), (height / 3).toFloat(), gridPaintLine)
        canvas.drawLine(0f, (2 * height / 3).toFloat(), width.toFloat(), (2 * height / 3).toFloat(), gridPaintLine)

        //two vertical lines
        canvas.drawLine((width / 3).toFloat(), 0f, (width / 3).toFloat(), height.toFloat(), gridPaintLine)
        canvas.drawLine((2 * width / 3).toFloat(), 0f, (2 * width / 3).toFloat(), height.toFloat(), gridPaintLine)
    }

    private fun drawPlayers(canvas: Canvas) {
        for (i in 0..2) {
            for (j in 0..2) {
                if (TicTacToeModel.getFieldContent(i, j) == TicTacToeModel.CIRCLE) {
                    val centerX = (i * width / 3 + width / 6).toFloat()
                    val centerY = (j * height / 3 + width / 6).toFloat()
                    val radius = height / 6 - 2

                    canvas.drawCircle(centerX, centerY, radius.toFloat(), circlePaintLine)
                } else if (TicTacToeModel.getFieldContent(i, j) == TicTacToeModel.CROSS) {
                    canvas.drawLine((i * width / 3).toFloat(), (j * height / 3).toFloat(),
                        ((i + 1) * width / 3).toFloat(),
                        ((j + 1) * height / 3).toFloat(), crossPaintLine)

                    canvas.drawLine(((i + 1) * width / 3).toFloat(), (j * height / 3).toFloat(),
                        (i * width / 3).toFloat(), ((j + 1) * height / 3).toFloat(), crossPaintLine)
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
//        x = event.x.toInt()
//        y = event.y.toInt()
//
//        invalidate() //onDraw újra meghívódik emiatt

        if (event.action == MotionEvent.ACTION_DOWN && touchable) {
            val tX = event.x.toInt() / (width / 3)
            val tY = event.y.toInt() / (height/ 3)

            if (tX < 3 && tY < 3 && TicTacToeModel.getFieldContent(tX, tY) == TicTacToeModel.EMPTY) {
                TicTacToeModel.setFieldContent(tX, tY, TicTacToeModel.getNextPlayer())

                invalidate()

                //itt vizsgálni, hogy van-e győztes...
                if (TicTacToeModel.checkWinner()) {
                    touchable = false

                    var next = "O"

                    if (TicTacToeModel.getNextPlayer() == TicTacToeModel.CROSS) {
                        next = "X"
                    }

                    (context as MainActivity).showText("Winner is: $next!")
                } else if (TicTacToeModel.checkTie()) {
                    (context as MainActivity).showText("It's a draw. No winner!")
                } else {
                    TicTacToeModel.changeNextPlayer()

                    var next = "O"

                    if (TicTacToeModel.getNextPlayer() == TicTacToeModel.CROSS) {
                        next = "X"
                    }
                    (context as MainActivity).showText("Next player is: $next")
                }
            }
        }

        return true
    }

    public fun resetGame() {
        TicTacToeModel.resetModel()
        (context as MainActivity).showText("First player is: O")
        touchable = true
        invalidate()
    }

}