package org.example.visuales.trayectoria

import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Arc
import javafx.scene.shape.ArcType
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.shape.Polyline
import javafx.scene.text.Font
import javafx.scene.text.Text
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.sin

class VisualizadorTrayectoria(private val contenedor: Pane) {
    private val recorrido = Polyline()
    private val pendiente = Polyline()
    private val puntoFinal = Circle()
    private val ejeX = Line()
    private val ejeY = Line()
    private val arcoAngulo = Arc()
    private val etiquetaX = Text("x")
    private val etiquetaY = Text("y")
    private val etiquetaOmega = Text("Ω")
    private var puntos = emptyList<Pair<Double, Double>>()
    private var tiempoTotal = 0.0
    private var origenEjesX = 0.0
    private var origenEjesY = 0.0
    private var ejesActivos = false

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
        configurarEje(ejeX, Color.web("#F25022"))
        configurarEje(ejeY, Color.web("#00A4EF"))
        configurarEtiqueta(etiquetaX, Color.web("#F25022"))
        configurarEtiqueta(etiquetaY, Color.web("#00A4EF"))
        puntoFinal.apply {
            radius = RADIO_PUNTO_FINAL
            fill = Color.LIMEGREEN
            stroke = Color.LIMEGREEN
            strokeWidth = 1.0
            isMouseTransparent = true
            isVisible = false
        }
        arcoAngulo.apply {
            type = ArcType.OPEN
            fill = Color.TRANSPARENT
            stroke = COLOR_ANGULO
            strokeWidth = 2.0
            isMouseTransparent = true
            isVisible = false
        }
        configurarEtiqueta(etiquetaOmega, COLOR_ANGULO)
        contenedor.children.addAll(
            pendiente,
            recorrido,
            ejeX,
            ejeY,
            arcoAngulo,
            etiquetaX,
            etiquetaY,
            etiquetaOmega,
            puntoFinal
        )
    }

    fun mostrarPrediccion(
        origenX: Double,
        origenY: Double,
        velocidadX: Double,
        velocidadY: Double,
        gravedad: Double,
        tiempoDeVuelo: Double,
        objetivoX: Double,
        objetivoY: Double
    ) {
        if (!tiempoDeVuelo.isFinite() || tiempoDeVuelo <= 0.0) {
            ocultar()
            return
        }

        tiempoTotal = tiempoDeVuelo
        puntoFinal.centerX = objetivoX
        puntoFinal.centerY = objetivoY
        puntoFinal.isVisible = true

        val anguloGrados = Math.toDegrees(atan2(-velocidadY, velocidadX))
        arcoAngulo.centerX = origenX
        arcoAngulo.centerY = origenY
        arcoAngulo.radiusX = RADIO_ARCO_ANGULO
        arcoAngulo.radiusY = RADIO_ARCO_ANGULO
        arcoAngulo.startAngle = 0.0
        arcoAngulo.length = anguloGrados
        arcoAngulo.isVisible = abs(anguloGrados) > UMBRAL_ANGULO
        val anguloMedio = Math.toRadians(anguloGrados / 2.0)
        etiquetaOmega.x = origenX + (RADIO_ARCO_ANGULO + MARGEN_OMEGA) * cos(anguloMedio)
        etiquetaOmega.y = origenY - (RADIO_ARCO_ANGULO + MARGEN_OMEGA) * sin(anguloMedio)
        etiquetaOmega.isVisible = arcoAngulo.isVisible

        puntos = (0..CANTIDAD_SEGMENTOS).map { indice ->
            val tiempo = tiempoDeVuelo * indice / CANTIDAD_SEGMENTOS
            if (indice == CANTIDAD_SEGMENTOS) {
                objetivoX to objetivoY
            } else {
                (origenX + velocidadX * tiempo) to
                    (origenY + velocidadY * tiempo + 0.5 * gravedad * tiempo * tiempo)
            }
        }
        pendiente.isVisible = true
        recorrido.isVisible = true
        actualizarProgreso(0.0)
    }

    fun actualizarProgreso(tiempoTranscurrido: Double) {
        if (puntos.size < 2 || tiempoTotal <= 0.0) return

        val posicion = (tiempoTranscurrido / tiempoTotal)
            .coerceIn(0.0, 1.0) * (puntos.size - 1)
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

    fun mostrarEjes(origenX: Double, origenY: Double) {
        origenEjesX = origenX
        origenEjesY = origenY
        ejesActivos = true
        actualizarEjes(origenX, origenY)
    }

    fun actualizarEjes(posicionX: Double, posicionY: Double) {
        if (!ejesActivos) return

        ejeX.startX = origenEjesX
        ejeX.startY = origenEjesY
        ejeX.endX = posicionX
        ejeX.endY = origenEjesY
        ejeY.startX = origenEjesX
        ejeY.startY = origenEjesY
        ejeY.endX = origenEjesX
        ejeY.endY = posicionY

        val desplazamientoX = abs(posicionX - origenEjesX) > UMBRAL_VISIBILIDAD
        val desplazamientoY = abs(posicionY - origenEjesY) > UMBRAL_VISIBILIDAD
        ejeX.isVisible = desplazamientoX
        etiquetaX.isVisible = desplazamientoX
        etiquetaX.x = if (posicionX >= origenEjesX) {
            posicionX + MARGEN_ETIQUETA
        } else {
            posicionX - etiquetaX.layoutBounds.width - MARGEN_ETIQUETA
        }
        etiquetaX.y = origenEjesY - MARGEN_ETIQUETA

        ejeY.isVisible = desplazamientoY
        etiquetaY.isVisible = desplazamientoY
        etiquetaY.x = origenEjesX + MARGEN_ETIQUETA
        etiquetaY.y = posicionY - MARGEN_ETIQUETA

    }

    fun ocultar() {
        puntos = emptyList()
        ejesActivos = false
        recorrido.isVisible = false
        pendiente.isVisible = false
        ejeX.isVisible = false
        ejeY.isVisible = false
        puntoFinal.isVisible = false
        arcoAngulo.isVisible = false
        etiquetaX.isVisible = false
        etiquetaY.isVisible = false
        etiquetaOmega.isVisible = false
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
        const val CANTIDAD_SEGMENTOS = 120
        const val MARGEN_ETIQUETA = 6.0
        const val UMBRAL_VISIBILIDAD = 0.5
        const val RADIO_PUNTO_FINAL = 3.5
        const val RADIO_ARCO_ANGULO = 34.0
        const val MARGEN_OMEGA = 8.0
        const val UMBRAL_ANGULO = 0.5
        val COLOR_ANGULO = Color.web("#FFB900")
    }
}