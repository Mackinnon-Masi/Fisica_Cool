package org.example.Trackeo

import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import javafx.scene.paint.Color

object MouseTracker {
    @Volatile
    var lastX: Double = 0.0
    @Volatile
    var lastY: Double = 0.0
    /** Actualiza las coordenadas del mouse y opcionalmente muestra un label. */
    fun attachTo(pane: Pane, showLabel: Boolean = true): Label? {
        val label = if (showLabel) {
            Label("x: 50.0  y: 50.0").apply {
                textFill = Color.WHITE
                style = "-fx-background-color: rgba(0, 0, 0, 0.75); -fx-padding: 6px; -fx-border-radius: 4px; -fx-background-radius: 4px;"
                isManaged = false
                isMouseTransparent = true
                layoutX = 10.0
                layoutY = 10.0
            }.also {
                pane.children.add(it)
                it.autosize()
                it.toFront()
            }
        } else {
            null
        }

        // Actualizamos la posición cuando el mouse se mueve o arrastra
        val mover: (MouseEvent) -> Unit = { e ->
            val sceneX = e.sceneX
            val sceneY = e.sceneY
            lastX = sceneX
            lastY = sceneY
            label?.let {
                it.text = "x: ${"%.1f".format(sceneX)}  y: ${"%.1f".format(sceneY)}"
                it.autosize()

                val mouseInPane = pane.sceneToLocal(sceneX, sceneY)
                it.layoutX = mouseInPane.x + 12.0
                it.layoutY = mouseInPane.y + 12.0
            }
        }

        pane.addEventFilter(MouseEvent.MOUSE_MOVED) { mover(it) }
        pane.addEventFilter(MouseEvent.MOUSE_DRAGGED) { mover(it) }

        return label
    }
}
