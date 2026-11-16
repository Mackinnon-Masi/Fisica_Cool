package org.example.fisicas

/**
 * Fabrica de la simulacion estandar.
 *
 * Responsabilidad unica: construir y configurar una simulacion de tiro
 * parabolico lista para la partida.
 */
class FabricaSimulacion {

    fun crear(): SimulacionTrayectoria = SimulacionTrayectoria(
        calculadora = CalculadoraTrayectoria(),
        pixelesPorMetro = PIXELES_POR_METRO,
        gravedad = GRAVEDAD_METROS_POR_SEGUNDO_CUADRADO,
        proyectil = Proyectil(posicionX = ORIGEN_X, posicionY = POSICION_DISPARO_Y)
    )

    private companion object {
        const val ORIGEN_X = 100.0
        const val POSICION_DISPARO_Y = 620.0
        const val PIXELES_POR_METRO = 100.0
        const val GRAVEDAD_METROS_POR_SEGUNDO_CUADRADO = 9.81
    }
}
