package org.example.visuales.trayectoria

import javafx.geometry.Point2D
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Polyline
import org.example.fisicas.VelocidadInicial
import kotlin.math.floor

/**
 * Dibuja el recorrido predicho y su avance en el tiempo.
 *
 * Responsabilidad unica: representar la curva de la trayectoria.
 */
internal class VisualizadorRecorrido(contenedor: Pane) {

    private val recorrido = Polyline()
    private val pendiente = Polyline()
    private val puntoFinal = Circle()
    private var puntos = emptyList<Point2D>()
    private var tiempoTotal = 0.0

    init {
        recorrido.apply {
            fill = Color.TRANSPARENT
            stroke = Color.WHITE
            strokeWidth = 2.0
            isMouseTransparent = true
        }
        pendiente.apply {
            fill = Color.TRANSPARENT
            stroke = Color.WHITE
            strokeWidth = 2.0
            opacity = 0.32
            isMouseTransparent = true
        }
        puntoFinal.apply {
            radius = 3.5
            fill = Color.LIMEGREEN
            stroke = Color.LIMEGREEN
            isMouseTransparent = true
            isVisible = false
        }
        contenedor.children.addAll(pendiente, recorrido, puntoFinal)
    }

    fun mostrar(
        origen: Point2D,
        velocidad: VelocidadInicial,
        gravedad: Double,
        objetivo: Point2D
    ) {
        tiempoTotal = velocidad.tiempoDeVuelo
        puntoFinal.centerX = objetivo.x
        puntoFinal.centerY = objetivo.y
        puntoFinal.isVisible = true
        puntos = (0..SEGMENTOS).map { indice ->
            if (indice == SEGMENTOS) {
                objetivo
            } else {
                val tiempo = tiempoTotal * indice / SEGMENTOS
                Point2D(
                    origen.x + velocidad.velocidadX * tiempo,
                    origen.y + velocidad.velocidadY * tiempo + 0.5 * gravedad * tiempo * tiempo
                )
            }
        }
        pendiente.isVisible = true
        recorrido.isVisible = true
        actualizar(0.0)
    }

    fun actualizar(tiempo: Double) {
        if (puntos.size < 2 || tiempoTotal <= 0.0) return

        val posicion = (tiempo / tiempoTotal).coerceIn(0.0, 1.0) * (puntos.size - 1)
        val indice = floor(posicion).toInt()
        val fraccion = posicion - indice
        val recorridoActual = puntos.take(indice + 1).toMutableList()
        val puntoActual = if (fraccion > 0.0 && indice < puntos.lastIndex) {
            interpolar(puntos[indice], puntos[indice + 1], fraccion)
                .also { recorridoActual.add(it) }
        } else {
            puntos[indice]
        }
        val puntosPendientes = buildList {
            add(puntoActual)
            addAll(puntos.drop(indice + 1))
        }
        recorrido.points.setAll(coordenadas(recorridoActual))
        pendiente.points.setAll(coordenadas(puntosPendientes))
        recorrido.isVisible = recorridoActual.size > 1
        pendiente.isVisible = puntosPendientes.size > 1
    }

    fun ocultar() {
        puntos = emptyList()
        recorrido.isVisible = false
        pendiente.isVisible = false
        puntoFinal.isVisible = false
    }

    private fun interpolar(inicio: Point2D, fin: Point2D, fraccion: Double): Point2D = Point2D(
        inicio.x + (fin.x - inicio.x) * fraccion,
        inicio.y + (fin.y - inicio.y) * fraccion
    )

    private fun coordenadas(puntos: List<Point2D>): List<Double> =
        puntos.flatMap { listOf(it.x, it.y) }

    private companion object {
        const val SEGMENTOS = 120
    }
}
