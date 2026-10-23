package org.example.visuales

import javafx.animation.AnimationTimer
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import org.example.fisicas.EstadoTrayectoria
import org.example.fisicas.SimulacionTrayectoria
import org.example.trackeo.KeyboardReader

class ControladorTrayectoria(
    private val simulacion: SimulacionTrayectoria,
    private val vista: VistaSimulacion,
    private val teclado: KeyboardReader,
    private val escena: Scene
) {
    private var modoEdicion = false
    private var origenY = simulacion.proyectil.posicionY
    private var ultimoTiempo = 0L

    private val temporizador = object : AnimationTimer() {
        override fun handle(now: Long) {
            actualizarModoEdicion()
            actualizarTipoTrayectoria()
            actualizarPrediccion()
            procesarDisparo(now)
            avanzarSimulacion(now)
        }
    }

    fun iniciar() {
        temporizador.start()
    }

    fun prepararPartida(origenY: Double) {
        this.origenY = origenY
        modoEdicion = false
        ultimoTiempo = 0L
        vista.prepararPartida(origenY)
    }

    private fun actualizarModoEdicion() {
        if (escena.root !== vista.raiz || !teclado.wasJustPressed(KeyCode.E)) return
        if (simulacion.estado == EstadoTrayectoria.EN_VUELO) {
            simulacion.reiniciar(origenY)
            vista.sincronizarProyectil()
            vista.limpiarErrorDeDisparo()
            modoEdicion = true
        } else {
            modoEdicion = !modoEdicion
        }
        vista.configurarEdicion(modoEdicion)
    }

    private fun actualizarTipoTrayectoria() {
        if (
            escena.root !== vista.raiz ||
            simulacion.estado == EstadoTrayectoria.EN_VUELO ||
            !modoEdicion || !teclado.wasJustPressed(KeyCode.Q)
        ) return

        simulacion.alternarTrayectoria()
        vista.actualizarIndicadorTrayectoria(simulacion.trayectoriaAlta)
    }

    private fun actualizarPrediccion() {
        if (
            escena.root !== vista.raiz ||
            simulacion.estado != EstadoTrayectoria.PREPARADA || modoEdicion
        ) return

        val objetivo = vista.objetivoActual()
        val rapidez = vista.rapidezIngresada() ?: return vista.ocultarTrayectoria()
        val velocidad = simulacion.calcularVelocidad(objetivo.x, objetivo.y, rapidez)
        if (velocidad == null) vista.ocultarTrayectoria()
        else vista.mostrarTrayectoria(velocidad, objetivo.x, objetivo.y)
    }

    private fun procesarDisparo(now: Long) {
        if (
            escena.root !== vista.raiz ||
            simulacion.estado == EstadoTrayectoria.EN_VUELO ||
            modoEdicion || !teclado.wasJustPressed(KeyCode.SPACE)
        ) return

        val objetivo = vista.objetivoActual()
        val rapidez = vista.rapidezIngresada()
        val velocidad = rapidez?.let { simulacion.disparar(objetivo.x, objetivo.y, it) }
        if (velocidad == null) {
            vista.mostrarErrorDeDisparo()
            return
        }

        vista.mostrarTrayectoria(velocidad, objetivo.x, objetivo.y)
        vista.mostrarEjes()
        vista.limpiarErrorDeDisparo()
        ultimoTiempo = now
    }

    private fun avanzarSimulacion(now: Long) {
        if (simulacion.estado != EstadoTrayectoria.EN_VUELO) {
            ultimoTiempo = now
            return
        }
        if (ultimoTiempo == 0L) {
            ultimoTiempo = now
            return
        }

        val deltaSegundos = (now - ultimoTiempo) / 1_000_000_000.0
        ultimoTiempo = now
        simulacion.avanzar(deltaSegundos)
        vista.actualizarTrayectoria(simulacion.tiempoTranscurrido)
        if (vista.estaFueraDeVista(escena.width, escena.height)) {
            simulacion.detener()
            vista.ocultarTrayectoria()
        }
    }
}