package klock.fx

import javafx.application.Application
import javafx.application.Application.launch
import javafx.scene.layout.StackPane
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.stage.Stage

fun main(args: Array<String>) {
    launch(KlockFX::class.java)
}

class KlockFX : Application() {

    private var label: Label? = null
    override fun init() {
        label = Label("Hello World")
    }

    override fun start(stage: Stage) {
        val root = StackPane()
        root.children.add(label)
        val scene = Scene(root, 200.0, 200.0)
        stage.title = "Hello World Example"
        stage.scene = scene
        stage.isFullScreen = true
        stage.show()
    }

    override fun stop() {}

}