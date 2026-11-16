package org.example.visuales

import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.example.fisicas.SimulacionTrayectoria
import org.example.trackeo.KeyboardReader

/**
 * Escenario de la partida.
 *
 * Responsabilidad unica: coordinar la simulacion, su vista y su controlador
 * dentro de la escena.
 */
class EscenarioSimulacion(
    private val escena: Scene,
    private val simulacion: SimulacionTrayectoria,
    private val onVolver: () -> Unit
) {
    private val vista = VistaSimulacion(simulacion) { volverAlInicio() }
    private val controlador = ControladorTrayectoria(
        simulacion,
        vista,
        KeyboardReader().apply { attachTo(escena) },
        escena
    )
    private var posicionDisparoY = simulacion.proyectil.posicionY

    val raiz get() = vista.raiz

    init {
        bloquearActivacionBotones()
    }

    fun iniciar() {
        controlador.iniciar()
    }

    fun prepararPartida() {
        posicionDisparoY = vista.posicionInicialY()
        controlador.prepararPartida(posicionDisparoY)
        vista.abrirAyuda()
    }

    private fun volverAlInicio() {
        controlador.prepararPartida(posicionDisparoY)
        onVolver()
    }

    private fun bloquearActivacionBotones() {
        val bloqueo = EventHandler<KeyEvent> { evento ->
            if (
                escena.root === vista.raiz &&
                (evento.code == KeyCode.SPACE || evento.code == KeyCode.ENTER)
            ) {
                evento.consume()
            }
        }
        escena.addEventFilter(KeyEvent.KEY_PRESSED, bloqueo)
        escena.addEventFilter(KeyEvent.KEY_RELEASED, bloqueo)
    }
}
