package org.example

import javafx.application.Application
import org.example.visuales.Pantalla
import org.example.fisicas.Proyectil

fun main() {
    // Inicia la aplicación JavaFX lanzando la clase Pantalla, que es la ventana principal de la simulación
    Application.launch(Pantalla::class.java)
}
