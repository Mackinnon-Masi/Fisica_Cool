package org.example.fisicas

/**
 * Contrato de calculo de la velocidad inicial.
 *
 * Es el unico punto de variacion del modelo fisico: permite cambiar o
 * sustituir el algoritmo de calculo sin tocar la simulacion.
 */
abstract class CalculadorTrayectoria {
    abstract fun calcular(solicitud: SolicitudTrayectoria): VelocidadInicial?
}
