package org.example.fisicas

/**
 * Representa un proyectil en movimiento parabólico (MRU en X, MRUV en Y).
 *
 * Responsabilidad única: Mantener estado cinemático y actualizarlo según leyes físicas.
 * No conoce al simulador, al cañón, ni a la renderización.
 *
 */
class Proyectil(
    posicionX: Double = 0.0,
    posicionY: Double = 0.0,
    velocidadX: Double = 0.0,
    velocidadY: Double = 0.0,
    activo: Boolean = true
) {
    var posicionX: Double = posicionX
        private set
    var posicionY: Double = posicionY
        private set
    var velocidadX: Double = velocidadX
        private set
    var velocidadY: Double = velocidadY
        private set
    var activo: Boolean = activo
        private set


    /**
     * Actualiza la cinemática del proyectil usando integración de Euler simple.
     *
     * Ecuaciones del movimiento:
     * - MRU en X: x_nueva = x + v_x * dt
     * - MRUV en Y: y_nueva = y + v_y * dt
     * - MRUV en Y: v_y_nueva = v_y - g * dt (gravedad positiva hacia abajo)
     * @param dt Intervalo de tiempo (delta time) en segundos.
    * @param gravedad Aceleración firmada en unidades de posición/s². En esta
    * convención, un valor negativo produce aceleración hacia +Y.
     */
    fun actualizar(dt: Double, gravedad: Double): Unit {
        if (!activo) return

        // MRU en eje X (velocidad constante, sin resistencia del aire)
        posicionX += velocidadX * dt

        // MRUV en eje Y: mismo desplazamiento analítico que la trayectoria prevista.
        posicionY += velocidadY * dt - 0.5 * gravedad * dt * dt
        velocidadY -= gravedad * dt
    }

    /**
     * Marca el proyectil como inactivo (ha tocado suelo o se ha destruido).
     */
    fun desactivar(): Unit {
        activo = false
    }

    fun colocarEn(x: Double, y: Double) {
        posicionX = x
        posicionY = y
    }

    fun lanzar(velocidadX: Double, velocidadY: Double) {
        this.velocidadX = velocidadX
        this.velocidadY = velocidadY
        activo = true
    }

    fun reiniciar(x: Double, y: Double) {
        colocarEn(x, y)
        velocidadX = 0.0
        velocidadY = 0.0
        activo = true
    }

    override fun toString(): String {
        val estado = if (activo) "ACTIVO" else "INACTIVO"
        return "Proyectil(x=%.2f, y=%.2f, vx=%.2f, vy=%.2f, %s)".format(
            posicionX, posicionY, velocidadX, velocidadY, estado
        )
    }
}