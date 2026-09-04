package org.example.visuales

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.Group
import javafx.scene.control.Label
import javafx.animation.AnimationTimer
import javafx.scene.layout.StackPane
import javafx.scene.layout.Pane
import javafx.stage.Stage
import javafx.scene.shape.Circle
import javafx.scene.paint.Color
import org.example.Fisicas.Proyectil

class Pantalla2 : Application() {
    override fun start(stage: Stage) {

        //val bala = Circle(500.0, 150.0, 50.0).apply {
        //    fill = Color.DODGERBLUE     //color de relleno
        //    stroke = Color.DARKBLUE     //color de borde
        //    strokeWidth = 3.0           //grosor de borde
        //}
        //Acá hicimos nuestro circulito todo feo. Se expresa de forma circle(centerX, centerY, radius)

        //val scene = Scene(Pane(bala), 1280.0, 720.0)
        //Este es el tamaño de la ventana, que le pusimos el tamaño del HD. Elegimos hacer uno fijo, y no el Full Screen de abajo para que no tengamos que adaptarlo a todas las pantallas.
        //También de paso le metemos el circulo, después del Pane, que descubrimos que es para que se quede en la posición que le asignamos mas arriba.

        // Instanciamos un proyectil (posiciones en píxeles, velocidades en px/s)
        val proyectil = Proyectil(
            posicionX = 100.0,
            posicionY = 620.0,
            velocidadX = 200.0,
            velocidadY = -600.0
        )

        val bala = Circle(proyectil.posicionX, proyectil.posicionY, 7.0).apply {
            fill = Color.BLUE
            stroke = Color.BLACK
            strokeWidth = 3.0
        }

        val pane = Pane(bala)

        val scene = Scene(pane, 1280.0, 720.0, Color.BLACK)

        // Carga de la pantalla de inicio
        val pantallaInicio = Inicio()
        val escenaInicio = pantallaInicio.crearEscena(
            onIniciarClick = {
                // Al hacer clic en INICIAR, cambia a la escena del círculo
                stage.scene = scene
            },
            onCreditosClick = {
                println("Créditos presionados")
            }
        )

        //stage.isFullScreen = true

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

                bala.centerX = proyectil.posicionX
                bala.centerY = proyectil.posicionY

                // Si cae fuera de la escena, detenemos la animación
                if (proyectil.posicionY > scene.height || proyectil.posicionX > scene.width) {
                    proyectil.desactivar()
                    stop()
                }
            }
        }
        timer.start()

        stage.title = "Proyecto Física MRUV re fachero facherito"
        //Esto de arriba es para cambiar el coso que le da el nombre a la ventana.
        
        stage.scene = escenaInicio // Arranca en la pantalla de inicio
        stage.show()
        //Esto muestra todo lo configurado anteriormente y que esté en los paréntesis del scene
    }
}