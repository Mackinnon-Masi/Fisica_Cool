package org.example.fisicas

import kotlin.math.sqrt

data class VelocidadInicial(
    val velocidadX: Double,
    val velocidadY: Double,
    val tiempoDeVuelo: Double
)

class CalculadoraTrayectoria {
    fun calcular(
        origenX: Double,
        origenY: Double,
        objetivoX: Double,
        objetivoY: Double,
        rapidez: Double,
        gravedad: Double,
        trayectoriaAlta: Boolean = false
    ): VelocidadInicial? {
        if (
            !origenX.isFinite() || !origenY.isFinite() ||
            !objetivoX.isFinite() || !objetivoY.isFinite() ||
            !rapidez.isFinite() || rapidez <= 0.0 ||
            !gravedad.isFinite() || gravedad <= 0.0
        ) {
            return null
        }

        val dx = objetivoX - origenX
        val dy = objetivoY - origenY
        val rapidezAlCuadrado = rapidez * rapidez
        val terminoLineal = rapidezAlCuadrado + gravedad * dy
        val distanciaAlCuadrado = dx * dx + dy * dy
        val discriminante = terminoLineal * terminoLineal -
            gravedad * gravedad * distanciaAlCuadrado
        val tolerancia = 1e-12 * maxOf(
            1.0,
            terminoLineal * terminoLineal,
            gravedad * gravedad * distanciaAlCuadrado
        )

        if (discriminante < -tolerancia) return null

        val raiz = sqrt(discriminante.coerceAtLeast(0.0))
        val denominador = gravedad * gravedad
        val tiemposAlCuadrado = listOf(
            2.0 * (terminoLineal - raiz) / denominador,
            2.0 * (terminoLineal + raiz) / denominador
        )
        val tiemposValidos = tiemposAlCuadrado
            .filter { it.isFinite() && it > 0.0 }
        val tiempoAlCuadrado = if (trayectoriaAlta) {
            tiemposValidos.maxOrNull()
        } else {
            tiemposValidos.minOrNull()
        } ?: return null
        val tiempo = sqrt(tiempoAlCuadrado)

        return VelocidadInicial(
            velocidadX = dx / tiempo,
            velocidadY = dy / tiempo - 0.5 * gravedad * tiempo,
            tiempoDeVuelo = tiempo
        )
    }
}
