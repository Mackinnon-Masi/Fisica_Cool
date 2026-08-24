package org.example.Fisicas

/**
 * Motor de simulación que gestiona la evolución temporal del sistema.
 *
 * Responsabilidad única: Coordinar la actualización de proyectiles,
 * detectar colisiones con el suelo y gestionar el ciclo de vida.
 *
 * Principios SOLID aplicados:
 * - Single Responsibility: Solo gestiona el loop de simulación
 * - Open/Closed: Extensible para diferentes fuerzas/efectos
 * - Dependency Inversion: Trabaja con Proyectil (concreto pero intercambiable)
 */
class Simulador(
    var gravedad: Double = 9.81,
    var ySuelo: Double = 0.0
) {

    private val proyectiles: MutableList<Proyectil> = mutableListOf()

    /**
     * Registra un proyectil en la simulación.
     *
     * @param proyectil Instancia de Proyectil a simular.
     */
    fun agregarProyectil(proyectil: Proyectil): Unit {
        proyectiles.add(proyectil)
    }

    /**
     * Avanza la simulación un paso de tiempo dt.
     *
     * Para cada proyectil activo:
     * 1. Actualiza su cinemática.
     * 2. Verifica si ha cruzado o tocado el nivel del suelo (y <= ySuelo).
     * 3. Si toca suelo, lo desactiva.
     *
     * @param dt Intervalo de tiempo en segundos.
     */
    fun actualizarSimulacion(dt: Double): Unit {
        // Iteramos sobre una copia para permitir eliminación segura durante iteración
        for (proyectil in proyectiles.toList()) {
            if (!proyectil.activo) continue

            proyectil.actualizar(dt, gravedad)

            // Detección de colisión con suelo (sin rebote, sin fricción)
            if (proyectil.posicionY <= ySuelo) {
                // Clamp visual opcional: asegurar que no pase por debajo del suelo
                proyectil.posicionY = ySuelo
                proyectil.desactivar()
            }
        }
    }

    /**
     * Retorna solo los proyectiles que siguen activos.
     */
    fun obtenerProyectilesActivos(): List<Proyectil> {
        return proyectiles.filter { it.activo }
    }

    /**
     * Retorna todos los proyectiles (activos e inactivos).
     */
    fun obtenerTodosLosProyectiles(): List<Proyectil> {
        return proyectiles.toList()
    }

    override fun toString(): String {
        val activos = obtenerProyectilesActivos().size
        val total = proyectiles.size
        return "Simulador(g=%.2f, suelo=%.2f, activos=%d/%d)".format(gravedad, ySuelo, activos, total)
    }
}