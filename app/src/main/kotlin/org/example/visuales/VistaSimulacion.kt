package org.example.visuales

import javafx.geometry.Insets
import javafx.geometry.Point2D
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.control.TextFormatter
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import org.example.fisicas.SimulacionTrayectoriaPort
import org.example.fisicas.VelocidadInicial
import org.example.trackeo.MouseTracker
import org.example.visuales.trayectoria.VisualizadorTrayectoria

interface VistaTrayectoria {
    val raiz: StackPane

    fun objetivoActual(): Point2D
    fun rapidezIngresada(): Double?
    fun prepararPartida(origenY: Double)
    fun sincronizarProyectil()
    fun registrarLanzamiento(velocidad: VelocidadInicial)
    fun configurarEdicion(activa: Boolean)
    fun actualizarIndicadorTrayectoria(esAlta: Boolean)
    fun mostrarErrorDeDisparo()
    fun limpiarErrorDeDisparo()
    fun mostrarTrayectoria(velocidad: VelocidadInicial, objetivoX: Double, objetivoY: Double)
    fun mostrarEjes()
    fun actualizarTrayectoria(tiempoTranscurrido: Double)
    fun ocultarTrayectoria()
    fun estaFueraDeVista(anchoEscena: Double, altoEscena: Double): Boolean
}

