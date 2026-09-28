package org.example.visuales

import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.stage.Stage
import org.example.fisicas.Proyectil

class Pantalla2 : Application() {
    override fun start(stage: Stage) {

        var flag = false

        // Instanciamos un proyectil (posiciones en píxeles, velocidades en px/s)
        val proyectil = Proyectil(
            posicionX = 100.0,
            posicionY = 620.0,
            velocidadX = 200.0,
            velocidadY = -600.0
        )

        val bala = Circle(proyectil.posicionX, proyectil.posicionY, 7.0).apply {
            fill = Color.WHITE
            stroke = Color.WHITE
            strokeWidth = 3.0
        }

        // --- BOTÓN FLECHA DE REGRESO (Misma estética y lógica de Creditos.kt) ---
        lateinit var escenaInicio: Scene

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
                
                // Reiniciar estado del proyectil para ejecuciones futuras
                proyectil.posicionX = 100.0
                proyectil.posicionY = 620.0
                proyectil.velocidadX = 200.0
                proyectil.velocidadY = -600.0
                proyectil.activo = true

                bala.centerX = proyectil.posicionX
                bala.centerY = proyectil.posicionY

                // Cambiar a la escena de Inicio
                stage.scene = escenaInicio
            }
        }

        // Pane secundario donde se dibuja la física (la bala)
        val paneSimulacion = Pane(bala)

        // Usamos BorderPane para superponer el botón "Volver" en la esquina superior izquierda
        val rootSimulacion = BorderPane().apply {
            top = btnVolver
            center = paneSimulacion
            style = "-fx-background-color: black;"
            padding = Insets(20.0, 0.0, 0.0, 30.0)
        }

        val scene = Scene(rootSimulacion, 1280.0, 720.0, Color.BLACK)

        // Carga de la pantalla de inicio
        val pantallaInicio = Inicio()
        
        // Creación de escena Créditos
        val pantallaCreditos = Creditos()
        lateinit var escenaCreditos: Scene
        escenaCreditos = pantallaCreditos.crearEscena(
            onVolverClick = {
                stage.scene = escenaInicio
            }
        )

        escenaInicio = pantallaInicio.crearEscena(
            onIniciarClick = {
                // Al hacer clic en INICIAR, cambia a la escena de simulación
                stage.scene = scene
                stage.sizeToScene()
                flag = true
            },
            onCreditosClick = {
                stage.scene = escenaCreditos
            }
        )

        var lastTime = 0L
        val timer = object : AnimationTimer() {
            override fun handle(now: Long) {
                if (flag) {
                    if (lastTime == 0L) {
                        lastTime = now
                        return
                    }
                    val dt = (now - lastTime) / 1_000_000_000.0
                    lastTime = now

                    // Usamos gravedad negativa para compensar la convención en Proyectil
                    proyectil.actualizar(dt, gravedad = -200.0)

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
        stage.scene = escenaInicio // Arranca en la pantalla de inicio
        stage.show()
    }
}