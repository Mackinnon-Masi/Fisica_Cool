package org.example.visuales.trayectoria

import javafx.geometry.Point2D
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Line
import javafx.scene.text.Font
import javafx.scene.text.Text
import kotlin.math.abs

/**
 * Dibuja los ejes de referencia del disparo.
 *
 * Responsabilidad unica: representar las distancias horizontal y vertical.
 */
internal class VisualizadorEjes(contenedor: Pane) {

    private val ejeX = Line()
    private val ejeY = Line()
    private val etiquetaX = Text("x")
    private val etiquetaY = Text("y")
    private var origen = Point2D.ZERO
    private var activo = false

    init {
        configurarEje(ejeX, COLOR_X)
        configurarEje(ejeY, COLOR_Y)
        configurarEtiqueta(etiquetaX, COLOR_X)
        configurarEtiqueta(etiquetaY, COLOR_Y)
        contenedor.children.addAll(ejeX, ejeY, etiquetaX, etiquetaY)
    }

    fun mostrar(origen: Point2D) {
        this.origen = origen
        activo = true
        actualizar(origen)
    }

    fun actualizar(posicion: Point2D) {
        if (!activo) return

        ejeX.startX = origen.x
        ejeX.startY = origen.y
        ejeX.endX = posicion.x
        ejeX.endY = origen.y
        ejeY.startX = origen.x
        ejeY.startY = origen.y
        ejeY.endX = origen.x
        ejeY.endY = posicion.y

        val mostrarX = abs(posicion.x - origen.x) > UMBRAL
        val mostrarY = abs(posicion.y - origen.y) > UMBRAL
        ejeX.isVisible = mostrarX
        etiquetaX.isVisible = mostrarX
        etiquetaX.x = if (posicion.x >= origen.x) posicion.x + MARGEN
        else posicion.x - etiquetaX.layoutBounds.width - MARGEN
        etiquetaX.y = origen.y - MARGEN
        ejeY.isVisible = mostrarY
        etiquetaY.isVisible = mostrarY
        etiquetaY.x = origen.x + MARGEN
        etiquetaY.y = posicion.y - MARGEN
    }

    fun ocultar() {
        activo = false
        ejeX.isVisible = false
        ejeY.isVisible = false
        etiquetaX.isVisible = false
        etiquetaY.isVisible = false
    }

    private fun configurarEje(eje: Line, color: Color) {
        eje.stroke = color
        eje.strokeWidth = 2.0
        eje.isMouseTransparent = true
        eje.isVisible = false
    }

    private fun configurarEtiqueta(etiqueta: Text, color: Color) {
        etiqueta.fill = color
        etiqueta.font = Font.font(14.0)
        etiqueta.isMouseTransparent = true
        etiqueta.isVisible = false
    }

    private companion object {
        const val MARGEN = 6.0
        const val UMBRAL = 0.5
        val COLOR_X: Color = Color.web("#F25022")
        val COLOR_Y: Color = Color.web("#00A4EF")
    }
}
