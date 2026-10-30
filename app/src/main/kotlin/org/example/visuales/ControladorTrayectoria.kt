package org.example.visuales

import javafx.animation.AnimationTimer
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import org.example.fisicas.EstadoTrayectoria
import org.example.fisicas.SimulacionTrayectoria
import org.example.trackeo.KeyboardReader

class ControladorTrayectoria(
    // Controlador de la simulación de trayectoria, que gestiona la interacción del usuario y la actualización de la vista
    private val simulacion: SimulacionTrayectoria,
    private val vista: VistaSimulacion,
    private val teclado: KeyboardReader,
    private val escena: Scene
) {
    private var modoEdicion = false
    private var origenY = simulacion.proyectil.posicionY
    private var ultimoTiempo = 0L

    private val temporizador = object : AnimationTimer() {
        // Método que se llama en cada frame de la animación para actualizar la simulación y la vista
        override fun handle(now: Long) {
            // Actualiza el modo de edición, el tipo de trayectoria, la predicción de la trayectoria, procesa el disparo y avanza la simulación
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
        // Prepara la partida reiniciando la simulación y configurando la vista para el modo de edición
        this.origenY = origenY
        modoEdicion = false
        ultimoTiempo = 0L
        vista.prepararPartida(origenY)
    }

    private fun actualizarModoEdicion() {
        // Alterna el modo de edición si se presiona la tecla 'E' y la escena actual es la vista de simulación
        if (escena.root !== vista.raiz || !teclado.wasJustPressed(KeyCode.E)) return
        if (simulacion.estado != EstadoTrayectoria.PREPARADA) {
            // Si la simulación está en vuelo o ya terminó, reiníciala para permitir la edición
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
            // Alterna el tipo de trayectoria (alta o baja) si se presiona la tecla 'Q' y la escena actual es la vista de simulación
            escena.root !== vista.raiz ||
            simulacion.estado == EstadoTrayectoria.EN_VUELO ||
            !modoEdicion || !teclado.wasJustPressed(KeyCode.Q)
        ) return

        simulacion.alternarTrayectoria()
        vista.actualizarIndicadorTrayectoria(simulacion.trayectoriaAlta)
    }

    private fun actualizarPrediccion() {
        if (
            // Actualiza la predicción de la trayectoria si la escena actual es la vista de simulación, la simulación está preparada y no está en modo de edición
            escena.root !== vista.raiz ||
            simulacion.estado != EstadoTrayectoria.PREPARADA || modoEdicion
        ) return
        // Obtiene el objetivo actual y la rapidez ingresada por el usuario, y calcula la velocidad inicial necesaria para alcanzar el objetivo. Si no se puede calcular la velocidad, oculta la trayectoria; de lo contrario, muestra la trayectoria predicha.
        val objetivo = vista.objetivoActual()
        val rapidez = vista.rapidezIngresada() ?: return vista.ocultarTrayectoria()
        val velocidad = simulacion.calcularVelocidad(objetivo.x, objetivo.y, rapidez)
        if (velocidad == null) vista.ocultarTrayectoria()
        else vista.mostrarTrayectoria(velocidad, objetivo.x, objetivo.y)
    }

    private fun procesarDisparo(now: Long) {
        // Procesa el disparo del proyectil si la escena actual es la vista de simulación, la simulación no está en vuelo, no está en modo de edición y se presiona la tecla 'SPACE'. Calcula la velocidad inicial necesaria para alcanzar el objetivo y, si es válida, muestra la trayectoria y los ejes; de lo contrario, muestra un error de disparo.
        if (
            escena.root !== vista.raiz ||
            simulacion.estado == EstadoTrayectoria.EN_VUELO ||
            modoEdicion || !teclado.wasJustPressed(KeyCode.SPACE)
        ) return

        val objetivo = vista.objetivoActual()
        val rapidez = vista.rapidezIngresada()
        // Calcula la velocidad inicial necesaria para alcanzar el objetivo y, si es válida, muestra la trayectoria y los ejes; de lo contrario, muestra un error de disparo.
        val velocidad = rapidez?.let { simulacion.disparar(objetivo.x, objetivo.y, it) }
        if (velocidad == null) {
            vista.mostrarErrorDeDisparo()
            return
        }
        // Muestra la trayectoria y los ejes, y limpia cualquier error de disparo previo
        vista.mostrarTrayectoria(velocidad, objetivo.x, objetivo.y)
        vista.registrarLanzamiento(velocidad)
        vista.mostrarEjes()
        vista.limpiarErrorDeDisparo()
        ultimoTiempo = now
    }

    private fun avanzarSimulacion(now: Long) {
        // Avanza la simulación si la escena actual es la vista de simulación y la simulación está en vuelo. Calcula el tiempo transcurrido desde el último frame y actualiza la simulación y la vista en consecuencia. Si el proyectil sale de la vista, detiene la simulación y oculta la trayectoria.
        if (simulacion.estado != EstadoTrayectoria.EN_VUELO) {
            ultimoTiempo = now
            return
        }
        if (ultimoTiempo == 0L) {
            ultimoTiempo = now
            return
        }

        // Calcula el tiempo transcurrido desde el último frame en segundos
        val deltaSegundos = (now - ultimoTiempo) / 1_000_000_000.0
        ultimoTiempo = now
        simulacion.avanzar(deltaSegundos)
        vista.actualizarTrayectoria(simulacion.tiempoTranscurrido)
        if (vista.estaFueraDeVista(escena.width, escena.height)) {
            // Si el proyectil sale de la vista, detiene la simulación y oculta la trayectoria
            simulacion.detener()
            vista.ocultarTrayectoria()
        }
    }
}