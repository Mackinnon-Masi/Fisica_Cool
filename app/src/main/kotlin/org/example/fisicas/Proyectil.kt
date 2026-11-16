package org.example.fisicas

/**
 * Representa un proyectil en movimiento parabolico (MRU en X, MRUV en Y).
 *
 * Responsabilidad unica: mantener el estado cinematico y actualizarlo segun
 * las leyes fisicas. No conoce al simulador, al canon ni a la renderizacion.
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
     * Actualiza la cinematica usando integracion de Euler simple.
     *
     * - MRU en X: x_nueva = x + v_x * dt
     * - MRUV en Y: y_nueva = y + v_y * dt
     * - MRUV en Y: v_y_nueva = v_y - g * dt
     *
     * @param dt Intervalo de tiempo en segundos.
     * @param gravedad Aceleracion firmada en unidades de posicion/s^2.
     */
    fun actualizar(dt: Double, gravedad: Double) {
        if (!activo) return

        posicionX += velocidadX * dt
        posicionY += velocidadY * dt - 0.5 * gravedad * dt * dt
        velocidadY -= gravedad * dt
    }

    /** Desactiva el proyectil sin borrar su posicion ni sus velocidades. */
    fun desactivar() {
        activo = false
    }

    /** Coloca el proyectil en unas coordenadas concretas. */
    fun colocarEn(x: Double, y: Double) {
        posicionX = x
        posicionY = y
    }

    /** Establece las velocidades y activa el proyectil. */
    fun lanzar(velocidadX: Double, velocidadY: Double) {
        this.velocidadX = velocidadX
        this.velocidadY = velocidadY
        activo = true
    }

    /** Reinicia posicion y velocidades, y activa el proyectil. */
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
