package org.example.visuales

import javafx.geometry.Insets
import javafx.geometry.Point2D
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.control.TextFormatter
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import org.example.fisicas.SimulacionTrayectoria
import org.example.fisicas.VelocidadInicial
import org.example.trackeo.MouseTracker
import org.example.visuales.trayectoria.VisualizadorTrayectoria

class VistaSimulacion(
    private val simulacion: SimulacionTrayectoria,
    onVolverClick: () -> Unit
) {
    private val pane = Pane()
    private val proyectil = simulacion.proyectil
    private val bala = Circle(proyectil.posicionX, proyectil.posicionY, 7.0).apply {
        fill = Color.WHITE
        stroke = Color.WHITE
        strokeWidth = 3.0
    }
    private val visualizador = VisualizadorTrayectoria(pane)
    private val mouseTracker = MouseTracker()
    private val campoRapidez = TextField("7.0").apply {
        promptText = "Rapidez (m/s)"
        prefColumnCount = 8
        isDisable = true
        textFormatter = TextFormatter<String> { cambio ->
            if (cambio.controlNewText.matches(Regex("[0-9]*(\\.[0-9]*)?"))) cambio else null
        }
    }
    private val etiquetaCoordenadas: Label?
    private val indicacionEdicion = Label("Presiona E para editar los datos").apply {
        textFill = Color.LIGHTGRAY
    }
    private val indicacionDisparo = Label("Presiona ESPACIO para lanzar").apply {
        textFill = Color.WHITE
    }
    private val estadoDisparo = Label().apply { textFill = Color.LIGHTCORAL }
    private val estadoTrayectoria = Label("Trayectoria baja").apply { textFill = Color.WHITE }
    val raiz: BorderPane

    init {
        pane.children.add(bala)
        bala.toFront()
        raiz = BorderPane().apply {
            top = VBox(
                8.0,
                BotonVolver(onVolverClick),
                HBox(8.0, Label("Rapidez inicial (m/s):").apply { textFill = Color.WHITE }, campoRapidez),
                estadoTrayectoria,
                Label("En modo edición, presiona Q para alternar alta/baja").apply {
                    textFill = Color.LIGHTGRAY
                },
                indicacionDisparo,
                indicacionEdicion,
                estadoDisparo
            )
            center = pane
            style = "-fx-background-color: black;"
            padding = Insets(20.0, 0.0, 0.0, 30.0)
        }
        etiquetaCoordenadas = mouseTracker.attachTo(raiz)
    }

    fun objetivoActual(): Point2D = pane.sceneToLocal(mouseTracker.lastX, mouseTracker.lastY)

    fun rapidezIngresada(): Double? = campoRapidez.text.toDoubleOrNull()

    fun posicionInicialY(): Double = (pane.height - 20.0).coerceAtLeast(20.0)

    fun prepararPartida(origenY: Double) {
        simulacion.reiniciar(origenY)
        estadoDisparo.text = ""
        campoRapidez.isDisable = true
        indicacionEdicion.text = "Presiona E para editar los datos"
        etiquetaCoordenadas?.isVisible = true
        ocultarTrayectoria()
        sincronizarProyectil()
    }

    fun sincronizarProyectil() {
        bala.centerX = proyectil.posicionX
        bala.centerY = proyectil.posicionY
    }

    fun configurarEdicion(activa: Boolean) {
        campoRapidez.isDisable = !activa
        indicacionEdicion.text = if (activa) {
            "Presiona E para volver a apuntar"
        } else {
            "Presiona E para editar los datos"
        }
        etiquetaCoordenadas?.isVisible = !activa
        if (activa) ocultarTrayectoria()
    }

    fun actualizarIndicadorTrayectoria(esAlta: Boolean) {
        estadoTrayectoria.text = if (esAlta) "Trayectoria alta" else "Trayectoria baja"
    }

    fun mostrarErrorDeDisparo() {
        estadoDisparo.text = "Rapidez inválida o el objetivo está fuera del alcance."
    }

    fun limpiarErrorDeDisparo() {
        estadoDisparo.text = ""
    }

    fun mostrarTrayectoria(velocidad: VelocidadInicial, objetivoX: Double, objetivoY: Double) {
        visualizador.mostrarPrediccion(
            origenX = proyectil.posicionX,
            origenY = proyectil.posicionY,
            velocidadX = velocidad.velocidadX * PIXELES_POR_METRO,
            velocidadY = velocidad.velocidadY * PIXELES_POR_METRO,
            gravedad = GRAVEDAD_METROS_POR_SEGUNDO_CUADRADO * PIXELES_POR_METRO,
            tiempoDeVuelo = velocidad.tiempoDeVuelo,
            objetivoX = objetivoX,
            objetivoY = objetivoY
        )
    }

    fun mostrarEjes() = visualizador.mostrarEjes(proyectil.posicionX, proyectil.posicionY)

    fun actualizarTrayectoria(tiempoTranscurrido: Double) {
        visualizador.actualizarProgreso(tiempoTranscurrido)
        visualizador.actualizarEjes(proyectil.posicionX, proyectil.posicionY)
        sincronizarProyectil()
    }

    fun ocultarTrayectoria() = visualizador.ocultar()

    fun estaFueraDeVista(anchoEscena: Double, altoEscena: Double): Boolean =
        proyectil.posicionY > altoEscena || proyectil.posicionX > anchoEscena

    private companion object {
        const val PIXELES_POR_METRO = 100.0
        const val GRAVEDAD_METROS_POR_SEGUNDO_CUADRADO = 9.81
    }
}