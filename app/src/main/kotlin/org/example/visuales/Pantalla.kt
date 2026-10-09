package org.example.visuales

import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
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

class Pantalla : Application() {
    override fun start(stage: Stage) {

        var flag = false
        var esperandoDisparo = false
        var tiempoDeVueloObjetivo = 0.0
        var tiempoTranscurrido = 0.0
        var objetivoX = 0.0
        var objetivoY = 0.0
        var espacioYaProcesado = false
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

                // Cambiar a la escena de Inicio
                flag = false
                mostrarVista(rootInicio)
            }
        }

        // Pane secundario donde se dibuja la física (la bala)
        val indicacionDisparo = Label("Presiona ESPACIO para lanzar").apply {
            textFill = Color.WHITE
        }
        val campoRapidez = TextField("700").apply {
            promptText = "Rapidez (px/s)"
            prefColumnCount = 8
            textFormatter = TextFormatter<String> { change ->
                if (change.controlNewText.matches(Regex("[0-9]*(\\.[0-9]*)?"))) {
                    change
                } else {
                    null
                }
            }
        }
        val etiquetaRapidez = Label("Rapidez inicial (px/s):").apply {
            textFill = Color.WHITE
        }
        val seleccionarTrayectoriaAlta = CheckBox("Trayectoria alta").apply {
            textFill = Color.WHITE
        }
        val estadoDisparo = Label().apply {
            textFill = Color.LIGHTCORAL
        }
        val controlesSimulacion = VBox(
            8.0,
            btnVolver,
            HBox(8.0, etiquetaRapidez, campoRapidez),
            seleccionarTrayectoriaAlta,
            indicacionDisparo,
            estadoDisparo
        )
        val paneSimulacion = Pane(bala)

        // Usamos BorderPane para superponer el botón "Volver" en la esquina superior izquierda
        val rootSimulacion = BorderPane().apply {
            top = controlesSimulacion
            center = paneSimulacion
            style = "-fx-background-color: black;"
            padding = Insets(20.0, 0.0, 0.0, 30.0)
        }
        MouseTracker.attachTo(rootSimulacion)

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
                bala.centerX = proyectil.posicionX
                bala.centerY = proyectil.posicionY
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

                if (esperandoDisparo && espacioPresionado && !espacioYaProcesado) {
                    espacioYaProcesado = true
                    val rapidez = campoRapidez.text.toDoubleOrNull()
                    val puntoObjetivo = paneSimulacion.sceneToLocal(
                        MouseTracker.lastX,
                        MouseTracker.lastY
                    )
                    val velocidad = rapidez?.let {
                        calculadoraTrayectoria.calcular(
                            origenX = 100.0,
                            origenY = posicionDisparoY,
                            objetivoX = puntoObjetivo.x,
                            objetivoY = puntoObjetivo.y,
                            rapidez = it,
                            gravedad = 200.0,
                            trayectoriaAlta = seleccionarTrayectoriaAlta.isSelected
                        )
                    }

                    if (velocidad == null) {
                        estadoDisparo.text =
                            "Rapidez inválida o el objetivo está fuera del alcance."
                    } else {
                        proyectil.posicionX = 100.0
                        proyectil.posicionY = posicionDisparoY
                        proyectil.velocidadX = velocidad.velocidadX
                        proyectil.velocidadY = velocidad.velocidadY
                        proyectil.activo = true
                        bala.centerX = proyectil.posicionX
                        bala.centerY = proyectil.posicionY
                        objetivoX = puntoObjetivo.x
                        objetivoY = puntoObjetivo.y
                        tiempoDeVueloObjetivo = velocidad.tiempoDeVuelo
                        tiempoTranscurrido = 0.0
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
                    proyectil.actualizar(dt, gravedad = -200.0)

                    if (tiempoTranscurrido >= tiempoDeVueloObjetivo) {
                        proyectil.posicionX = objetivoX
                        proyectil.posicionY = objetivoY
                        proyectil.desactivar()
                        flag = false
                        esperandoDisparo = true
                    }

                    bala.centerX = proyectil.posicionX
                    bala.centerY = proyectil.posicionY

                    // Si cae fuera de la escena, detenemos la animación
                    if (proyectil.posicionY > scene.height || proyectil.posicionX > scene.width) {
                        proyectil.desactivar()
                        flag = false
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