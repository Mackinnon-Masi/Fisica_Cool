package org.example.fisicas

enum class EstadoTrayectoria {
    PREPARADA,
    EN_VUELO,
    COMPLETADA
}

class SimulacionTrayectoria(
    private val calculadora: CalculadorTrayectoria,
    private val pixelesPorMetro: Double,
    private val gravedad: Double,
    val proyectil: Proyectil
) {
    var estado: EstadoTrayectoria = EstadoTrayectoria.PREPARADA
        private set
    var trayectoriaAlta: Boolean = false
        private set
    var tiempoTranscurrido: Double = 0.0
        private set
    var tiempoDeVuelo: Double = 0.0
        private set
    private var objetivoX = 0.0
    private var objetivoY = 0.0
    private var origenY = proyectil.posicionY

    init {
        reiniciar(origenY)
    }

    fun alternarTrayectoria() {
        trayectoriaAlta = !trayectoriaAlta
    }

    fun reiniciar(origenY: Double) {
        this.origenY = origenY
        proyectil.reiniciar(ORIGEN_X, origenY)
        proyectil.desactivar()
        tiempoTranscurrido = 0.0
        tiempoDeVuelo = 0.0
        estado = EstadoTrayectoria.PREPARADA
    }

    fun calcularVelocidad(objetivoX: Double, objetivoY: Double, rapidez: Double): VelocidadInicial? =
        calculadora.calcular(
            SolicitudTrayectoria(
                origenX = ORIGEN_X / pixelesPorMetro,
                origenY = origenY / pixelesPorMetro,
                objetivoX = objetivoX / pixelesPorMetro,
                objetivoY = objetivoY / pixelesPorMetro,
                rapidez = rapidez,
                gravedad = gravedad,
                trayectoriaAlta = trayectoriaAlta
            )
        )

    fun disparar(
        objetivoX: Double,
        objetivoY: Double,
        rapidez: Double
    ): VelocidadInicial? {
        if (estado == EstadoTrayectoria.EN_VUELO) return null
        val velocidad = calcularVelocidad(objetivoX, objetivoY, rapidez) ?: return null

        this.objetivoX = objetivoX
        this.objetivoY = objetivoY
        tiempoDeVuelo = velocidad.tiempoDeVuelo
        tiempoTranscurrido = 0.0
        proyectil.reiniciar(ORIGEN_X, origenY)
        proyectil.lanzar(
            velocidadX = velocidad.velocidadX * pixelesPorMetro,
            velocidadY = velocidad.velocidadY * pixelesPorMetro
        )
        estado = EstadoTrayectoria.EN_VUELO
        return velocidad
    }

    fun avanzar(deltaSegundos: Double) {
        if (estado != EstadoTrayectoria.EN_VUELO || !deltaSegundos.isFinite() || deltaSegundos <= 0.0) {
            return
        }

        tiempoTranscurrido += deltaSegundos
        proyectil.actualizar(deltaSegundos, gravedad = -gravedad * pixelesPorMetro)
        if (tiempoTranscurrido >= tiempoDeVuelo) {
            proyectil.colocarEn(objetivoX, objetivoY)
            proyectil.desactivar()
            estado = EstadoTrayectoria.COMPLETADA
        }
    }

    fun detener() {
        proyectil.desactivar()
        estado = EstadoTrayectoria.COMPLETADA
    }

    private companion object {
        const val ORIGEN_X = 100.0
    }
}