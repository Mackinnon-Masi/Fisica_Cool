package org.example.visuales

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font

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
        val titulo = Label("SIMULADOR MRUV").apply {
            textFill = Color.WHITE
            font = Font.font("Horizon", 72.0) 
        }

        val btnIniciar = crearBoton("INICIAR", onIniciarClick)
        val btnCreditos = crearBoton("CRÉDITOS", onCreditosClick)

        val contenedorBotones = VBox(20.0, btnIniciar, btnCreditos).apply {
            // Configuración del contenedor de botones
            alignment = Pos.CENTER
        }

        val root = VBox(80.0, titulo, contenedorBotones).apply {
            // Configuración del contenedor principal
            alignment = Pos.CENTER
            style = "-fx-background-color: black;"
        }

        return root
    }

    private fun crearBoton(texto: String, accion: () -> Unit): Button {
        val estiloNormal = """
            // Estilo CSS para el botón
            -fx-background-color: transparent;
            -fx-text-fill: white;
            -fx-border-color: white;
            -fx-border-width: 2px;
            -fx-font-family: 'Montserrat';
            -fx-font-size: 20px;
            -fx-font-weight: light;
            -fx-cursor: hand;
        """.trimIndent()
        
        val estiloHover = estiloNormal.replace("transparent", "#505050")
        return Button(texto).apply {
            // Configuración de las propiedades del botón
            minWidth = ANCHO_BOTON
            prefWidth = ANCHO_BOTON
            maxWidth = ANCHO_BOTON
            minHeight = ALTO_BOTON
            prefHeight = ALTO_BOTON
            maxHeight = ALTO_BOTON
            isPickOnBounds = true
            style = estiloNormal
            setOnMouseEntered { style = estiloHover }
            setOnMouseExited { style = estiloNormal }
            setOnAction { accion() }
        }
    }

    private companion object {
        // Constantes para el tamaño de los botones
        const val ANCHO_BOTON = 350.0
        const val ALTO_BOTON = 55.0
    }
}