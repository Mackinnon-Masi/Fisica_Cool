package org.example.visuales

import javafx.geometry.Insets
import javafx.geometry.Point2D
import javafx.geometry.Pos
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
import org.example.fisicas.SimulacionTrayectoria
import org.example.fisicas.VelocidadInicial
import org.example.trackeo.MouseTracker
import org.example.visuales.trayectoria.VisualizadorTrayectoria
import kotlin.math.atan2

class VistaSimulacion(
    // La simulación que se va a visualizar
    private val simulacion: SimulacionTrayectoria,
    onVolverClick: () -> Unit
) {
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
    private var alturaMaxima: Double? = null
    private var anguloLanzamiento: Double? = null
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
    // Etiqueta para mostrar las coordenadas del mouse en la simulación
    private val valorX = Label("-- m")
    private val valorY = Label("-- m")
    private val valorYMaxima = Label("-- m")
    private val valorAngulo = Label("--°")
    private val capaAyuda: StackPane
    private val metricas = HBox(
        22.0,
        crearMetrica("X", valorX, COLOR_X),
        crearMetrica("Y", valorY, COLOR_Y),
        crearMetrica("Y máxima", valorYMaxima, COLOR_Y),
        crearMetrica("Ω", valorAngulo, COLOR_ANGULO)
    ).apply {
        alignment = Pos.CENTER_LEFT
        padding = Insets(8.0, 0.0, 8.0, 0.0)
    }
    private val estadoDisparo = Label().apply { textFill = Color.LIGHTCORAL }
    private val estadoTrayectoria = Label("Trayectoria baja").apply { textFill = Color.WHITE }
    val raiz: StackPane

    init {
        // Agrega el proyectil (bala) al panel principal y lo coloca al frente
        pane.children.add(bala)
        bala.toFront()
        val botonCerrarAyuda = Button("×").apply {
            minWidth = 24.0
            prefWidth = 24.0
            maxWidth = 24.0
            minHeight = 24.0
            prefHeight = 24.0
            maxHeight = 24.0
            style = "-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0; -fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold; -fx-cursor: hand;"
            setOnAction { cerrarAyuda() }
        }
        val panelAyuda = VBox(
            2.0,
            BorderPane().apply {
                left = Label("Controles").apply {
                    textFill = Color.WHITE
                    style = "-fx-font-size: 14px; -fx-font-weight: bold;"
                }
                right = botonCerrarAyuda
            },
            Label("E: editar/salir  |  Q: cambiar trayectoria  |  Espacio: lanzar").apply {
                textFill = Color.WHITE
                font = javafx.scene.text.Font.font(12.0)
                isWrapText = true
                maxWidth = ANCHO_PANEL_AYUDA
            }
        ).apply {
            prefWidth = ANCHO_PANEL_AYUDA + 24.0
            maxWidth = ANCHO_PANEL_AYUDA + 24.0
            minHeight = ALTO_PANEL_AYUDA
            prefHeight = ALTO_PANEL_AYUDA
            maxHeight = ALTO_PANEL_AYUDA
            style = "-fx-background-color: black; -fx-padding: 6px; -fx-border-color: white; -fx-border-width: 1px;"
        }
        capaAyuda = StackPane(panelAyuda).apply {
            alignment = Pos.CENTER
            isVisible = false
            isManaged = false
            isPickOnBounds = false
        }
        val botonInfo = Button("ⓘ").apply {
            style = "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 24px; -fx-cursor: hand;"
            setOnAction { alternarAyuda() }
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
                metricas,
                estadoDisparo
            )
            center = pane
            style = "-fx-background-color: black;"
            padding = Insets(20.0, 0.0, 0.0, 30.0)
        }
        raiz = StackPane(contenido, capaAyuda)
        etiquetaCoordenadas = mouseTracker.attachTo(raiz)
    }

    fun objetivoActual(): Point2D = pane.sceneToLocal(mouseTracker.lastX, mouseTracker.lastY)
    // Devuelve las coordenadas del objetivo actual basado en la posición del mouse en la escena
    fun rapidezIngresada(): Double? = campoRapidez.text.toDoubleOrNull()
    // Devuelve la rapidez inicial ingresada por el usuario en el campo de texto, o null si no es un número válido
    fun posicionInicialY(): Double = (pane.height - 20.0).coerceAtLeast(20.0)
    // Devuelve la posición inicial en Y del proyectil, asegurando que esté dentro de los límites del panel

    fun prepararPartida(origenY: Double) {
        // Prepara la simulación para una nueva partida, reiniciando el proyectil y actualizando las etiquetas y campos de texto
        simulacion.reiniciar(origenY)
        this.origenY = origenY
        alturaMaxima = null
        anguloLanzamiento = null
        estadoDisparo.text = ""
        campoRapidez.isDisable = true
        etiquetaCoordenadas?.isVisible = true
        ocultarTrayectoria()
        sincronizarProyectil()
    }

    fun abrirAyuda() {
        capaAyuda.isManaged = true
        capaAyuda.isVisible = true
    }

    private fun alternarAyuda() {
        if (capaAyuda.isVisible) cerrarAyuda() else abrirAyuda()
    }

    private fun cerrarAyuda() {
        capaAyuda.isVisible = false
        capaAyuda.isManaged = false
    }

    fun sincronizarProyectil() {
        // Sincroniza la posición del proyectil (bala) con las coordenadas actuales del proyectil en la simulación
        bala.centerX = proyectil.posicionX
        bala.centerY = proyectil.posicionY
        actualizarMetricas()
    }

    fun registrarLanzamiento(velocidad: VelocidadInicial) {
        anguloLanzamiento = Math.toDegrees(atan2(-velocidad.velocidadY, velocidad.velocidadX))
        alturaMaxima = if (velocidad.velocidadY < 0.0) {
            velocidad.velocidadY * velocidad.velocidadY /
                (2.0 * GRAVEDAD_METROS_POR_SEGUNDO_CUADRADO)
        } else {
            0.0
        }
        actualizarMetricas()
    }

    fun configurarEdicion(activa: Boolean) {
        // Configura el modo de edición de la simulación, habilitando o deshabilitando el campo de texto para ingresar la rapidez inicial y actualizando las indicaciones en pantalla
        campoRapidez.isDisable = !activa
        etiquetaCoordenadas?.isVisible = !activa
        if (activa) ocultarTrayectoria()
    }

    fun actualizarIndicadorTrayectoria(esAlta: Boolean) {
        // Actualiza la etiqueta que indica si la trayectoria proyectada es alta o baja, según la selección del usuario
        estadoTrayectoria.text = if (esAlta) "Trayectoria alta" else "Trayectoria baja"
    }

    fun mostrarErrorDeDisparo() {
        // Muestra un mensaje de error en la pantalla indicando que la rapidez ingresada es inválida o que el objetivo está fuera del alcance
        estadoDisparo.text = "Rapidez inválida o el objetivo está fuera del alcance."
    }

    fun limpiarErrorDeDisparo() {
        // Limpia el mensaje de error de disparo en la pantalla
        estadoDisparo.text = ""
    }

    fun mostrarTrayectoria(velocidad: VelocidadInicial, objetivoX: Double, objetivoY: Double) {
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

    fun mostrarEjes() = visualizador.mostrarEjes(proyectil.posicionX, proyectil.posicionY)
    // Muestra los ejes de referencia en la pantalla en la posición actual del proyectil

    fun actualizarTrayectoria(tiempoTranscurrido: Double) {
        // Actualiza la trayectoria proyectada y los ejes de referencia en la pantalla según el tiempo transcurrido desde el inicio de la simulación
        visualizador.actualizarProgreso(tiempoTranscurrido)
        visualizador.actualizarEjes(proyectil.posicionX, proyectil.posicionY)
        sincronizarProyectil()
    }

    fun ocultarTrayectoria() = visualizador.ocultar()
    // Oculta la trayectoria proyectada y los ejes de referencia en la pantalla

    fun estaFueraDeVista(anchoEscena: Double, altoEscena: Double): Boolean =
        // Verifica si el proyectil está fuera de los límites de la escena, ya sea por debajo del suelo o por encima del techo, o si se ha salido por los lados
        proyectil.posicionY > altoEscena || proyectil.posicionX > anchoEscena

    private fun actualizarMetricas() {
        valorX.text = "%.2f m".format(proyectil.posicionX / PIXELES_POR_METRO)
        valorY.text = "%.2f m".format((origenY - proyectil.posicionY) / PIXELES_POR_METRO)
        valorYMaxima.text = alturaMaxima?.let { "%.2f m".format(it) } ?: "-- m"
        valorAngulo.text = anguloLanzamiento?.let { "%.1f°".format(it) } ?: "--°"
    }

    private fun crearMetrica(titulo: String, valor: Label, color: Color): VBox = VBox(
        3.0,
        Label(titulo).apply { textFill = color },
        valor.apply { textFill = Color.WHITE }
    )

    private companion object {
        // Constantes utilizadas para la conversión de unidades y la simulación de la gravedad
        const val PIXELES_POR_METRO = 100.0
        const val GRAVEDAD_METROS_POR_SEGUNDO_CUADRADO = 9.81
        const val ANCHO_PANEL_AYUDA = 280.0
        const val ALTO_PANEL_AYUDA = 78.0
        val COLOR_X = Color.web("#F25022")
        val COLOR_Y = Color.web("#00A4EF")
        val COLOR_ANGULO = Color.web("#FFB900")
    }
}