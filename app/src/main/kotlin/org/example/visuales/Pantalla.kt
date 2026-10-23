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
    // Inicia la aplicación JavaFX y configura la ventana principal de la simulación
    override fun start(stage: Stage) {
        // Configura la escena principal de la aplicación y los elementos de la simulación
        lateinit var rootInicio: Pane
        lateinit var escena: Scene
        var posicionDisparoY = 620.0

        fun mostrarVista(root: Pane) {
            // Cambia la vista actual de la escena a la raíz proporcionada y actualiza el diseño
            escena.root = root
            root.applyCss()
            root.layout()
        }

        val simulacion = SimulacionTrayectoria(
            // Configura la simulación de la trayectoria del proyectil con los parámetros iniciales
            calculadora = CalculadoraTrayectoria(),
            pixelesPorMetro = PIXELES_POR_METRO,
            gravedad = GRAVEDAD_METROS_POR_SEGUNDO_CUADRADO,
            proyectil = Proyectil(posicionX = 100.0, posicionY = 620.0)
        )
        lateinit var controlador: ControladorTrayectoria
        val vistaSimulacion = VistaSimulacion(simulacion) {
            // Configura la acción al hacer clic en el botón de volver, mostrando la vista de inicio y preparando la partida con la posición de disparo actual
            controlador.prepararPartida(posicionDisparoY)
            mostrarVista(rootInicio)
        }
        escena = Scene(vistaSimulacion.raiz, 1280.0, 720.0, Color.BLACK)
        val teclado = KeyboardReader().apply { attachTo(escena) }
        // Configura el controlador de la simulación, que maneja la lógica de la trayectoria del proyectil y la interacción con el teclado y la escena
        controlador = ControladorTrayectoria(simulacion, vistaSimulacion, teclado, escena)

        val bloquearActivacionBotones = EventHandler<KeyEvent> { evento ->
            // Bloquea la activación de botones al presionar ESPACIO o ENTER mientras se está en la vista de simulación, evitando que se activen acciones no deseadas
            if (
                escena.root === vistaSimulacion.raiz &&
                (evento.code == KeyCode.SPACE || evento.code == KeyCode.ENTER)
            ) {
                evento.consume()
            }
        }
        escena.addEventFilter(KeyEvent.KEY_PRESSED, bloquearActivacionBotones)
        // Agrega un filtro de eventos para bloquear la activación de botones al presionar ESPACIO o ENTER mientras se está en la vista de simulación, evitando que se activen acciones no deseadas
        escena.addEventFilter(KeyEvent.KEY_RELEASED, bloquearActivacionBotones)

        val pantallaCreditos = Creditos()
        val rootCreditos = pantallaCreditos.crearVista {
            // Configura la acción al hacer clic en el botón de volver desde la vista de créditos, mostrando la vista de inicio
            mostrarVista(rootInicio)
        }
        rootInicio = Inicio().crearVista(
            // Configura las acciones al hacer clic en los botones de iniciar y créditos desde la vista de inicio, mostrando la vista de simulación o la vista de créditos según corresponda
            onIniciarClick = {
                // Muestra la vista de simulación y prepara la partida con la posición de disparo inicial, calculada a partir de la vista de simulación
                mostrarVista(vistaSimulacion.raiz)
                posicionDisparoY = vistaSimulacion.posicionInicialY()
                controlador.prepararPartida(posicionDisparoY)
            },
            onCreditosClick = { mostrarVista(rootCreditos) }
        )
        // Configura la ventana principal de la aplicación con el título, la vista inicial y muestra la escena
        stage.title = "Proyecto Física MRUV re fachero facherito"
        mostrarVista(rootInicio)
        stage.scene = escena
        stage.show()
        controlador.iniciar()
    }
}