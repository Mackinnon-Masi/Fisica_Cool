package org.example.visuales

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font
import org.example.Trackeo.MouseTracker

class Inicio {

    init {
        // Carga las fuentes desde la carpeta resources/fonts/
        // El primer parámetro es la ruta relativa desde la carpeta resources
        val horizonFont = javaClass.getResourceAsStream("/fonts/Horizon.otf")
        val montserratFont = javaClass.getResourceAsStream("/fonts/Montserrat.ttf")

        if (horizonFont != null) {
            Font.loadFont(horizonFont, 72.0)
        } else {
            println("No se pudo encontrar Horizon.otf en /fonts/")
        }

        if (montserratFont != null) {
            Font.loadFont(montserratFont, 20.0)
        } else {
            println("No se pudo encontrar Montserrat.ttf en /fonts/")
        }
    }

    fun crearVista(
        onIniciarClick: () -> Unit = {},
        onCreditosClick: () -> Unit = {}
    ): VBox {
        // --- TÍTULO ---
        val titulo = Label("SIMULADOR MRUV").apply {
            textFill = Color.WHITE
            // Usamos el nombre de la familia tipográfica registrada
            font = Font.font("Horizon", 72.0) 
        }

        // --- ESTILOS CSS ---
        val estiloNormal = """
            -fx-background-color: transparent;
            -fx-text-fill: white;
            -fx-border-color: white;
            -fx-border-width: 2px;
            -fx-font-family: 'Montserrat';
            -fx-font-size: 20px;
            -fx-font-weight: light;
            -fx-cursor: hand;
        """.trimIndent()

        val estiloHover = """
            -fx-background-color: #505050;
            -fx-text-fill: white;
            -fx-border-color: white;
            -fx-border-width: 2px;
            -fx-font-family: 'Montserrat';
            -fx-font-size: 20px;
            -fx-font-weight: light;
            -fx-cursor: hand;
        """.trimIndent()

        // --- BOTONES Y REGISTRO IGUAL QUE ANTES ---
        val btnIniciar = Button("INICIAR").apply {
            minWidth = 350.0
            prefWidth = 350.0
            maxWidth = 350.0
            minHeight = 55.0
            prefHeight = 55.0
            maxHeight = 55.0
            isPickOnBounds = true
            style = estiloNormal
            setOnMouseEntered { style = estiloHover }
            setOnMouseExited { style = estiloNormal }
            setOnAction { onIniciarClick() }
        }

        val btnCreditos = Button("CRÉDITOS").apply {
            minWidth = 350.0
            prefWidth = 350.0
            maxWidth = 350.0
            minHeight = 55.0
            prefHeight = 55.0
            maxHeight = 55.0
            isPickOnBounds = true
            style = estiloNormal
            setOnMouseEntered { style = estiloHover }
            setOnMouseExited { style = estiloNormal }
            setOnAction { onCreditosClick() }
        }

        val contenedorBotones = VBox(20.0, btnIniciar, btnCreditos).apply {
            alignment = Pos.CENTER
        }

        val root = VBox(80.0, titulo, contenedorBotones).apply {
            alignment = Pos.CENTER
            style = "-fx-background-color: black;"
        }

        MouseTracker.attachTo(root, showLabel = false)
        return root
    }
}