package org.example.fisicas

/**
 * Parametros de un calculo de tiro parabolico.
 *
 * Responsabilidad unica: transportar y validar los datos de la solicitud.
 */
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
