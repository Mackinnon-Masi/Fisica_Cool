package org.example.fisicas

import kotlin.math.sqrt

data class VelocidadInicial(
    // Variables para almacenar las componentes de la velocidad inicial y el tiempo de vuelo
    val velocidadX: Double,
    val velocidadY: Double,
    val tiempoDeVuelo: Double
)

class SolicitudTrayectoria(
    // Variables para almacenar los parámetros de la solicitud de trayectoria
    val origenX: Double,
    val origenY: Double,
    val objetivoX: Double,
    val objetivoY: Double,
    val rapidez: Double,
    val gravedad: Double,
    val trayectoriaAlta: Boolean = false
) {
    fun esValida(): Boolean =
        // Verifica si los parámetros de la solicitud son válidos
        origenX.isFinite() && origenY.isFinite() &&
            // Variables para almacenar los parámetros de la solicitud de trayectoria
            objetivoX.isFinite() && objetivoY.isFinite() &&
            rapidez.isFinite() && rapidez > 0.0 &&
            gravedad.isFinite() && gravedad > 0.0
}

fun interface CalculadorTrayectoria {
    // Interfaz funcional para calcular la velocidad inicial necesaria para alcanzar un objetivo
    fun calcular(solicitud: SolicitudTrayectoria): VelocidadInicial?
}

class CalculadoraTrayectoria : CalculadorTrayectoria {
    // Implementación de la interfaz CalculadorTrayectoria para calcular la velocidad inicial necesaria para alcanzar un objetivo dado una solicitud de trayectoria
    override fun calcular(solicitud: SolicitudTrayectoria): VelocidadInicial? {
        // Verifica si la solicitud es válida antes de realizar los cálculos
        if (!solicitud.esValida()) return null

        // Calcula las diferencias en las coordenadas y otros parámetros necesarios para determinar la velocidad inicial
        val dx = solicitud.objetivoX - solicitud.origenX
        val dy = solicitud.objetivoY - solicitud.origenY
        val rapidezAlCuadrado = solicitud.rapidez * solicitud.rapidez
        val terminoLineal = rapidezAlCuadrado + solicitud.gravedad * dy
        val distanciaAlCuadrado = dx * dx + dy * dy
        val discriminante = discriminante(terminoLineal, distanciaAlCuadrado, solicitud.gravedad)
        if (!esAlcanzable(discriminante, terminoLineal, distanciaAlCuadrado, solicitud.gravedad)) {
            return null
        }

        // Calcula el tiempo de vuelo necesario para alcanzar el objetivo y devuelve la velocidad inicial correspondiente
        val tiempo = calcularTiempoDeVuelo(
            terminoLineal,
            distanciaAlCuadrado,
            solicitud.gravedad,
            solicitud.trayectoriaAlta
        ) ?: return null

        // Calcula y devuelve la velocidad inicial necesaria para alcanzar el objetivo
        return VelocidadInicial(
            velocidadX = dx / tiempo,
            velocidadY = dy / tiempo - 0.5 * solicitud.gravedad * tiempo,
            tiempoDeVuelo = tiempo
        )
    }

    private fun discriminante(terminoLineal: Double, distanciaAlCuadrado: Double, gravedad: Double): Double =
        // Función privada para calcular el discriminante de la ecuación cuadrática que determina si es posible alcanzar el objetivo dado los parámetros de la solicitud
        terminoLineal * terminoLineal - gravedad * gravedad * distanciaAlCuadrado

    private fun esAlcanzable(
        // Función privada para determinar si es posible alcanzar el objetivo dado los parámetros de la solicitud
        discriminante: Double,
        terminoLineal: Double,
        distanciaAlCuadrado: Double,
        gravedad: Double
    ): Boolean {
        // Calcula una tolerancia basada en los parámetros de la solicitud para determinar si el discriminante es suficientemente grande para permitir alcanzar el objetivo
        val tolerancia = 1e-12 * maxOf(
            1.0,
            terminoLineal * terminoLineal,
            gravedad * gravedad * distanciaAlCuadrado
        )
        return discriminante >= -tolerancia
    }

    private fun calcularTiempoDeVuelo(
        // Función privada para calcular el tiempo de vuelo necesario para alcanzar el objetivo dado los parámetros de la solicitud
        terminoLineal: Double,
        distanciaAlCuadrado: Double,
        gravedad: Double,
        trayectoriaAlta: Boolean
    ): Double? {
        // Calcula el discriminante y verifica si es posible alcanzar el objetivo antes de calcular el tiempo de vuelo
        val discriminante = discriminante(terminoLineal, distanciaAlCuadrado, gravedad)
        if (!esAlcanzable(discriminante, terminoLineal, distanciaAlCuadrado, gravedad)) return null

        // Calcula el tiempo de vuelo necesario para alcanzar el objetivo y devuelve el resultado
        val raiz = sqrt(discriminante.coerceAtLeast(0.0))
        val denominador = gravedad * gravedad
        val tiemposValidos = listOf(
            // Calcula los posibles tiempos de vuelo a partir de la ecuación cuadrática y filtra los resultados para obtener solo los tiempos válidos (finito y positivo)
            2.0 * (terminoLineal - raiz) / denominador,
            2.0 * (terminoLineal + raiz) / denominador
        ).filter { it.isFinite() && it > 0.0 }
        val tiempoAlCuadrado = if (trayectoriaAlta) {
            // Si se desea una trayectoria alta, se selecciona el tiempo máximo de vuelo válido
            tiemposValidos.maxOrNull()
        } else {
            // Si se desea una trayectoria baja, se selecciona el tiempo mínimo de vuelo válido
            tiemposValidos.minOrNull()
        } ?: return null
        return sqrt(tiempoAlCuadrado)
    }
}