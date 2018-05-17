package de.ddkfm.micro.main

import javafx.application.Application
import javafx.scene.image.Image
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import tornadofx.App
import tornadofx.View

fun main(args: Array<String>) {
    Application.launch(Main::class.java, *args)
}

class Main: App(MainView::class) {
    override fun start(stage: Stage) {
        stage.title = "MicroNG"
        stage.icons.add(Image(this.javaClass.getResourceAsStream("/images/icon.png")))
        super.start(stage)
    }
}

class MainView : View() {
    override val root : BorderPane by fxml("/views/MainView.fxml")
}