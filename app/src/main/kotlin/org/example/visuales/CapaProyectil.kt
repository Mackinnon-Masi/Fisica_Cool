package org.example.visuales

import javafx.geometry.Point2D
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import org.example.fisicas.Proyectil
import org.example.fisicas.VelocidadInicial
import org.example.visuales.trayectoria.VisualizadorAngulo
import org.example.visuales.trayectoria.VisualizadorEjes
import org.example.visuales.trayectoria.VisualizadorRecorrido

/**
 * Lienzo donde se dibujan el proyectil y su trayectoria.
 *
 * Responsabilidad unica: reflejar visualmente el estado de la simulacion.
 */
class CapaProyectil(private val proyectil: Proyectil) {

    val nodo = Pane()

    private val bala = Circle(proyectil.posicionX, proyectil.posicionY, 8.0).apply {
        fill = Color.WHITE
        stroke = Color.WHITE
        strokeWidth = 3.0
    }

    private val recorrido = VisualizadorRecorrido(nodo)
    private val ejes = VisualizadorEjes(nodo)
    private val angulo = VisualizadorAngulo(nodo)

    init {
        nodo.children.add(bala)
        bala.toFront()
    }

    fun sincronizar() {
        bala.centerX = proyectil.posicionX
        bala.centerY = proyectil.posicionY
    }

    fun mostrarTrayectoria(velocidad: VelocidadInicial, objetivo: Point2D) {
        if (!velocidad.tiempoDeVuelo.isFinite() || velocidad.tiempoDeVuelo <= 0.0) {
            ocultar()
            return
        }
        val origen = posicionActual()
        val velocidadEnPixeles = VelocidadInicial(
            velocidadX = velocidad.velocidadX * PIXELES_POR_METRO,
            velocidadY = velocidad.velocidadY * PIXELES_POR_METRO,
            tiempoDeVuelo = velocidad.tiempoDeVuelo
        )
        recorrido.mostrar(
            origen,
            velocidadEnPixeles,
            GRAVEDAD_METROS_POR_SEGUNDO_CUADRADO * PIXELES_POR_METRO,
            objetivo
        )
        angulo.mostrar(origen, velocidadEnPixeles)
    }

    fun mostrarEjes() {
        ejes.mostrar(posicionActual())
    }

    fun actualizarTrayectoria(tiempoTranscurrido: Double) {
        recorrido.actualizar(tiempoTranscurrido)
        ejes.actualizar(posicionActual())
        sincronizar()
    }

    fun ocultar() {
        recorrido.ocultar()
        ejes.ocultar()
        angulo.ocultar()
    }

    fun estaFueraDeVista(anchoEscena: Double, altoEscena: Double): Boolean =
        proyectil.posicionY > altoEscena || proyectil.posicionX > anchoEscena

    private fun posicionActual(): Point2D = Point2D(proyectil.posicionX, proyectil.posicionY)

    private companion object {
        const val PIXELES_POR_METRO = 100.0
        const val GRAVEDAD_METROS_POR_SEGUNDO_CUADRADO = 9.81
    }
}
