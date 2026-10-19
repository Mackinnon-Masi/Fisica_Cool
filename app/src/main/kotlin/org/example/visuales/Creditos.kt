package org.example.visuales

import javafx.geometry.Insets
import javafx.geometry.Pos
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

    fun crearVista(onVolverClick: () -> Unit = {}): BorderPane {

        val btnVolver = BotonVolver(onVolverClick)

        val bloque1 = crearBloqueProgramador("Malena Masi Fragapane")
        val bloque2 = crearBloqueProgramador("Lautaro Mackinnon")

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
        
        return root
    }

    private fun crearBloqueProgramador(nombre: String): VBox {
        val rol = Label("Programador").apply {
            textFill = Color.WHITE
            font = Font.font("Montserrat", javafx.scene.text.FontWeight.BOLD, 48.0)
        }
        val etiquetaNombre = Label(nombre).apply {
            textFill = Color.WHITE
            font = Font.font("Montserrat", javafx.scene.text.FontWeight.NORMAL, 28.0)
        }
        return VBox(10.0, rol, etiquetaNombre).apply { alignment = Pos.CENTER }
    }
}