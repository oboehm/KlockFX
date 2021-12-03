package klock.fx

import javafx.application.Application
import javafx.application.Application.launch
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.stage.Screen
import javafx.stage.Stage


fun main(args: Array<String>) {
    launch(KlockFX::class.java)
}

class KlockFX : Application() {

    override fun start(stage: Stage) {
        createScene(stage)
        stage.title = "KlockFX"
        stage.isFullScreen = true
        stage.show()
    }

    override fun stop() {}

    private fun createScene(stage: Stage) {
        val size = Screen.getPrimary().bounds
        val canvas = Canvas(size.width, size.height)
        val gc: GraphicsContext = canvas.graphicsContext2D
        // Set line width
        // Set line width
        gc.lineWidth = 1.0
        // Set fill color
        // Set fill color
        gc.fill = Color.BLUE

        // Draw a Text

        // Draw a Text
        gc.strokeText("This is a stroked Text", 10.0, 50.0)
        gc.strokeText("This is a stroked Text with Max Width 300 px", 10.0, 100.0, 300.0)
        // Draw a filled Text
        // Draw a filled Text
        gc.fillText("This is a filled Text", 10.0, 150.0)
        gc.fillText("This is a filled Text with Max Width 400 px", 10.0, 200.0, 400.0)

        // Create the Pane
        // Create the Pane
        val root = Pane()
        // Set the Style-properties of the Pane
        // Set the Style-properties of the Pane
        root.style = "-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;"

        // Add the Canvas to the Pane

        // Add the Canvas to the Pane
        root.children.add(canvas)
        // Create the Scene
        // Create the Scene
        val scene = Scene(root)
        scene.fill = Color.BLACK
        // Add the Scene to the Stage
        // Add the Scene to the Stage
        stage.scene = scene
        // Set the Title of the Stage
        // Set the Title of the Stage
        stage.title = "Drawing a Text on a Canvas"
        // Display the Stage
        // Display the Stage
        //stage.show()
    }

}