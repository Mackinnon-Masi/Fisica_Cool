package org.example.visuales.trayectoria

import javafx.scene.layout.Pane

class VisualizadorTrayectoria(contenedor: Pane) {
    private val recorrido = VisualizadorRecorrido(contenedor)
    private val ejes = VisualizadorEjes(contenedor)
    private val angulo = VisualizadorAngulo(contenedor)

    fun mostrarPrediccion(
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
            ocultar()
            return
        }
        recorrido.mostrar(
            origenX, origenY, velocidadX, velocidadY, gravedad,
            tiempoDeVuelo, objetivoX, objetivoY
        )
        angulo.mostrar(origenX, origenY, velocidadX, velocidadY)
    }

    fun actualizarProgreso(tiempoTranscurrido: Double) = recorrido.actualizar(tiempoTranscurrido)

    fun mostrarEjes(origenX: Double, origenY: Double) = ejes.mostrar(origenX, origenY)

    fun actualizarEjes(posicionX: Double, posicionY: Double) = ejes.actualizar(posicionX, posicionY)

    fun ocultar() {
        recorrido.ocultar()
        ejes.ocultar()
        angulo.ocultar()
    }
}