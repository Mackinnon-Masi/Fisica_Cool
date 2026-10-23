package org.example.fisicas

import kotlin.math.sqrt

data class VelocidadInicial(
    val velocidadX: Double,
    val velocidadY: Double,
    val tiempoDeVuelo: Double
)

class SolicitudTrayectoria(
    val origenX: Double,
    val origenY: Double,
    val objetivoX: Double,
    val objetivoY: Double,
    val rapidez: Double,
    val gravedad: Double,
    val trayectoriaAlta: Boolean = false
) {
    fun esValida(): Boolean =
        origenX.isFinite() && origenY.isFinite() &&
            objetivoX.isFinite() && objetivoY.isFinite() &&
            rapidez.isFinite() && rapidez > 0.0 &&
            gravedad.isFinite() && gravedad > 0.0
}

fun interface CalculadorTrayectoria {
    fun calcular(solicitud: SolicitudTrayectoria): VelocidadInicial?
}

class CalculadoraTrayectoria : CalculadorTrayectoria {
    override fun calcular(solicitud: SolicitudTrayectoria): VelocidadInicial? {
        if (!solicitud.esValida()) return null

        val dx = solicitud.objetivoX - solicitud.origenX
        val dy = solicitud.objetivoY - solicitud.origenY
        val rapidezAlCuadrado = solicitud.rapidez * solicitud.rapidez
        val terminoLineal = rapidezAlCuadrado + solicitud.gravedad * dy
        val distanciaAlCuadrado = dx * dx + dy * dy
        val discriminante = discriminante(terminoLineal, distanciaAlCuadrado, solicitud.gravedad)
        if (!esAlcanzable(discriminante, terminoLineal, distanciaAlCuadrado, solicitud.gravedad)) {
            return null
        }

        val tiempo = calcularTiempoDeVuelo(
            terminoLineal,
            distanciaAlCuadrado,
            solicitud.gravedad,
            solicitud.trayectoriaAlta
        ) ?: return null

        return VelocidadInicial(
            velocidadX = dx / tiempo,
            velocidadY = dy / tiempo - 0.5 * solicitud.gravedad * tiempo,
            tiempoDeVuelo = tiempo
        )
    }

    private fun discriminante(terminoLineal: Double, distanciaAlCuadrado: Double, gravedad: Double): Double =
        terminoLineal * terminoLineal - gravedad * gravedad * distanciaAlCuadrado

    private fun esAlcanzable(
        discriminante: Double,
        terminoLineal: Double,
        distanciaAlCuadrado: Double,
        gravedad: Double
    ): Boolean {
        val tolerancia = 1e-12 * maxOf(
            1.0,
            terminoLineal * terminoLineal,
            gravedad * gravedad * distanciaAlCuadrado
        )
        return discriminante >= -tolerancia
    }

    private fun calcularTiempoDeVuelo(
        terminoLineal: Double,
        distanciaAlCuadrado: Double,
        gravedad: Double,
        trayectoriaAlta: Boolean
    ): Double? {
        val discriminante = discriminante(terminoLineal, distanciaAlCuadrado, gravedad)
        if (!esAlcanzable(discriminante, terminoLineal, distanciaAlCuadrado, gravedad)) return null

        val raiz = sqrt(discriminante.coerceAtLeast(0.0))
        val denominador = gravedad * gravedad
        val tiemposValidos = listOf(
            2.0 * (terminoLineal - raiz) / denominador,
            2.0 * (terminoLineal + raiz) / denominador
        ).filter { it.isFinite() && it > 0.0 }
        val tiempoAlCuadrado = if (trayectoriaAlta) {
            tiemposValidos.maxOrNull()
        } else {
            tiemposValidos.minOrNull()
        } ?: return null
        return sqrt(tiempoAlCuadrado)
    }
}
