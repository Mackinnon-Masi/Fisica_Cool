package org.example.visuales

import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.stage.Stage
import org.example.fisicas.CalculadoraTrayectoria
import org.example.fisicas.Proyectil
import org.example.fisicas.SimulacionTrayectoria
import org.example.trackeo.KeyboardReader

/**
 * Punto de composicion de la aplicacion.
 *
 * Responsabilidad unica: armar la simulacion, la vista, el controlador y la
 * ventana principal.
 */
class Pantalla : Application() {
    private lateinit var escena: Scene
    private lateinit var rootInicio: Pane
    private lateinit var vistaSimulacion: VistaSimulacion
    private lateinit var controlador: ControladorTrayectoria
    private var posicionDisparoY = POSICION_DISPARO_Y

    override fun start(stage: Stage) {
        val simulacion = crearSimulacion()
        vistaSimulacion = crearVistaSimulacion(simulacion)
        escena = Scene(vistaSimulacion.raiz, ANCHO_ESCENA, ALTO_ESCENA, Color.BLACK)
        controlador = crearControlador(simulacion)
        bloquearActivacionBotones()
        rootInicio = crearInicio()

        stage.title = TITULO
        mostrarVista(rootInicio)
        stage.scene = escena
        stage.show()
        controlador.iniciar()
    }

    private fun crearSimulacion(): SimulacionTrayectoria = SimulacionTrayectoria(
        calculadora = CalculadoraTrayectoria(),
        pixelesPorMetro = PIXELES_POR_METRO,
        gravedad = GRAVEDAD_METROS_POR_SEGUNDO_CUADRADO,
        proyectil = Proyectil(posicionX = 100.0, posicionY = 620.0)
    )

    private fun crearVistaSimulacion(simulacion: SimulacionTrayectoria): VistaSimulacion =
        VistaSimulacion(simulacion) {
            controlador.prepararPartida(posicionDisparoY)
            mostrarVista(rootInicio)
        }

    private fun crearControlador(simulacion: SimulacionTrayectoria): ControladorTrayectoria {
        val teclado = KeyboardReader().apply { attachTo(escena) }
        return ControladorTrayectoria(simulacion, vistaSimulacion, teclado, escena)
    }

    private fun bloquearActivacionBotones() {
        val bloqueo = EventHandler<KeyEvent> { evento ->
            if (
                escena.root === vistaSimulacion.raiz &&
                (evento.code == KeyCode.SPACE || evento.code == KeyCode.ENTER)
            ) {
                evento.consume()
            }
        }
        escena.addEventFilter(KeyEvent.KEY_PRESSED, bloqueo)
        escena.addEventFilter(KeyEvent.KEY_RELEASED, bloqueo)
    }

    private fun crearInicio(): Pane {
        val rootCreditos = Creditos().crearVista { mostrarVista(rootInicio) }
        return Inicio().crearVista(
            onIniciarClick = {
                mostrarVista(vistaSimulacion.raiz)
                posicionDisparoY = vistaSimulacion.posicionInicialY()
                controlador.prepararPartida(posicionDisparoY)
                vistaSimulacion.abrirAyuda()
            },
            onCreditosClick = { mostrarVista(rootCreditos) }
        )
    }

    private fun mostrarVista(root: Pane) {
        escena.root = root
        root.applyCss()
        root.layout()
    }

    private companion object {
        const val TITULO = "Proyecto Física MRUV re fachero facherito"
        const val ANCHO_ESCENA = 1280.0
        const val ALTO_ESCENA = 720.0
        const val POSICION_DISPARO_Y = 620.0
        const val PIXELES_POR_METRO = 100.0
        const val GRAVEDAD_METROS_POR_SEGUNDO_CUADRADO = 9.81
    }
}
