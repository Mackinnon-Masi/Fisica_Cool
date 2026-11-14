package org.example.visuales

import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import org.example.fisicas.ILecturaProyectil
import org.example.fisicas.VelocidadInicial
import org.example.visuales.trayectoria.IVisualizadorTrayectoria
import org.example.visuales.trayectoria.VisualizadorTrayectoria

/** Lienzo donde se dibujan el proyectil y su trayectoria. */
class CapaProyectil(
    private val proyectil: ILecturaProyectil
) : ICapaProyectil {

    override val nodo = Pane()

    private val bala = Circle(proyectil.posicionX, proyectil.posicionY, 8.0).apply {
        fill = Color.WHITE
        stroke = Color.WHITE
        strokeWidth = 3.0
    }

    private val visualizador: IVisualizadorTrayectoria = VisualizadorTrayectoria(nodo)

    init {
        nodo.children.add(bala)
        bala.toFront()
    }

    override fun sincronizar() {
        bala.centerX = proyectil.posicionX
        bala.centerY = proyectil.posicionY
    }

    override fun mostrarTrayectoria(
        velocidad: VelocidadInicial,
        objetivoX: Double,
        objetivoY: Double
    ) {
        visualizador.mostrarPrediccion(
            origenX = proyectil.posicionX,
            origenY = proyectil.posicionY,
            velocidadX = velocidad.velocidadX * PIXELES_POR_METRO,
            velocidadY = velocidad.velocidadY * PIXELES_POR_METRO,
            gravedad = GRAVEDAD_METROS_POR_SEGUNDO_CUADRADO * PIXELES_POR_METRO,
            tiempoDeVuelo = velocidad.tiempoDeVuelo,
            objetivoX = objetivoX,
            objetivoY = objetivoY
        )
    }

    override fun mostrarEjes() {
        visualizador.mostrarEjes(proyectil.posicionX, proyectil.posicionY)
    }

    override fun actualizarTrayectoria(tiempoTranscurrido: Double) {
        visualizador.actualizarProgreso(tiempoTranscurrido)
        visualizador.actualizarEjes(proyectil.posicionX, proyectil.posicionY)
        sincronizar()
    }

    override fun ocultar() = visualizador.ocultar()

    override fun estaFueraDeVista(anchoEscena: Double, altoEscena: Double): Boolean =
        proyectil.posicionY > altoEscena || proyectil.posicionX > anchoEscena

    private companion object {
        const val PIXELES_POR_METRO = 100.0
        const val GRAVEDAD_METROS_POR_SEGUNDO_CUADRADO = 9.81
    }
}