class VistaSimulacion(
    // La simulación que se va a visualizar
    private val simulacion: SimulacionTrayectoriaPort,
    onVolverClick: () -> Unit
) : VistaTrayectoria {
    // El panel principal donde se dibujan los elementos de la simulación
    private val pane = Pane()
    private val proyectil = simulacion.proyectil
    private val bala = Circle(proyectil.posicionX, proyectil.posicionY, 8.0).apply {
        fill = Color.WHITE
        stroke = Color.WHITE
        strokeWidth = 3.0
    }
    // El visualizador de la trayectoria proyectada
    private val visualizador = VisualizadorTrayectoria(pane)
    private val mouseTracker = MouseTracker()
    private var origenY = proyectil.posicionY
    private val metricas = PanelMetricasTrayectoria()
    private val campoRapidez = TextField("12.0").apply {
        // Configuración del campo de texto para ingresar la rapidez inicial
        promptText = "Rapidez (m/s)"
        prefColumnCount = 8
        isDisable = true
        textFormatter = TextFormatter<String> { cambio ->
            // Permitir solo números y un punto decimal
            if (cambio.controlNewText.matches(Regex("[0-9]*(\\.[0-9]*)?"))) cambio else null
        }
    }
    private val etiquetaCoordenadas: Label?
    private val panelAyuda = PanelAyudaControles()
    private val estadoDisparo = Label().apply { textFill = Color.LIGHTCORAL }
    private val estadoTrayectoria = Label("Trayectoria baja").apply { textFill = Color.WHITE }
    override val raiz: StackPane

    init {
        // Agrega el proyectil (bala) al panel principal y lo coloca al frente
        pane.children.add(bala)
        bala.toFront()
        val botonInfo = Button("ⓘ").apply {
            style = "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 24px; -fx-cursor: hand;"
            setOnAction { panelAyuda.alternar() }
        }
        val encabezado = BorderPane().apply {
            left = BotonVolver(onVolverClick)
            right = botonInfo
        }
        val contenido = BorderPane().apply {
            // Configuración del panel principal de la vista de simulación, incluyendo botones y etiquetas
            top = VBox(
                8.0,
                encabezado,
                VBox(
                    6.0,
                    HBox(8.0, Label("Rapidez inicial (m/s):").apply { textFill = Color.WHITE }, campoRapidez),
                    estadoTrayectoria
                ),
                metricas.raiz,
                estadoDisparo
            )
            center = pane
            style = "-fx-background-color: black;"
            padding = Insets(20.0, 0.0, 0.0, 30.0)
        }
        raiz = StackPane(contenido, panelAyuda.raiz)
        etiquetaCoordenadas = mouseTracker.attachTo(raiz)
    }

    override fun objetivoActual(): Point2D = pane.sceneToLocal(mouseTracker.lastX, mouseTracker.lastY)
    // Devuelve las coordenadas del objetivo actual basado en la posición del mouse en la escena
    override fun rapidezIngresada(): Double? = campoRapidez.text.toDoubleOrNull()
    // Devuelve la rapidez inicial ingresada por el usuario en el campo de texto, o null si no es un número válido
    fun posicionInicialY(): Double = (pane.height - 20.0).coerceAtLeast(20.0)
    // Devuelve la posición inicial en Y del proyectil, asegurando que esté dentro de los límites del panel

    override fun prepararPartida(origenY: Double) {
        // Prepara la simulación para una nueva partida, reiniciando el proyectil y actualizando las etiquetas y campos de texto
        simulacion.reiniciar(origenY)
        this.origenY = origenY
        metricas.reiniciar()
        estadoDisparo.text = ""
        campoRapidez.isDisable = true
        etiquetaCoordenadas?.isVisible = true
        ocultarTrayectoria()
        sincronizarProyectil()
    }

    fun abrirAyuda() {
        panelAyuda.abrir()
    }

    override fun sincronizarProyectil() {
        // Sincroniza la posición del proyectil (bala) con las coordenadas actuales del proyectil en la simulación
        bala.centerX = proyectil.posicionX
        bala.centerY = proyectil.posicionY
        metricas.actualizar(proyectil.posicionX, proyectil.posicionY, origenY)
    }

    override fun registrarLanzamiento(velocidad: VelocidadInicial) {
        metricas.registrarLanzamiento(velocidad, proyectil.posicionX, proyectil.posicionY, origenY)
    }

    override fun configurarEdicion(activa: Boolean) {
        // Configura el modo de edición de la simulación, habilitando o deshabilitando el campo de texto para ingresar la rapidez inicial y actualizando las indicaciones en pantalla
        campoRapidez.isDisable = !activa
        etiquetaCoordenadas?.isVisible = !activa
        if (activa) ocultarTrayectoria()
    }

    override fun actualizarIndicadorTrayectoria(esAlta: Boolean) {
        // Actualiza la etiqueta que indica si la trayectoria proyectada es alta o baja, según la selección del usuario
        estadoTrayectoria.text = if (esAlta) "Trayectoria alta" else "Trayectoria baja"
    }

    override fun mostrarErrorDeDisparo() {
        // Muestra un mensaje de error en la pantalla indicando que la rapidez ingresada es inválida o que el objetivo está fuera del alcance
        estadoDisparo.text = "Rapidez inválida o el objetivo está fuera del alcance."
    }

    override fun limpiarErrorDeDisparo() {
        // Limpia el mensaje de error de disparo en la pantalla
        estadoDisparo.text = ""
    }

    override fun mostrarTrayectoria(velocidad: VelocidadInicial, objetivoX: Double, objetivoY: Double) {
        // Muestra la trayectoria proyectada del proyectil en la pantalla utilizando el visualizador de trayectoria, basado en la velocidad inicial calculada y las coordenadas del objetivo
        visualizador.mostrarPrediccion(
            // Configuración de la trayectoria proyectada con los parámetros calculados
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

    override fun mostrarEjes() = visualizador.mostrarEjes(proyectil.posicionX, proyectil.posicionY)
    // Muestra los ejes de referencia en la pantalla en la posición actual del proyectil

    override fun actualizarTrayectoria(tiempoTranscurrido: Double) {
        // Actualiza la trayectoria proyectada y los ejes de referencia en la pantalla según el tiempo transcurrido desde el inicio de la simulación
        visualizador.actualizarProgreso(tiempoTranscurrido)
        visualizador.actualizarEjes(proyectil.posicionX, proyectil.posicionY)
        sincronizarProyectil()
    }

    override fun ocultarTrayectoria() = visualizador.ocultar()
    // Oculta la trayectoria proyectada y los ejes de referencia en la pantalla

    override fun estaFueraDeVista(anchoEscena: Double, altoEscena: Double): Boolean =
        // Verifica si el proyectil está fuera de los límites de la escena, ya sea por debajo del suelo o por encima del techo, o si se ha salido por los lados
        proyectil.posicionY > altoEscena || proyectil.posicionX > anchoEscena

    private companion object {
        const val PIXELES_POR_METRO = 100.0
        const val GRAVEDAD_METROS_POR_SEGUNDO_CUADRADO = 9.81
    }
}