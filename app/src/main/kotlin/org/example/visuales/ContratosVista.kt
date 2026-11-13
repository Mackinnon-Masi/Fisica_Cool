package org.example.visuales

import javafx.geometry.Point2D
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import org.example.fisicas.VelocidadInicial

/**
 * Contratos de la capa visual. Cada cliente depende solo de la
 * responsabilidad que usa (ISP) y de abstracciones (DIP).
 */

interface IVistaEntrada {
    val raiz: Pane
    fun objetivoActual(): Point2D
    fun rapidezIngresada(): Double?
}

interface IVistaEstado {
    fun sincronizarProyectil()
    fun configurarEdicion(activa: Boolean)
    fun actualizarIndicadorTrayectoria(esAlta: Boolean)
    fun mostrarErrorDeDisparo()
    fun limpiarErrorDeDisparo()
    fun registrarLanzamiento(velocidad: VelocidadInicial)
}

interface IVistaTrayectoria {
    fun mostrarTrayectoria(
        velocidad: VelocidadInicial,
        objetivoX: Double,
        objetivoY: Double
    )

    fun mostrarEjes()
    fun actualizarTrayectoria(tiempoTranscurrido: Double)
    fun ocultarTrayectoria()
    fun estaFueraDeVista(anchoEscena: Double, altoEscena: Double): Boolean
}

interface IVistaNavegacion {
    fun posicionInicialY(): Double
    fun prepararPartida(origenY: Double)
    fun abrirAyuda()
}

interface IVistaSimulacion :
    IVistaEntrada,
    IVistaEstado,
    IVistaTrayectoria,
    IVistaNavegacion

interface IPanelControles {
    val nodo: VBox
    val estadoDisparo: Label
    fun rapidezIngresada(): Double?
    fun habilitarEdicion(activa: Boolean)
    fun mostrarIndicadorTrayectoria(esAlta: Boolean)
    fun mostrarError(mensaje: String?)
    fun reiniciar()
}

interface IPanelMetricas {
    val nodo: HBox
    fun preparar(origenY: Double)
    fun registrarLanzamiento(velocidad: VelocidadInicial)
    fun actualizar(posicionX: Double, posicionY: Double)
}

interface IPanelAyuda {
    val nodo: StackPane
    fun abrir()
    fun cerrar()
    fun alternar()
}

interface IEntradaVista {
    fun objetivoActual(): Point2D
    fun posicionInicialY(): Double
    fun mostrarEtiqueta(visible: Boolean)
}

interface ICapaProyectil {
    val nodo: Pane
    fun sincronizar()
    fun mostrarTrayectoria(
        velocidad: VelocidadInicial,
        objetivoX: Double,
        objetivoY: Double
    )

    fun mostrarEjes()
    fun actualizarTrayectoria(tiempoTranscurrido: Double)
    fun ocultar()
    fun estaFueraDeVista(anchoEscena: Double, altoEscena: Double): Boolean
}

interface IControladorTrayectoria {
    fun iniciar()
    fun prepararPartida(origenY: Double)
}

interface IVistaInicio {
    fun crearVista(
        onIniciarClick: () -> Unit,
        onCreditosClick: () -> Unit
    ): VBox
}

interface IVistaCreditos {
    fun crearVista(onVolverClick: () -> Unit): BorderPane
}
