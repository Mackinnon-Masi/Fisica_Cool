package org.example.visuales

import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.control.TextFormatter
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color

/** Panel con el campo de rapidez y los mensajes de estado. */
class PanelControles : IPanelControles {

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

    override val estadoDisparo = Label().apply {
        textFill = Color.LIGHTCORAL
    }

    override val nodo: VBox = VBox(
        6.0,
        HBox(
            8.0,
            Label("Rapidez inicial (m/s):").apply { textFill = Color.WHITE },
            campoRapidez
        ),
        estadoTrayectoria
    )

    override fun rapidezIngresada(): Double? = campoRapidez.text.toDoubleOrNull()

    override fun habilitarEdicion(activa: Boolean) {
        campoRapidez.isDisable = !activa
    }

    override fun mostrarIndicadorTrayectoria(esAlta: Boolean) {
        trayectoriaAlta = esAlta
        estadoTrayectoria.text = if (esAlta) "Trayectoria alta" else "Trayectoria baja"
    }

    override fun mostrarError(mensaje: String?) {
        estadoDisparo.text = mensaje ?: ""
    }

    override fun reiniciar() {
        estadoDisparo.text = ""
        campoRapidez.isDisable = true
        mostrarIndicadorTrayectoria(trayectoriaAlta)
    }
}
