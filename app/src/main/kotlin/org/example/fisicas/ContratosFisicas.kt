package org.example.fisicas

/**
 * Contratos del modelo fisico.
 *
 * Los colaboradores dependen de estas abstracciones y no de las
 * implementaciones concretas (DIP).
 */

interface ILecturaProyectil {
    val posicionX: Double
    val posicionY: Double
    val activo: Boolean
}

interface IMovimientoProyectil {
    fun actualizar(dt: Double, gravedad: Double)
    fun desactivar()
    fun colocarEn(x: Double, y: Double)
    fun lanzar(velocidadX: Double, velocidadY: Double)
    fun reiniciar(x: Double, y: Double)
}

interface IProyectil : ILecturaProyectil, IMovimientoProyectil

interface IEstadoSimulacion {
    val estado: EstadoTrayectoria
    val trayectoriaAlta: Boolean
    val tiempoTranscurrido: Double
}

interface IProyectilSimulacion {
    val proyectil: ILecturaProyectil
    fun reiniciar(origenY: Double)
}

interface IDisparoSimulacion {
    fun alternarTrayectoria()
    fun calcularVelocidad(
        objetivoX: Double,
        objetivoY: Double,
        rapidez: Double
    ): VelocidadInicial?

    fun disparar(
        objetivoX: Double,
        objetivoY: Double,
        rapidez: Double
    ): VelocidadInicial?

    fun avanzar(deltaSegundos: Double)
    fun detener()
}

interface ISimulacionTrayectoria :
    IEstadoSimulacion,
    IProyectilSimulacion,
    IDisparoSimulacion
