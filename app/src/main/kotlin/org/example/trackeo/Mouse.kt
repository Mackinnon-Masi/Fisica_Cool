package org.example.trackeo

import javafx.scene.control.Label
import javafx.event.EventHandler
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import javafx.scene.paint.Color

class MouseTracker {
    // Variables para almacenar las coordenadas del mouse
    var lastX: Double = 0.0
        private set
    var lastY: Double = 0.0
        private set

    //Actualiza las coordenadas del mouse y opcionalmente muestra un label
    fun attachTo(pane: Pane, showLabel: Boolean = true): Label? {
        // Crea un label para mostrar las coordenadas del mouse si showLabel es true
        val label = if (showLabel) crearEtiqueta(pane) else null
        val mover = EventHandler<MouseEvent> { evento -> actualizarPosicion(evento, pane, label) }
        // Agrega los manejadores de eventos para mover y arrastrar el mouse
        pane.addEventFilter(MouseEvent.MOUSE_MOVED, mover)
        pane.addEventFilter(MouseEvent.MOUSE_DRAGGED, mover)
        return label
    }

    private fun crearEtiqueta(pane: Pane): Label = Label("x: 50.0  y: 50.0").apply {
        // Configuración de las propiedades del label
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

    private fun actualizarPosicion(evento: MouseEvent, pane: Pane, label: Label?) {
        // Actualiza las coordenadas del mouse y la posición del label si existe
        lastX = evento.sceneX
        lastY = evento.sceneY
        label?.let {
            // Actualiza el texto y la posición del label según las coordenadas del mouse
            it.text = "x: ${"%.1f".format(lastX)}  y: ${"%.1f".format(lastY)}"
            it.autosize()
            val posicionEnPane = pane.sceneToLocal(lastX, lastY)
            // Ajusta la posición del label para que no se superponga con el cursor
            it.layoutX = posicionEnPane.x + MARGEN_CURSOR
            it.layoutY = posicionEnPane.y + MARGEN_CURSOR
        }
    }

    private companion object {
        // Constante para el margen del label respecto al cursor
        const val MARGEN_CURSOR = 12.0
    }
}
