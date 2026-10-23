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

private const val PIXELES_POR_METRO = 100.0
private const val GRAVEDAD_METROS_POR_SEGUNDO_CUADRADO = 9.81

class Pantalla : Application() {
    override fun start(stage: Stage) {
        lateinit var rootInicio: Pane
        lateinit var escena: Scene
        var posicionDisparoY = 620.0

        fun mostrarVista(root: Pane) {
            escena.root = root
            root.applyCss()
            root.layout()
        }

        val simulacion = SimulacionTrayectoria(
            calculadora = CalculadoraTrayectoria(),
            pixelesPorMetro = PIXELES_POR_METRO,
            gravedad = GRAVEDAD_METROS_POR_SEGUNDO_CUADRADO,
            proyectil = Proyectil(posicionX = 100.0, posicionY = 620.0)
        )
        lateinit var controlador: ControladorTrayectoria
        val vistaSimulacion = VistaSimulacion(simulacion) {
            controlador.prepararPartida(posicionDisparoY)
            mostrarVista(rootInicio)
        }
        escena = Scene(vistaSimulacion.raiz, 1280.0, 720.0, Color.BLACK)
        val teclado = KeyboardReader().apply { attachTo(escena) }
        controlador = ControladorTrayectoria(simulacion, vistaSimulacion, teclado, escena)

        val bloquearActivacionBotones = EventHandler<KeyEvent> { evento ->
            if (
                escena.root === vistaSimulacion.raiz &&
                (evento.code == KeyCode.SPACE || evento.code == KeyCode.ENTER)
            ) {
                evento.consume()
            }
        }
        escena.addEventFilter(KeyEvent.KEY_PRESSED, bloquearActivacionBotones)
        escena.addEventFilter(KeyEvent.KEY_RELEASED, bloquearActivacionBotones)

        val pantallaCreditos = Creditos()
        val rootCreditos = pantallaCreditos.crearVista {
            mostrarVista(rootInicio)
        }
        rootInicio = Inicio().crearVista(
            onIniciarClick = {
                mostrarVista(vistaSimulacion.raiz)
                posicionDisparoY = vistaSimulacion.posicionInicialY()
                controlador.prepararPartida(posicionDisparoY)
            },
            onCreditosClick = { mostrarVista(rootCreditos) }
        )

        stage.title = "Proyecto Física MRUV re fachero facherito"
        mostrarVista(rootInicio)
        stage.scene = escena
        stage.show()
        controlador.iniciar()
    }
}