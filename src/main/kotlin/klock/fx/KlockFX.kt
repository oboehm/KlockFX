package klock.fx

import javafx.application.Application
import javafx.application.Application.launch
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.Label
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.stage.Stage


fun main(args: Array<String>) {
    launch(KlockFX::class.java)
}

class KlockFX : Application() {

    override fun start(stage: Stage) {
        createScene(stage);
        stage.title = "KlockFX"
        stage.isFullScreen = true
        stage.show()
    }
//    override fun start(stage: Stage) {
//        val root = StackPane()
//        root.children.add(label)
//        val scene = Scene(root, 200.0, 200.0)
//        stage.title = "KlockFX"
//        stage.scene = scene
//        stage.isFullScreen = true
//        stage.show()
//    }

    override fun stop() {}

    private fun createScene(stage: Stage) {
        val width: Double = 400.0
        val height: Double = 300.0
        // Create the Canvas
        // Create the Canvas
        val canvas = Canvas(width, height)
        // Get the graphics context of the canvas
        // Get the graphics context of the canvas
        val gc: GraphicsContext = canvas.getGraphicsContext2D()
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
        // Add the Scene to the Stage
        // Add the Scene to the Stage
        stage.setScene(scene)
        // Set the Title of the Stage
        // Set the Title of the Stage
        stage.setTitle("Drawing a Text on a Canvas")
        // Display the Stage
        // Display the Stage
        //stage.show()
    }

}