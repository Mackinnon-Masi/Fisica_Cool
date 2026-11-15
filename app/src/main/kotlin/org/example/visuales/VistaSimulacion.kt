package org.example.visuales

import javafx.geometry.Insets
import javafx.geometry.Point2D
import javafx.scene.control.Button
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import org.example.fisicas.IProyectilSimulacion
import org.example.fisicas.VelocidadInicial
import org.example.trackeo.MouseTracker

/**
 * Fachada de la vista de simulacion. Delega cada responsabilidad en
 * un componente cohesionado (controles, metricas, ayuda, entrada y
 * capa del proyectil).
 */
class VistaSimulacion(
    private val simulacion: IProyectilSimulacion,
    onVolverClick: () -> Unit
) : IVistaSimulacion {

    private val capa: ICapaProyectil = CapaProyectil(simulacion.proyectil)
    private val metricas: IPanelMetricas = PanelMetricas()
    private val controles: IPanelControles = PanelControles()
    private val ayuda: IPanelAyuda = PanelAyuda()
    private val entrada: IEntradaVista

    override val raiz: StackPane

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
        entrada = EntradaVista(capa.nodo, raiz, MouseTracker())
    }

    override fun objetivoActual(): Point2D = entrada.objetivoActual()

    override fun rapidezIngresada(): Double? = controles.rapidezIngresada()

    override fun posicionInicialY(): Double = entrada.posicionInicialY()

    override fun prepararPartida(origenY: Double) {
        simulacion.reiniciar(origenY)
        metricas.preparar(origenY)
        controles.reiniciar()
        ayuda.cerrar()
        entrada.mostrarEtiqueta(true)
        capa.ocultar()
        sincronizarProyectil()
    }

    override fun abrirAyuda() {
        ayuda.abrir()
    }

    override fun sincronizarProyectil() {
        capa.sincronizar()
        metricas.actualizar(simulacion.proyectil.posicionX, simulacion.proyectil.posicionY)
    }

    override fun registrarLanzamiento(velocidad: VelocidadInicial) {
        metricas.registrarLanzamiento(velocidad)
    }

    override fun configurarEdicion(activa: Boolean) {
        controles.habilitarEdicion(activa)
        entrada.mostrarEtiqueta(!activa)
        if (activa) capa.ocultar()
    }

    override fun actualizarIndicadorTrayectoria(esAlta: Boolean) {
        controles.mostrarIndicadorTrayectoria(esAlta)
    }

    override fun mostrarErrorDeDisparo() {
        controles.mostrarError("Rapidez inválida o el objetivo está fuera del alcance.")
    }

    override fun limpiarErrorDeDisparo() {
        controles.mostrarError(null)
    }

    override fun mostrarTrayectoria(
        velocidad: VelocidadInicial,
        objetivoX: Double,
        objetivoY: Double
    ) {
        capa.mostrarTrayectoria(velocidad, objetivoX, objetivoY)
    }

    override fun mostrarEjes() {
        capa.mostrarEjes()
    }

    override fun actualizarTrayectoria(tiempoTranscurrido: Double) {
        capa.actualizarTrayectoria(tiempoTranscurrido)
        metricas.actualizar(simulacion.proyectil.posicionX, simulacion.proyectil.posicionY)
    }

    override fun ocultarTrayectoria() {
        capa.ocultar()
    }

    override fun estaFueraDeVista(anchoEscena: Double, altoEscena: Double): Boolean =
        capa.estaFueraDeVista(anchoEscena, altoEscena)
}
