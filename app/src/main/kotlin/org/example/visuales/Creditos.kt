package org.example.visuales

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font

class Creditos {

    init {
        val montserratBold = javaClass.getResourceAsStream("/fonts/Montserrat-Bold.ttf")
        val montserratRegular = javaClass.getResourceAsStream("/fonts/Montserrat.ttf")

        if (montserratBold != null) Font.loadFont(montserratBold, 48.0)
        if (montserratRegular != null) Font.loadFont(montserratRegular, 28.0)
    }

    fun crearEscena(onVolverClick: () -> Unit = {}): Scene {
        
        // --- BOTÓN FLECHA DE REGRESO ---
        val btnVolver = Button("🡰").apply {
            style = """
                -fx-background-color: transparent;
                -fx-text-fill: white;
                -fx-font-size: 40px;
                -fx-font-weight: bold;
                -fx-cursor: hand;
            """.trimIndent()
            
            // Efecto Hover: Cambia ligeramente de opacidad o color al pasar el mouse
            setOnMouseEntered { style = "-fx-background-color: transparent; -fx-text-fill: #A0A0A0; -fx-font-size: 40px; -fx-font-weight: bold; -fx-cursor: hand;" }
            setOnMouseExited { style = "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 40px; -fx-font-weight: bold; -fx-cursor: hand;" }
            
            setOnAction { onVolverClick() }
        }

        // --- BLOQUE PROGRAMADOR 1 ---
        val lblRol1 = Label("Programador").apply {
            textFill = Color.WHITE
            font = Font.font("Montserrat", javafx.scene.text.FontWeight.BOLD, 48.0)
        }
        val lblNombre1 = Label("Malena Masi Fragapane").apply {
            textFill = Color.WHITE
            font = Font.font("Montserrat", javafx.scene.text.FontWeight.NORMAL, 28.0)
        }
        val bloque1 = VBox(10.0, lblRol1, lblNombre1).apply {
            alignment = Pos.CENTER
        }

        // --- BLOQUE PROGRAMADOR 2 ---
        val lblRol2 = Label("Programador").apply {
            textFill = Color.WHITE
            font = Font.font("Montserrat", javafx.scene.text.FontWeight.BOLD, 48.0)
        }
        val lblNombre2 = Label("Lautaro Mackinnon").apply {
            textFill = Color.WHITE
            font = Font.font("Montserrat", javafx.scene.text.FontWeight.NORMAL, 28.0)
        }
        val bloque2 = VBox(10.0, lblRol2, lblNombre2).apply {
            alignment = Pos.CENTER
        }

        // --- CONTENEDOR CENTRAL DE TEXTO ---
        val contenedorTexto = VBox(60.0, bloque1, bloque2).apply {
            alignment = Pos.CENTER
        }

        // --- CONTENEDOR GENERAL (BorderPane) ---
        val root = BorderPane().apply {
            top = btnVolver
            center = contenedorTexto
            style = "-fx-background-color: black;"
            padding = Insets(20.0, 0.0, 0.0, 30.0) // Margen para la flecha (Arriba, Derecha, Abajo, Izquierda)
        }

        return Scene(root, 1280.0, 720.0)
    }
}