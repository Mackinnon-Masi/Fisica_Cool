package org.example.visuales

import javafx.geometry.Insets
import javafx.geometry.Point2D
import javafx.scene.control.Button
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import org.example.fisicas.SimulacionTrayectoria
import org.example.fisicas.VelocidadInicial
import org.example.trackeo.MouseTracker

/**
 * Vista de la simulacion.
 *
 * Responsabilidad unica: componer el layout de la partida y traducir las
 * acciones del usuario a operaciones sobre la simulacion.
 */
class VistaSimulacion(
    private val simulacion: SimulacionTrayectoria,
    onVolverClick: () -> Unit
) {
    private val capa = CapaProyectil(simulacion.proyectil)
    private val metricas = PanelMetricas()
    private val controles = PanelControles()
    private val ayuda = PanelAyuda()
    private val mouse = MouseTracker()

    val raiz: StackPane

    init {
        val botonInfo = Button("ⓘ").apply {
            style = "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 24px; -fx-cursor: hand;"
            setOnAction { ayuda.alternar() }
        }
        val encabezado = BorderPane().apply {
            left = BotonVolver(onVolverClick)
            right = botonInfo
        }
        val contenido = BorderPane().apply {
            top = VBox(
                8.0,
                encabezado,
                controles.nodo,
                metricas.nodo,
                controles.estadoDisparo
            )
            center = capa.nodo
            style = "-fx-background-color: black;"
            padding = Insets(20.0, 0.0, 0.0, 30.0)
        }
        raiz = StackPane(contenido, ayuda.nodo)
        mouse.attachTo(raiz)
    }

    fun objetivoActual(): Point2D = capa.nodo.sceneToLocal(mouse.lastX, mouse.lastY)

    fun rapidezIngresada(): Double? = controles.rapidezIngresada()

    fun posicionInicialY(): Double = (capa.nodo.height - 20.0).coerceAtLeast(20.0)

    fun prepararPartida(origenY: Double) {
        simulacion.reiniciar(origenY)
        metricas.preparar(origenY)
        controles.reiniciar()
        ayuda.cerrar()
        mouse.mostrarEtiqueta(true)
        capa.ocultar()
        sincronizarProyectil()
    }

    fun abrirAyuda() {
        ayuda.abrir()
    }

    fun sincronizarProyectil() {
        capa.sincronizar()
        metricas.actualizar(simulacion.proyectil.posicionX, simulacion.proyectil.posicionY)
    }

    fun registrarLanzamiento(velocidad: VelocidadInicial) {
        metricas.registrarLanzamiento(velocidad)
    }

    fun configurarEdicion(activa: Boolean) {
        controles.habilitarEdicion(activa)
        mouse.mostrarEtiqueta(!activa)
        if (activa) capa.ocultar()
    }

    fun actualizarIndicadorTrayectoria(esAlta: Boolean) {
        controles.mostrarIndicadorTrayectoria(esAlta)
    }

    fun mostrarErrorDeDisparo() {
        controles.mostrarError("Rapidez inválida o el objetivo está fuera del alcance.")
    }

    fun limpiarErrorDeDisparo() {
        controles.mostrarError(null)
    }

    fun mostrarTrayectoria(velocidad: VelocidadInicial, objetivo: Point2D) {
        capa.mostrarTrayectoria(velocidad, objetivo)
    }

    fun mostrarEjes() {
        capa.mostrarEjes()
    }

    fun actualizarTrayectoria(tiempoTranscurrido: Double) {
        capa.actualizarTrayectoria(tiempoTranscurrido)
        metricas.actualizar(simulacion.proyectil.posicionX, simulacion.proyectil.posicionY)
    }

    fun ocultarTrayectoria() {
        capa.ocultar()
    }

    fun estaFueraDeVista(anchoEscena: Double, altoEscena: Double): Boolean =
        capa.estaFueraDeVista(anchoEscena, altoEscena)
}
