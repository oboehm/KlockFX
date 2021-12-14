package klock.fx

import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.application.Application.launch
import javafx.geometry.Rectangle2D
import javafx.scene.Group
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.scene.text.TextAlignment
import javafx.stage.Screen
import javafx.stage.Stage
import org.apache.logging.log4j.LogManager
import java.lang.Double.min

internal val COLOR_DARK : Color = Color(1.0, 1.0, 1.0, 0.15)
internal val COLOR_LIGHT : Color = Color.WHITESMOKE

fun main(args: Array<String>) {
    launch(KlockFX::class.java)
}

class KlockFX : Application() {

    private val log = LogManager.getLogger()
    lateinit var matrix: TextMatrix
    lateinit var stage : Stage

    override fun start(s: Stage) {
        stage = s
        createScene()
        stage.isFullScreen = true
        showKlock()
        val timer = KlockTimer()
        timer.start()
    }

    override fun stop() {}

    private fun createScene() {
        val size = Screen.getPrimary().bounds
        matrix = TextMatrix(size.width, size.height)
        val m = matrix.getMatrix()
        val textgroup = Group()
        for (y in 0 until m.size) {
            addText(y, m, size, textgroup)
        }
        stage.scene = Scene(textgroup)
        stage.scene.fill = Color.BLACK
        stage.title = "KlockFX"
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
            text.fill = COLOR_DARK
            text.textAlignment = TextAlignment.CENTER
            text.text = chars[x].toString()
            text.x = rightMargin + x * dx + (dx - text.layoutBounds.width) / 2
            text.y = topMargin + y * dy
            linegroup.children.add(text)
        }
        textgroup.children.add(linegroup)
    }

    private fun showKlock() {
        val parent = stage.scene.root
        val children : List<Node> = parent.childrenUnmodifiable
        val time = matrix.getTimeMatrix()
        for (i in 0 .. children.size-1) {
            val group = children[i] as Group
            val timeRow = time[i]
            highlight(group, timeRow)
        }
        log.debug("{}", children)
        stage.show()
    }

    private fun highlight(group: Group, timeRow: String) {
        val children : List<Node> = group.children
        val letters = timeRow.toCharArray()
        for (i in (0 .. children.size-1)) {
            val text : Text = children[i] as Text
            if (letters[i].isWhitespace()) {
                text.fill = COLOR_DARK
            } else {
                text.fill = COLOR_LIGHT
            }
        }
    }


    inner class KlockTimer : AnimationTimer() {

        var t0 : Long = System.nanoTime()

        override fun handle(now: Long) {
            if ((now - t0) > 5_000_000_000L) {
                log.info("tick {}", now)
                t0 = now
                showKlock()
            }
        }

    }

}