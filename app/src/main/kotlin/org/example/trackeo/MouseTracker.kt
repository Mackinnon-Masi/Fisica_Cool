package org.example.trackeo

import javafx.event.EventHandler
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import javafx.scene.paint.Color

/**
 * Sigue la posicion del mouse sobre un panel y muestra una etiqueta con las
 * coordenadas.
 *
 * Responsabilidad unica: adaptar los eventos de mouse de JavaFX.
 */
class MouseTracker {
    var lastX: Double = 0.0
        private set
    var lastY: Double = 0.0
        private set

    private var etiqueta: Label? = null

    fun attachTo(pane: Pane) {
        etiqueta = crearEtiqueta(pane)
        val mover = EventHandler<MouseEvent> { evento -> actualizarPosicion(evento, pane) }
        pane.addEventFilter(MouseEvent.MOUSE_MOVED, mover)
        pane.addEventFilter(MouseEvent.MOUSE_DRAGGED, mover)
    }

    fun mostrarEtiqueta(visible: Boolean) {
        etiqueta?.isVisible = visible
    }

    private fun crearEtiqueta(pane: Pane): Label = Label("x: 50.0  y: 50.0").apply {
        textFill = Color.WHITE
        style = "-fx-background-color: rgba(0, 0, 0, 0.75); -fx-padding: 6px; -fx-border-radius: 4px; -fx-background-radius: 4px;"
        isManaged = false
        isMouseTransparent = true
        layoutX = 10.0
        layoutY = 10.0
        pane.children.add(this)
        autosize()
        toFront()
    }

    private fun actualizarPosicion(evento: MouseEvent, pane: Pane) {
        lastX = evento.sceneX
        lastY = evento.sceneY
        etiqueta?.let { label ->
            label.text = "x: ${"%.1f".format(lastX)}  y: ${"%.1f".format(lastY)}"
            label.autosize()
            val posicionEnPane = pane.sceneToLocal(lastX, lastY)
            label.layoutX = posicionEnPane.x + MARGEN_CURSOR
            label.layoutY = posicionEnPane.y + MARGEN_CURSOR
        }
    }

    private companion object {
        const val MARGEN_CURSOR = 12.0
    }
}
