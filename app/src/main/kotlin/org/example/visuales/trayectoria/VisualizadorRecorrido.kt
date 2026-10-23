package org.example.visuales.trayectoria

import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Polyline
import kotlin.math.floor

internal class VisualizadorRecorrido(contenedor: Pane) {
    // Trayectoria y punto final
    private val recorrido = Polyline()
    private val pendiente = Polyline()
    private val puntoFinal = Circle()
    private var puntos = emptyList<Pair<Double, Double>>()
    private var tiempoTotal = 0.0

    init {
        recorrido.apply {
            // Configuración de las propiedades del recorrido
            fill = Color.TRANSPARENT
            stroke = Color.WHITE
            strokeWidth = 2.0
            isMouseTransparent = true
        }
        pendiente.apply {
            // Configuración de las propiedades de la pendiente
            fill = Color.TRANSPARENT
            stroke = Color.WHITE
            strokeWidth = 2.0
            opacity = 0.32
            isMouseTransparent = true
        }
        puntoFinal.apply {
            // Configuración de las propiedades del punto final
            radius = 3.5
            fill = Color.LIMEGREEN
            stroke = Color.LIMEGREEN
            isMouseTransparent = true
            isVisible = false
        }
        contenedor.children.addAll(pendiente, recorrido, puntoFinal) 
    }

    fun mostrar(
        // Calcular y mostrar la trayectoria completa y el punto final
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
        // Calcular los puntos de la trayectoria y almacenarlos en la lista de puntos
        }
        pendiente.isVisible = true
        recorrido.isVisible = true
        actualizar(0.0)
    }

    fun actualizar(tiempo: Double) {
        // Actualizar la visualización de la trayectoria y la pendiente según el tiempo transcurrido
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
        // Ocultar la visualización de la trayectoria y el punto final
        puntos = emptyList()
        recorrido.isVisible = false
        pendiente.isVisible = false
        puntoFinal.isVisible = false
    }

    private fun interpolar(
        // Interpolación lineal entre dos puntos según una fracción
        inicio: Pair<Double, Double>,
        fin: Pair<Double, Double>,
        fraccion: Double
    ): Pair<Double, Double> =
    // Calcular las coordenadas interpoladas entre dos puntos según una fracción
        (inicio.first + (fin.first - inicio.first) * fraccion) to
            (inicio.second + (fin.second - inicio.second) * fraccion)

    private fun coordenadas(puntos: List<Pair<Double, Double>>): List<Double> =
    // Convertir la lista de puntos en una lista de coordenadas para el Polyline
        puntos.flatMap { listOf(it.first, it.second) }

    private companion object {
        // Constante para el número de segmentos en la trayectoria
        const val SEGMENTOS = 120
    }
}