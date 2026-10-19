package org.example.visuales.trayectoria

import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Polyline
import kotlin.math.floor

internal class VisualizadorRecorrido(contenedor: Pane) {
    private val recorrido = Polyline()
    private val pendiente = Polyline()
    private val puntoFinal = Circle()
    private var puntos = emptyList<Pair<Double, Double>>()
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
        origenX: Double,
        origenY: Double,
        velocidadX: Double,
        velocidadY: Double,
        gravedad: Double,
        tiempoTotal: Double,
        objetivoX: Double,
        objetivoY: Double
    ) {
        this.tiempoTotal = tiempoTotal
        puntoFinal.centerX = objetivoX
        puntoFinal.centerY = objetivoY
        puntoFinal.isVisible = true
        puntos = (0..SEGMENTOS).map { indice ->
            val tiempo = tiempoTotal * indice / SEGMENTOS
            if (indice == SEGMENTOS) objetivoX to objetivoY
            else (origenX + velocidadX * tiempo) to
                (origenY + velocidadY * tiempo + 0.5 * gravedad * tiempo * tiempo)
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
        } else puntos[indice]
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

    private fun interpolar(
        inicio: Pair<Double, Double>,
        fin: Pair<Double, Double>,
        fraccion: Double
    ): Pair<Double, Double> =
        (inicio.first + (fin.first - inicio.first) * fraccion) to
            (inicio.second + (fin.second - inicio.second) * fraccion)

    private fun coordenadas(puntos: List<Pair<Double, Double>>): List<Double> =
        puntos.flatMap { listOf(it.first, it.second) }

    private companion object {
        const val SEGMENTOS = 120
    }
}