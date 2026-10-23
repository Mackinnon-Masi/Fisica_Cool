package org.example.visuales.trayectoria

import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Line
import javafx.scene.text.Font
import javafx.scene.text.Text
import kotlin.math.abs

internal class VisualizadorEjes(contenedor: Pane) {
    // Ejes y etiquetas
    private val ejeX = Line()
    private val ejeY = Line()
    private val etiquetaX = Text("x")
    private val etiquetaY = Text("y")
    private var origenX = 0.0
    private var origenY = 0.0
    private var activo = false

    init {
        // Asociación de los ejes y etiquetas
        configurarEje(ejeX, COLOR_X)
        configurarEje(ejeY, COLOR_Y)
        configurarEtiqueta(etiquetaX, COLOR_X)
        configurarEtiqueta(etiquetaY, COLOR_Y)
        contenedor.children.addAll(ejeX, ejeY, etiquetaX, etiquetaY)
    }

    fun mostrar(origenX: Double, origenY: Double) {
        // Establecer el origen y activar la visualización. Hace que inicie en 0
        this.origenX = origenX
        this.origenY = origenY
        activo = true
        actualizar(origenX, origenY)
    }

    fun actualizar(posicionX: Double, posicionY: Double) {
        // Actualizar la posición de los ejes y etiquetas según la posición actual. Hace que se pueda reiniciar
        if (!activo) return
        ejeX.startX = origenX
        ejeX.startY = origenY
        ejeX.endX = posicionX
        ejeX.endY = origenY
        ejeY.startX = origenX
        ejeY.startY = origenY
        ejeY.endX = origenX
        ejeY.endY = posicionY

        // Determinar si se deben mostrar los ejes y etiquetas según la distancia al origen. Si está fuera de rango las trayectorias no se ven
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
        // Desactivar la visualización de los ejes y etiquetas
        activo = false
        ejeX.isVisible = false
        ejeY.isVisible = false
        etiquetaX.isVisible = false
        etiquetaY.isVisible = false
    }

    private fun configurarEje(eje: Line, color: Color) {
        // Configuración de las propiedades del eje
        eje.stroke = color
        eje.strokeWidth = 2.0
        eje.isMouseTransparent = true
        eje.isVisible = false
    }

    private fun configurarEtiqueta(etiqueta: Text, color: Color) {
        // Configuración de las propiedades de la etiqueta
        etiqueta.fill = color
        etiqueta.font = Font.font(14.0)
        etiqueta.isMouseTransparent = true
        etiqueta.isVisible = false
    }

    private companion object {
        // Constantes para el margen y el umbral de visibilidad
        const val MARGEN = 6.0
        const val UMBRAL = 0.5
        val COLOR_X: Color = Color.web("#F25022")
        val COLOR_Y: Color = Color.web("#00A4EF")
    }
}