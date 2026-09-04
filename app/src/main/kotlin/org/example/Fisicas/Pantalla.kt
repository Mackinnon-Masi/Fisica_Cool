package org.example.Fisicas

import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.Pane
import javafx.stage.Stage
import javafx.scene.shape.Circle
import javafx.scene.paint.Color
import org.example.Fisicas.Proyectil



class Pantalla : Application() {
    override fun start(stage: Stage) {
        

        // Instanciamos un proyectil (posiciones en píxeles, velocidades en px/s)
        val proyectil = Proyectil(
            posicionX = 100.0,
            posicionY = 620.0,
            velocidadX = 200.0,
            velocidadY = -600.0
        )

        val circulo1 = Circle(proyectil.posicionX, proyectil.posicionY, 5.0).apply {
            fill = Color.WHITE
            stroke = Color.WHITE
            strokeWidth = 3.0
        }

        val pane = Pane(circulo1)

        val scene = Scene(pane, 1280.0, 720.0, Color.BLACK)

        var lastTime = 0L
        val timer = object : AnimationTimer() {
            override fun handle(now: Long) {
                if (lastTime == 0L) {
                    lastTime = now
                    return
                }
                val dt = (now - lastTime) / 1_000_000_000.0
                lastTime = now

                // Usamos una gravedad negativa para compensar la convención en Proyectil
                proyectil.actualizar(dt, gravedad = -200.0)

                circulo1.centerX = proyectil.posicionX
                circulo1.centerY = proyectil.posicionY

                // Si cae fuera de la escena, detenemos la animación
                if (proyectil.posicionY > scene.height || proyectil.posicionX > scene.width) {
                    proyectil.desactivar()
                    stop()
                }
            }
        }
        timer.start()

        stage.title = "Proyecto Física MRUV"
        stage.scene = scene
        stage.show()
    }

}