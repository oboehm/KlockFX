package klock.fx

import javafx.application.Application
import javafx.application.Application.launch
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.stage.Screen
import javafx.stage.Stage


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
        val dh = size.height / (m.size + 1)
        val topMargin = dh / 2
        val fontsize = dh / 2
        val textgroup = Group()
        for (y in 0 until m.size) {
            val text = Text()
            text.font = Font.font("Lucidas Sans Unicode", fontsize)
            text.fill = Color.DARKGRAY
            text.text = m[y]
            text.x = 10.0
            text.y = topMargin + y * dh
            textgroup.children.add(text)
        }
        val scene = Scene(textgroup)
        scene.fill = Color.BLACK
        stage.scene = scene
        stage.title = "Drawing a Text on a Canvas"
    }

}