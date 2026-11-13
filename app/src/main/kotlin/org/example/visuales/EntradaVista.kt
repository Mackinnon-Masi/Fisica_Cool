package org.example.visuales

import javafx.geometry.Point2D
import javafx.scene.layout.Pane
import org.example.trackeo.IRastreadorMouse

/**
 * Concentra la entrada del usuario: objetivo del mouse y posicion
 * inicial del disparo.
 */
class EntradaVista(
    private val pane: Pane,
    contenedor: Pane,
    private val mouseTracker: IRastreadorMouse
) : IEntradaVista {

    init {
        mouseTracker.attachTo(contenedor)
    }

    override fun objetivoActual(): Point2D =
        pane.sceneToLocal(mouseTracker.lastX, mouseTracker.lastY)

    override fun posicionInicialY(): Double = (pane.height - 20.0).coerceAtLeast(20.0)

    override fun mostrarEtiqueta(visible: Boolean) {
        mouseTracker.mostrarEtiqueta(visible)
    }
}
