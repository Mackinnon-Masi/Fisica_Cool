package org.example.Fisicas

/**
 * Representa un proyectil en movimiento parabólico (MRU en X, MRUV en Y).
 *
 * Responsabilidad única: Mantener estado cinemático y actualizarlo según leyes físicas.
 * No conoce al simulador, al cañón, ni a la renderización.
 *
 * Principios SOLID aplicados:
 * - Single Responsibility: Solo gestiona su propia cinemática
 * - Open/Closed: Extensible via herencia sin modificar código existente
 * - Liskov Substitution: Puede ser sustituido por subtipos
 */
class Proyectil(
    var posicionX: Double = 0.0,
    var posicionY: Double = 0.0,
    var velocidadX: Double = 4.0,
    var velocidadY: Double = 0.0,
    var activo: Boolean = true
) {

    /**
     * Actualiza la cinemática del proyectil usando integración de Euler simple.
     *
     * Ecuaciones del movimiento:
     * - MRU en X: x_nueva = x + v_x * dt
     * - MRUV en Y: y_nueva = y + v_y * dt
     * - MRUV en Y: v_y_nueva = v_y - g * dt  (gravedad positiva hacia abajo)
     *
     * @param dt Intervalo de tiempo (delta time) en segundos.
     * @param gravedad Aceleración gravitacional (m/s²), positiva hacia abajo.
     */
    fun actualizar(dt: Double, gravedad: Double): Unit {
        if (!activo) return

        // MRU en eje X (velocidad constante, sin resistencia del aire)
        posicionX += velocidadX * dt

        // MRUV en eje Y (aceleración constante = -gravedad)
        posicionY += velocidadY * dt
        velocidadY -= gravedad * dt
    }

    /**
     * Marca el proyectil como inactivo (ha tocado suelo o se ha destruido).
     */
    fun desactivar(): Unit {
        activo = false
    }

    override fun toString(): String {
        val estado = if (activo) "ACTIVO" else "INACTIVO"
        return "Proyectil(x=%.2f, y=%.2f, vx=%.2f, vy=%.2f, %s)".format(
            posicionX, posicionY, velocidadX, velocidadY, estado
        )
    }
}