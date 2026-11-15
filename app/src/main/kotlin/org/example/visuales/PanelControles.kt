package org.example.visuales

import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.control.TextFormatter
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color

/**
 * Panel de entrada y estado del disparo.
 *
 * Responsabilidad unica: capturar la rapidez inicial y mostrar el tipo de
 * trayectoria y los mensajes de error.
 */
class PanelControles {

    private val campoRapidez = TextField("12.0").apply {
        promptText = "Rapidez (m/s)"
        prefColumnCount = 8
        isDisable = true
        textFormatter = TextFormatter<String> { cambio ->
            if (cambio.controlNewText.matches(Regex("[0-9]*(\\.[0-9]*)?"))) cambio else null
        }
    }

    private val estadoTrayectoria = Label("Trayectoria baja").apply {
        textFill = Color.WHITE
    }

    private var trayectoriaAlta = false

    val estadoDisparo = Label().apply {
        textFill = Color.LIGHTCORAL
    }

    val nodo: VBox = VBox(
        6.0,
        HBox(
            8.0,
            Label("Rapidez inicial (m/s):").apply { textFill = Color.WHITE },
            campoRapidez
        ),
        estadoTrayectoria
    )

    fun rapidezIngresada(): Double? = campoRapidez.text.toDoubleOrNull()

    fun habilitarEdicion(activa: Boolean) {
        campoRapidez.isDisable = !activa
    }

    fun mostrarIndicadorTrayectoria(esAlta: Boolean) {
        trayectoriaAlta = esAlta
        estadoTrayectoria.text = if (esAlta) "Trayectoria alta" else "Trayectoria baja"
    }

    fun mostrarError(mensaje: String?) {
        estadoDisparo.text = mensaje ?: ""
    }

    fun reiniciar() {
        estadoDisparo.text = ""
        campoRapidez.isDisable = true
        mostrarIndicadorTrayectoria(trayectoriaAlta)
    }
}
