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
        //si el proyectil no esta activo, no hace nada
        if (!activo) return

        // MRU en eje X (sin resistencia del aire)
        posicionX += velocidadX * dt

        // MRUV en eje Y: mismo desplazamiento analítico que la trayectoria prevista.
        posicionY += velocidadY * dt - 0.5 * gravedad * dt * dt
        velocidadY -= gravedad * dt
    }

    //desactivar pone activo en false. No borra la posición ni las velocidades; simplemente hace que actualizar deje de moverlo.
    fun desactivar(): Unit {
        activo = false
    }

    //colocarEn cambia su posición a unas coordenadas concretas.
    fun colocarEn(x: Double, y: Double) {
        posicionX = x
        posicionY = y
    }

    //lanzar establece sus velocidades y lo activa.
    fun lanzar(velocidadX: Double, velocidadY: Double) {
        this.velocidadX = velocidadX
        this.velocidadY = velocidadY
        activo = true
    }

    //reiniciar lo coloca donde indiques, pone ambas velocidades en cero y lo activa.
    fun reiniciar(x: Double, y: Double) {
        colocarEn(x, y)
        velocidadX = 0.0
        velocidadY = 0.0
        activo = true
    }

    //toString devuelve Proyectil(x=1.50, y=5.25, vx=3.00, vy=13.00, ACTIVO) con los valores del momento
    override fun toString(): String {
        val estado = if (activo) "ACTIVO" else "INACTIVO"
        return "Proyectil(x=%.2f, y=%.2f, vx=%.2f, vy=%.2f, %s)".format(
            posicionX, posicionY, velocidadX, velocidadY, estado
        )
    }
}