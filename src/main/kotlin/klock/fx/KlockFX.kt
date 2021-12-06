package klock.fx

import javafx.application.Application
import javafx.application.Application.launch
import javafx.geometry.Rectangle2D
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.scene.text.TextAlignment
import javafx.stage.Screen
import javafx.stage.Stage
import java.lang.Double.min


fun main(args: Array<String>) {
    launch(KlockFX::class.java)
}

class KlockFX : Application() {

    lateinit var matrix: TextMatrix

    override fun start(stage: Stage) {
        createScene(stage)
        stage.title = "KlockFX"
        stage.isFullScreen = true
        stage.show()
    }

    override fun stop() {}

    private fun createScene(stage: Stage) {
        val size = Screen.getPrimary().bounds
        matrix = TextMatrix(size.width, size.height)
        val m = matrix.getMatrix()
        val textgroup = Group()
        for (y in 0 until m.size) {
            addText(y, m, size, textgroup)
        }
        stage.scene = Scene(textgroup)
        stage.scene.fill = Color.BLACK
        stage.title = "Drawing a Text on a Canvas"
    }

    private fun addText(
        y: Int,
        lines: Array<String>,
        size: Rectangle2D,
        textgroup: Group
    ) {
        val chars = lines[y].toCharArray()
        var dy = size.height / lines.size
        val dx = size.width / chars.size
        val fontsize = min(dy, dx)
        dy = (size.height - fontsize) / (lines.size - 1)
        val topMargin = fontsize
        val rightMargin = 0.0
        val linegroup = Group()
        for (x in 0 until chars.size) {
            val text = Text()
            text.font = Font.font("Lucidas Sans Unicode", fontsize)
            text.fill = Color.DARKGRAY
            text.textAlignment = TextAlignment.CENTER
            text.text = chars[x].toString()
            text.x = rightMargin + x * dx + (dx - text.layoutBounds.width) / 2
            text.y = topMargin + y * dy
            linegroup.children.add(text)
        }
        textgroup.children.add(linegroup)
    }

}