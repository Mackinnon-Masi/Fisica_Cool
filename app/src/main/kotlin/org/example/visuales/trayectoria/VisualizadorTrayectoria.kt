package org.example.visuales.trayectoria

import javafx.scene.layout.Pane

class VisualizadorTrayectoria(contenedor: Pane) {
    // Composición de los visualizadores de recorrido, ejes y ángulo
    private val recorrido = VisualizadorRecorrido(contenedor)
    private val ejes = VisualizadorEjes(contenedor)
    private val angulo = VisualizadorAngulo(contenedor)

    fun mostrarPrediccion(
        // Calcular y mostrar la predicción de la trayectoria, ejes y ángulo
        origenX: Double,
        origenY: Double,
        velocidadX: Double,
        velocidadY: Double,
        gravedad: Double,
        tiempoDeVuelo: Double,
        objetivoX: Double,
        objetivoY: Double
    ) {
        if (!tiempoDeVuelo.isFinite() || tiempoDeVuelo <= 0.0) {
            // Si el tiempo de vuelo no es válido, ocultar la predicción
            ocultar()
            return
        }
        recorrido.mostrar(
            // Calcular y mostrar la trayectoria completa y el punto final
            origenX, origenY, velocidadX, velocidadY, gravedad,
            tiempoDeVuelo, objetivoX, objetivoY
        )
        angulo.mostrar(origenX, origenY, velocidadX, velocidadY)
    }

    fun actualizarProgreso(tiempoTranscurrido: Double) = recorrido.actualizar(tiempoTranscurrido)
    // Actualizar la visualización del progreso de la trayectoria según el tiempo transcurrido
    fun mostrarEjes(origenX: Double, origenY: Double) = ejes.mostrar(origenX, origenY)
    // Mostrar los ejes en la posición del origen
    fun actualizarEjes(posicionX: Double, posicionY: Double) = ejes.actualizar(posicionX, posicionY)
    // Actualizar la posición de los ejes según la posición actual

    fun ocultar() {
        // Ocultar la predicción de la trayectoria, ejes y ángulo
        recorrido.ocultar()
        ejes.ocultar()
        angulo.ocultar()
    }
}