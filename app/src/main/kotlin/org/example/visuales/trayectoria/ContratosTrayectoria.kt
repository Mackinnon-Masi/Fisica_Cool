package org.example.visuales.trayectoria

/** Contratos de los visualizadores de trayectoria. */

interface IVisualizadorTrayectoria {
    fun mostrarPrediccion(
        origenX: Double,
        origenY: Double,
        velocidadX: Double,
        velocidadY: Double,
        gravedad: Double,
        tiempoDeVuelo: Double,
        objetivoX: Double,
        objetivoY: Double
    )

    fun actualizarProgreso(tiempoTranscurrido: Double)
    fun mostrarEjes(origenX: Double, origenY: Double)
    fun actualizarEjes(posicionX: Double, posicionY: Double)
    fun ocultar()
}

interface IVisualizadorRecorrido {
    fun mostrar(
        origenX: Double,
        origenY: Double,
        velocidadX: Double,
        velocidadY: Double,
        gravedad: Double,
        tiempoTotal: Double,
        objetivoX: Double,
        objetivoY: Double
    )

    fun actualizar(tiempo: Double)
    fun ocultar()
}

interface IVisualizadorEjes {
    fun mostrar(origenX: Double, origenY: Double)
    fun actualizar(posicionX: Double, posicionY: Double)
    fun ocultar()
}

interface IVisualizadorAngulo {
    fun mostrar(
        origenX: Double,
        origenY: Double,
        velocidadX: Double,
        velocidadY: Double
    )

    fun ocultar()
}
