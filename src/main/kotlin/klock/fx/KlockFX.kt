package klock.fx

import javafx.animation.AnimationTimer
import javafx.application.Application
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

internal val COLOR_DARK : Color = Color(1.0, 1.0, 1.0, 0.07)
internal val COLOR_LIGHT : Color = Color(1.0, 1.0, 1.0, 0.50)

class KlockFX : Application() {

    companion object {
        val log = LogManager.getLogger(KlockFX::javaClass)
        @JvmStatic
        fun main(args: Array<String>) {
            log.info("KlockFX wird gestartet...")
            launch(KlockFX::class.java)
            log.info("...KlockFX wird beendet.")
        }
    }

    lateinit var matrix: TextMatrix
    lateinit var stage : Stage

    override fun start(s: Stage) {
        stage = s
        createScene()
        stage.isFullScreen = true
        showKlock()
        val dim = matrix.getTextSize()
        val periodInMillis = 300_000L / (dim.width * dim.height)
        val timer = KlockTimer(periodInMillis)
        timer.start()
        log.info("{} ist fuer {} gestartet.", timer, s)
    }

    override fun stop() {
        log.info("{} wird gestoppt.", this)
    }

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
        dy = (size.height - fontsize - 2) / (lines.size - 1)
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
        val children: List<Node> = getTextGroupNodes()
        val time = matrix.getTimeMatrix()
        for (i in 0 .. children.size-1) {
            val group = children[i] as Group
            val timeRow = time[i]
            highlight(group, timeRow)
        }
        log.debug("{}", children)
        showTick()
        stage.show()
    }

    private fun getTextGroupNodes(): List<Node> {
        val parent = stage.scene.root
        val children: List<Node> = parent.childrenUnmodifiable
        return children
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

    /**
     * Die Idee hinter showTick ist, dass hierueber der Fortschritt angezeigt
     * wird. Dazu wird ein Buchstabe immer ausgeblendet. Dies soll auch
     * verhindern, dass auf einem OLED-Monitor Teil des Bildschirms einbrennt.
     */
    private fun showTick() {
        val children: List<Node> = getTextGroupNodes()
        val textSize = matrix.getTextSize()
        val n = textSize.height * textSize.width
        val i = TextKlock().getTickProgress(n)
        val y = i / textSize.width
        val x = i % textSize.width
        log.info("Zeichen {}/{} wird ausgeblendet.", y, x)
        val group = children[y.toInt()] as Group
        downlight(group, x.toInt())
    }

    private fun downlight(group: Group, x: Int) {
        val children : List<Node> = group.children
        val text : Text = children[x] as Text
        text.fill = Color.BLACK
    }


    inner class KlockTimer(val periodInMillis: Long) : AnimationTimer() {

        var t0 : Long = System.nanoTime()

        override fun handle(now: Long) {
            if ((now - t0) > periodInMillis * 1_000_000L) {
                log.debug("tick {}", now)
                t0 = now
                showKlock()
            }
        }

    }

}