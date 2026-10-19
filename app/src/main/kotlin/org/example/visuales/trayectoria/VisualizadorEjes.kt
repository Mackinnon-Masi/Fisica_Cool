package org.example.visuales.trayectoria

import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Line
import javafx.scene.text.Font
import javafx.scene.text.Text
import kotlin.math.abs

internal class VisualizadorEjes(contenedor: Pane) {
    private val ejeX = Line()
    private val ejeY = Line()
    private val etiquetaX = Text("x")
    private val etiquetaY = Text("y")
    private var origenX = 0.0
    private var origenY = 0.0
    private var activo = false

    init {
        configurarEje(ejeX, COLOR_X)
        configurarEje(ejeY, COLOR_Y)
        configurarEtiqueta(etiquetaX, COLOR_X)
        configurarEtiqueta(etiquetaY, COLOR_Y)
        contenedor.children.addAll(ejeX, ejeY, etiquetaX, etiquetaY)
    }

    fun mostrar(origenX: Double, origenY: Double) {
        this.origenX = origenX
        this.origenY = origenY
        activo = true
        actualizar(origenX, origenY)
    }

    fun actualizar(posicionX: Double, posicionY: Double) {
        if (!activo) return
        ejeX.startX = origenX
        ejeX.startY = origenY
        ejeX.endX = posicionX
        ejeX.endY = origenY
        ejeY.startX = origenX
        ejeY.startY = origenY
        ejeY.endX = origenX
        ejeY.endY = posicionY

        val mostrarX = abs(posicionX - origenX) > UMBRAL
        val mostrarY = abs(posicionY - origenY) > UMBRAL
        ejeX.isVisible = mostrarX
        etiquetaX.isVisible = mostrarX
        etiquetaX.x = if (posicionX >= origenX) posicionX + MARGEN
        else posicionX - etiquetaX.layoutBounds.width - MARGEN
        etiquetaX.y = origenY - MARGEN
        ejeY.isVisible = mostrarY
        etiquetaY.isVisible = mostrarY
        etiquetaY.x = origenX + MARGEN
        etiquetaY.y = posicionY - MARGEN
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