package org.example.visuales

import javafx.scene.control.Button

class BotonVolver(onClick: () -> Unit) : Button("🡰") {
    init {
        style = ESTILO_NORMAL
        setOnMouseEntered { style = ESTILO_HOVER }
        setOnMouseExited { style = ESTILO_NORMAL }
        setOnAction { onClick() }
    }

    private companion object {
        const val ESTILO_NORMAL = """
            -fx-background-color: transparent;
            -fx-text-fill: white;
            -fx-font-size: 40px;
            -fx-font-weight: bold;
            -fx-cursor: hand;
        """
        const val ESTILO_HOVER = """
            -fx-background-color: transparent;
            -fx-text-fill: #A0A0A0;
            -fx-font-size: 40px;
            -fx-font-weight: bold;
            -fx-cursor: hand;
        """
    }
}