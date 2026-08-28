package org.example.visuales

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.Group
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import javafx.scene.layout.Pane
import javafx.stage.Stage
import javafx.scene.shape.Circle
import javafx.scene.paint.Color

class HolaJavaFX : Application() {
    override fun start(stage: Stage) {
        val label = Label("Estoy toqueteando esto para demostrar que sé un poco al menos lo que estoy haciendo")
        //Este es el texto que se muestra por ahora.

        val circulo1 = Circle(500.0, 150.0, 50.0).apply {
            fill = Color.DODGERBLUE     //color de relleno
            stroke = Color.DARKBLUE     //color de borde
            strokeWidth = 3.0           //grosor de borde
        }
        //Acá hicimos nuestro circulito todo feo. Se expresa de forma circle(centerX, centerY, radius)

        val scene = Scene(Pane(circulo1, label), 1280.0, 720.0)
        //Este es el tamaño de la ventana, que le pusimos el tamaño del HD. Elegimos hacer uno fijo, y no el Full Screen de abajo para que no tengamos que adaptarlo a todas las pantallas.
        //También de paso le metemos el circulo, después del Pane, que descubrimos que es para que se quede en la posición que le asignamos mas arriba.

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
        
        stage.title = "Proyecto Física MRUV re fachero facherito"
        //Esto de arriba es para cambiar el coso que le da el nombre a la ventana.
        
        stage.scene = escenaInicio // Arranca en la pantalla de inicio
        stage.show()
        //Esto muestra todo lo configurado anteriormente y que esté en los paréntesis del scene
    }
}