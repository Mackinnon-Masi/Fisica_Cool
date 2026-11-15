package org.example.visuales

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import org.example.fisicas.VelocidadInicial
import kotlin.math.atan2

/** Panel que muestra las metricas X, Y, altura maxima y angulo. */
class PanelMetricas : IPanelMetricas {

    private val valorX = Label("-- m")
    private val valorY = Label("-- m")
    private val valorYMaxima = Label("-- m")
    private val valorAngulo = Label("--°")

    private var origenY = 0.0
    private var alturaMaxima: Double? = null
    private var anguloLanzamiento: Double? = null

    override val nodo: HBox

    init {
        fun metrica(titulo: String, valor: Label, color: Color): VBox = VBox(
            3.0,
            Label(titulo).apply { textFill = color },
            valor.apply { textFill = Color.WHITE }
        )

        nodo = HBox(
            22.0,
            metrica("X", valorX, COLOR_X),
            metrica("Y", valorY, COLOR_Y),
            metrica("Y máxima", valorYMaxima, COLOR_Y),
            metrica("Ω", valorAngulo, COLOR_ANGULO)
        ).apply {
            alignment = Pos.CENTER_LEFT
            padding = Insets(8.0, 0.0, 8.0, 0.0)
        }
    }

    override fun preparar(origenY: Double) {
        this.origenY = origenY
        alturaMaxima = null
        anguloLanzamiento = null
        valorYMaxima.text = "-- m"
        valorAngulo.text = "--°"
    }

    override fun registrarLanzamiento(velocidad: VelocidadInicial) {
        anguloLanzamiento = Math.toDegrees(
            atan2(-velocidad.velocidadY, velocidad.velocidadX)
        )
        alturaMaxima = if (velocidad.velocidadY < 0.0) {
            velocidad.velocidadY * velocidad.velocidadY /
                (2.0 * GRAVEDAD_METROS_POR_SEGUNDO_CUADRADO)
        } else {
            0.0
        }
        actualizarDerivadas()
    }

    override fun actualizar(posicionX: Double, posicionY: Double) {
        valorX.text = "%.2f m".format(posicionX / PIXELES_POR_METRO)
        valorY.text = "%.2f m".format((origenY - posicionY) / PIXELES_POR_METRO)
        actualizarDerivadas()
    }

    private fun actualizarDerivadas() {
        valorYMaxima.text = alturaMaxima?.let { "%.2f m".format(it) } ?: "-- m"
        valorAngulo.text = anguloLanzamiento?.let { "%.1f°".format(it) } ?: "--°"
    }

    private companion object {
        const val PIXELES_POR_METRO = 100.0
        const val GRAVEDAD_METROS_POR_SEGUNDO_CUADRADO = 9.81
        val COLOR_X = Color.web("#F25022")
        val COLOR_Y = Color.web("#00A4EF")
        val COLOR_ANGULO = Color.web("#FFB900")
    }
}
