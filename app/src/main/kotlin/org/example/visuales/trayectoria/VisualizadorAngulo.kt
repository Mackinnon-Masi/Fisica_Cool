package org.example.visuales.trayectoria

import javafx.geometry.Point2D
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Arc
import javafx.scene.shape.ArcType
import javafx.scene.text.Font
import javafx.scene.text.Text
import org.example.fisicas.VelocidadInicial
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

/**
 * Dibuja el angulo de lanzamiento.
 *
 * Responsabilidad unica: representar el arco y su etiqueta en grados.
 */
internal class VisualizadorAngulo(contenedor: Pane) {

    private val arco = Arc()
    private val etiqueta = Text("Ω")

    init {
        arco.apply {
            type = ArcType.OPEN
            fill = Color.TRANSPARENT
            stroke = COLOR
            strokeWidth = 2.0
            isMouseTransparent = true
            isVisible = false
        }
        etiqueta.apply {
            fill = COLOR
            font = Font.font(14.0)
            isMouseTransparent = true
            isVisible = false
        }
        contenedor.children.addAll(arco, etiqueta)
    }

    fun mostrar(origen: Point2D, velocidad: VelocidadInicial) {
        val grados = Math.toDegrees(atan2(-velocidad.velocidadY, velocidad.velocidadX))
        arco.centerX = origen.x
        arco.centerY = origen.y
        arco.radiusX = RADIO
        arco.radiusY = RADIO
        arco.startAngle = 0.0
        arco.length = grados
        arco.isVisible = abs(grados) > UMBRAL
        val mitad = Math.toRadians(grados / 2.0)
        etiqueta.x = origen.x + (RADIO + MARGEN) * cos(mitad)
        etiqueta.y = origen.y - (RADIO + MARGEN) * sin(mitad)
        etiqueta.isVisible = arco.isVisible
    }

    fun ocultar() {
        arco.isVisible = false
        etiqueta.isVisible = false
    }

    private companion object {
        const val RADIO = 34.0
        const val MARGEN = 8.0
        const val UMBRAL = 0.5
        val COLOR = Color.web("#FFB900")
    }
}
