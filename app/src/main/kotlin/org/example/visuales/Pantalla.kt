package org.example.visuales

import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.control.TextFormatter
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.stage.Stage
import org.example.Trackeo.KeyboardReader
import org.example.Trackeo.MouseTracker
import org.example.fisicas.CalculadoraTrayectoria
import org.example.fisicas.Proyectil
import org.example.fisicas.VelocidadInicial
import org.example.visuales.trayectoria.VisualizadorTrayectoria

private const val PIXELES_POR_METRO = 100.0
private const val GRAVEDAD_METROS_POR_SEGUNDO_CUADRADO = 9.81

class Pantalla : Application() {
    override fun start(stage: Stage) {

        var flag = false
        var esperandoDisparo = false
        var tiempoDeVueloObjetivo = 0.0
        var tiempoTranscurrido = 0.0
        var objetivoX = 0.0
        var objetivoY = 0.0
        var espacioYaProcesado = false
        var eYaProcesada = false
        var qYaProcesada = false
        var modoEdicion = false
        var trayectoriaAltaSeleccionada = false
        var recorridoTerminado = false
        var posicionDisparoY = 620.0

        // Instanciamos un proyectil (posiciones en píxeles, velocidades en px/s)
        val proyectil = Proyectil(
            posicionX = 100.0,
            posicionY = 620.0,
            velocidadX = 0.0,
            velocidadY = 0.0
        )
        val calculadoraTrayectoria = CalculadoraTrayectoria()

        val bala = Circle(proyectil.posicionX, proyectil.posicionY, 7.0).apply {
            fill = Color.WHITE
            stroke = Color.WHITE
            strokeWidth = 3.0
        }
        val paneSimulacion = Pane(bala)
        val visualizadorTrayectoria = VisualizadorTrayectoria(paneSimulacion)
        bala.toFront()

        // --- BOTÓN FLECHA DE REGRESO (Misma estética y lógica de Creditos.kt) ---
        lateinit var rootInicio: Pane
        lateinit var rootCreditos: Pane
        lateinit var scene: Scene

        fun mostrarVista(root: Pane) {
            scene.root = root
            root.applyCss()
            root.layout()
        }

        val btnVolver = Button("🡰").apply {
            style = """
                -fx-background-color: transparent;
                -fx-text-fill: white;
                -fx-font-size: 40px;
                -fx-font-weight: bold;
                -fx-cursor: hand;
            """.trimIndent()

            // Efectos de Hover
            setOnMouseEntered { 
                style = "-fx-background-color: transparent; -fx-text-fill: #A0A0A0; -fx-font-size: 40px; -fx-font-weight: bold; -fx-cursor: hand;" 
            }
            setOnMouseExited { 
                style = "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 40px; -fx-font-weight: bold; -fx-cursor: hand;" 
            }

            setOnAction {
                // Pausar simulación
                flag = false
                esperandoDisparo = false
                
                // Reiniciar estado del proyectil para ejecuciones futuras
                proyectil.posicionX = 100.0
                proyectil.posicionY = posicionDisparoY
                proyectil.velocidadX = 0.0
                proyectil.velocidadY = 0.0
                proyectil.activo = true

                bala.centerX = proyectil.posicionX
                bala.centerY = proyectil.posicionY
                visualizadorTrayectoria.ocultar()

                // Cambiar a la escena de Inicio
                flag = false
                mostrarVista(rootInicio)
            }
        }

        // Pane secundario donde se dibuja la física (la bala)
        val indicacionDisparo = Label("Presiona ESPACIO para lanzar").apply {
            textFill = Color.WHITE
        }
        val indicacionEdicion = Label("Presiona E para editar los datos").apply {
            textFill = Color.LIGHTGRAY
        }
        val campoRapidez = TextField("7.0").apply {
            promptText = "Rapidez (m/s)"
            prefColumnCount = 8
            isDisable = true
            textFormatter = TextFormatter<String> { change ->
                if (change.controlNewText.matches(Regex("[0-9]*(\\.[0-9]*)?"))) {
                    change
                } else {
                    null
                }
            }
        }
        val etiquetaRapidez = Label("Rapidez inicial (m/s):").apply {
            textFill = Color.WHITE
        }
        val estadoTrayectoria = Label("Trayectoria baja").apply {
            textFill = Color.WHITE
        }
        val indicacionTrayectoria = Label("En modo edición, presiona Q para alternar alta/baja").apply {
            textFill = Color.LIGHTGRAY
        }
        fun calcularVelocidad(objetivoXpx: Double, objetivoYpx: Double): VelocidadInicial? {
            val rapidez = campoRapidez.text.toDoubleOrNull() ?: return null
            return calculadoraTrayectoria.calcular(
                origenX = 100.0 / PIXELES_POR_METRO,
                origenY = posicionDisparoY / PIXELES_POR_METRO,
                objetivoX = objetivoXpx / PIXELES_POR_METRO,
                objetivoY = objetivoYpx / PIXELES_POR_METRO,
                rapidez = rapidez,
                gravedad = GRAVEDAD_METROS_POR_SEGUNDO_CUADRADO,
                trayectoriaAlta = trayectoriaAltaSeleccionada
            )
        }
        fun mostrarTrayectoria(velocidad: VelocidadInicial, objetivoXpx: Double, objetivoYpx: Double) {
            visualizadorTrayectoria.mostrarPrediccion(
                origenX = 100.0,
                origenY = posicionDisparoY,
                velocidadX = velocidad.velocidadX * PIXELES_POR_METRO,
                velocidadY = velocidad.velocidadY * PIXELES_POR_METRO,
                gravedad = GRAVEDAD_METROS_POR_SEGUNDO_CUADRADO * PIXELES_POR_METRO,
                tiempoDeVuelo = velocidad.tiempoDeVuelo,
                objetivoX = objetivoXpx,
                objetivoY = objetivoYpx
            )
        }
        val estadoDisparo = Label().apply {
            textFill = Color.LIGHTCORAL
        }
        val controlesSimulacion = VBox(
            8.0,
            btnVolver,
            HBox(8.0, etiquetaRapidez, campoRapidez),
            estadoTrayectoria,
            indicacionTrayectoria,
            indicacionDisparo,
            indicacionEdicion,
            estadoDisparo
        )
        // Usamos BorderPane para superponer el botón "Volver" en la esquina superior izquierda
        val rootSimulacion = BorderPane().apply {
            top = controlesSimulacion
            center = paneSimulacion
            style = "-fx-background-color: black;"
            padding = Insets(20.0, 0.0, 0.0, 30.0)
        }
        val etiquetaCoordenadas = MouseTracker.attachTo(rootSimulacion)

        scene = Scene(rootSimulacion, 1280.0, 720.0, Color.BLACK)
        val keyboardReader = KeyboardReader().apply {
            attachTo(scene)
        }
        val bloquearActivacionBotones = javafx.event.EventHandler<KeyEvent> { event ->
            if (
                scene.root === rootSimulacion &&
                (event.code == KeyCode.SPACE || event.code == KeyCode.ENTER)
            ) {
                event.consume()
            }
        }
        scene.addEventFilter(KeyEvent.KEY_PRESSED, bloquearActivacionBotones)
        scene.addEventFilter(KeyEvent.KEY_RELEASED, bloquearActivacionBotones)

        // Carga de la pantalla de inicio
        val pantallaInicio = Inicio()
        val pantallaCreditos = Creditos()
        rootCreditos = pantallaCreditos.crearVista(
            onVolverClick = {
                mostrarVista(rootInicio)
            }
        )

        rootInicio = pantallaInicio.crearVista(
            onIniciarClick = {
                estadoDisparo.text = ""
                mostrarVista(rootSimulacion)
                posicionDisparoY = (paneSimulacion.height - 20.0).coerceAtLeast(20.0)
                proyectil.posicionX = 100.0
                proyectil.posicionY = posicionDisparoY
                recorridoTerminado = false
                modoEdicion = false
                campoRapidez.isDisable = true
                indicacionEdicion.text = "Presiona E para editar los datos"
                etiquetaCoordenadas?.isVisible = true
                bala.centerX = proyectil.posicionX
                bala.centerY = proyectil.posicionY
                visualizadorTrayectoria.ocultar()
                esperandoDisparo = true
            },
            onCreditosClick = {
                mostrarVista(rootCreditos)
            }
        )

        var lastTime = 0L
        val timer = object : AnimationTimer() {
            override fun handle(now: Long) {
                val espacioPresionado = keyboardReader.isKeyPressed(KeyCode.SPACE)
                if (!espacioPresionado) {
                    espacioYaProcesado = false
                }

                val ePresionada = keyboardReader.isKeyPressed(KeyCode.E)
                if (!ePresionada) {
                    eYaProcesada = false
                }
                val qPresionada = keyboardReader.isKeyPressed(KeyCode.Q)
                if (!qPresionada) {
                    qYaProcesada = false
                }
                if (
                    scene.root === rootSimulacion && esperandoDisparo && modoEdicion &&
                    qPresionada && !qYaProcesada
                ) {
                    qYaProcesada = true
                    trayectoriaAltaSeleccionada = !trayectoriaAltaSeleccionada
                    estadoTrayectoria.text = if (trayectoriaAltaSeleccionada) {
                        "Trayectoria alta"
                    } else {
                        "Trayectoria baja"
                    }
                }
                if (
                    scene.root === rootSimulacion && (esperandoDisparo || flag) &&
                    ePresionada && !eYaProcesada
                ) {
                    eYaProcesada = true
                    if (flag) {
                        flag = false
                        esperandoDisparo = true
                        tiempoTranscurrido = 0.0
                        proyectil.posicionX = 100.0
                        proyectil.posicionY = posicionDisparoY
                        proyectil.velocidadX = 0.0
                        proyectil.velocidadY = 0.0
                        proyectil.activo = true
                        bala.centerX = proyectil.posicionX
                        bala.centerY = proyectil.posicionY
                        estadoDisparo.text = ""
                        modoEdicion = true
                    } else {
                        modoEdicion = !modoEdicion
                    }
                    campoRapidez.isDisable = !modoEdicion
                    indicacionEdicion.text = if (modoEdicion) {
                        "Presiona E para volver a apuntar"
                    } else {
                        "Presiona E para editar los datos"
                    }
                    etiquetaCoordenadas?.isVisible = !modoEdicion
                    if (modoEdicion) {
                        recorridoTerminado = false
                        visualizadorTrayectoria.ocultar()
                    }
                }

                if (esperandoDisparo && !modoEdicion && !recorridoTerminado) {
                    val puntoObjetivo = paneSimulacion.sceneToLocal(
                        MouseTracker.lastX,
                        MouseTracker.lastY
                    )
                    val velocidad = calcularVelocidad(puntoObjetivo.x, puntoObjetivo.y)
                    if (velocidad == null) {
                        visualizadorTrayectoria.ocultar()
                    } else {
                        mostrarTrayectoria(velocidad, puntoObjetivo.x, puntoObjetivo.y)
                    }
                }

                if (
                    esperandoDisparo && !modoEdicion && espacioPresionado &&
                    !espacioYaProcesado
                ) {
                    espacioYaProcesado = true
                    val puntoObjetivo = paneSimulacion.sceneToLocal(
                        MouseTracker.lastX,
                        MouseTracker.lastY
                    )
                    val velocidad = calcularVelocidad(puntoObjetivo.x, puntoObjetivo.y)

                    if (velocidad == null) {
                        estadoDisparo.text =
                            "Rapidez inválida o el objetivo está fuera del alcance."
                    } else {
                        proyectil.posicionX = 100.0
                        proyectil.posicionY = posicionDisparoY
                        proyectil.velocidadX = velocidad.velocidadX * PIXELES_POR_METRO
                        proyectil.velocidadY = velocidad.velocidadY * PIXELES_POR_METRO
                        proyectil.activo = true
                        mostrarTrayectoria(velocidad, puntoObjetivo.x, puntoObjetivo.y)
                        visualizadorTrayectoria.mostrarEjes(
                            origenX = proyectil.posicionX,
                            origenY = proyectil.posicionY
                        )
                        bala.centerX = proyectil.posicionX
                        bala.centerY = proyectil.posicionY
                        objetivoX = puntoObjetivo.x
                        objetivoY = puntoObjetivo.y
                        tiempoDeVueloObjetivo = velocidad.tiempoDeVuelo
                        tiempoTranscurrido = 0.0
                        recorridoTerminado = false
                        estadoDisparo.text = ""
                        esperandoDisparo = false
                        flag = true
                        lastTime = now
                    }
                }

                if (flag) {
                    if (lastTime == 0L) {
                        lastTime = now
                        return
                    }
                    val dt = (now - lastTime) / 1_000_000_000.0
                    lastTime = now
                    tiempoTranscurrido += dt

                    // Usamos gravedad negativa para compensar la convención en Proyectil
                    proyectil.actualizar(
                        dt,
                        gravedad = -GRAVEDAD_METROS_POR_SEGUNDO_CUADRADO * PIXELES_POR_METRO
                    )

                    if (tiempoTranscurrido >= tiempoDeVueloObjetivo) {
                        proyectil.posicionX = objetivoX
                        proyectil.posicionY = objetivoY
                        proyectil.desactivar()
                        flag = false
                        esperandoDisparo = true
                        recorridoTerminado = true
                    }

                    visualizadorTrayectoria.actualizarProgreso(tiempoTranscurrido)
                    visualizadorTrayectoria.actualizarEjes(
                        proyectil.posicionX,
                        proyectil.posicionY
                    )
                    bala.centerX = proyectil.posicionX
                    bala.centerY = proyectil.posicionY

                    // Si cae fuera de la escena, detenemos la animación
                    if (proyectil.posicionY > scene.height || proyectil.posicionX > scene.width) {
                        proyectil.desactivar()
                        flag = false
                        visualizadorTrayectoria.ocultar()
                    }
                } else {
                    lastTime = now
                }
            }
        }
        timer.start()

        stage.title = "Proyecto Física MRUV re fachero facherito"
        mostrarVista(rootInicio)
        stage.scene = scene
        stage.show()
    }
}