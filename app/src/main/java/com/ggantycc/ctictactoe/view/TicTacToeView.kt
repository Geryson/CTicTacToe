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
    private var paintLine: Paint = Paint()

//    var x = -1
//    var y = -1

    //HÁZI: reset / megérteni a kirajzolást / next player is szöveg helyesbítése
    //HÁZI: győzelem? döntetlen? (ide checkWinner a mátrixból) minden egyes lépés után
    //HÁZI: különböző O és X színek használata / előmenü készítése a játékhoz

    init {
        paintBackground.color = Color.BLACK
        paintBackground.style = Paint.Style.FILL

        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 5f
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
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)
        //two horizontal lines
        canvas.drawLine(0f, (height / 3).toFloat(), width.toFloat(), (height / 3).toFloat(), paintLine)
        canvas.drawLine(0f, (2 * height / 3).toFloat(), width.toFloat(), (2 * height / 3).toFloat(), paintLine)

        //two vertical lines
        canvas.drawLine((width / 3).toFloat(), 0f, (width / 3).toFloat(), height.toFloat(), paintLine)
        canvas.drawLine((2 * width / 3).toFloat(), 0f, (2 * width / 3).toFloat(), height.toFloat(), paintLine)
    }

    private fun drawPlayers(canvas: Canvas) {
        for (i in 0..2) {
            for (j in 0..2) {
                if (TicTacToeModel.getFieldContent(i, j) == TicTacToeModel.CIRCLE) {
                    val centerX = (i * width / 3 + width / 6).toFloat()
                    val centerY = (j * height / 3 + width / 6).toFloat()
                    val radius = height / 6 - 2

                    canvas.drawCircle(centerX, centerY, radius.toFloat(), paintLine)
                } else if (TicTacToeModel.getFieldContent(i, j) == TicTacToeModel.CROSS) {
                    canvas.drawLine((i * width / 3).toFloat(), (j * height / 3).toFloat(),
                        ((i + 1) * width / 3).toFloat(),
                        ((j + 1) * height / 3).toFloat(), paintLine)

                    canvas.drawLine(((i + 1) * width / 3).toFloat(), (j * height / 3).toFloat(),
                        (i * width / 3).toFloat(), ((j + 1) * height / 3).toFloat(), paintLine)
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
//        x = event.x.toInt()
//        y = event.y.toInt()
//
//        invalidate() //onDraw újra meghívódik emiatt

        if (event.action == MotionEvent.ACTION_DOWN) {
            val tX = event.x.toInt() / (width / 3)
            val tY = event.y.toInt() / (height/ 3)

            if (tX < 3 && tY < 3 && TicTacToeModel.getFieldContent(tX, tY) == TicTacToeModel.EMPTY) {
                TicTacToeModel.setFieldContent(tX, tY, TicTacToeModel.getNextPlayer())

                //itt vizsgálni, hogy van-e győztes...

                TicTacToeModel.changeNextPlayer()

                var next = "O"

                if (TicTacToeModel.getNextPlayer() == TicTacToeModel.CROSS) {
                    next = "X"
                }

                (context as MainActivity).showText("Next player is: $next")

                invalidate()
            }
        }

        return true
    }

    public fun resetGame() {
        TicTacToeModel.resetModel()
        invalidate()
    }

}