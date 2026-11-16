package org.example.visuales

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.stage.Stage
import org.example.fisicas.FabricaSimulacion

/**
 * Punto de entrada de la aplicacion.
 *
 * Responsabilidad unica: configurar la ventana y delegar la navegacion y la
 * partida en sus colaboradores.
 */
class Pantalla : Application() {

    override fun start(stage: Stage) {
        val escena = Scene(Pane(), ANCHO_ESCENA, ALTO_ESCENA, Color.BLACK)
        val navegacion = Navegacion(escena)
        val escenario = EscenarioSimulacion(escena, FabricaSimulacion().crear()) {
            navegacion.mostrarInicio()
        }
        navegacion.configurarInicio {
            navegacion.mostrar(escenario.raiz)
            escenario.prepararPartida()
        }

        stage.title = TITULO
        navegacion.mostrarInicio()
        stage.scene = escena
        stage.show()
        escenario.iniciar()
    }

    private companion object {
        const val TITULO = "Proyecto Física MRUV re fachero facherito"
        const val ANCHO_ESCENA = 1280.0
        const val ALTO_ESCENA = 720.0
    }
}
