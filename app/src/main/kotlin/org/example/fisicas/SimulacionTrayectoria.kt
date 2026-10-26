package org.example.fisicas

enum class EstadoTrayectoria {
    // Estado de la simulación de trayectoria, que puede ser PREPARADA, EN_VUELO o COMPLETADA
    PREPARADA,
    EN_VUELO,
    COMPLETADA
}

class SimulacionTrayectoria(
    // Clase que representa la simulación de la trayectoria de un proyectil, gestionando su estado, tipo de trayectoria, tiempo transcurrido y objetivo
    private val calculadora: CalculadorTrayectoria,
    private val pixelesPorMetro: Double,
    private val gravedad: Double,
    val proyectil: Proyectil
) {
    // Estado actual de la simulación, que puede ser PREPARADA, EN_VUELO o COMPLETADA

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
        // Reinicia la simulación estableciendo el origen Y del proyectil, reiniciando su posición y estado, y configurando el tiempo transcurrido y de vuelo a cero
        this.origenY = origenY
        proyectil.reiniciar(ORIGEN_X, origenY)
        proyectil.desactivar()
        tiempoTranscurrido = 0.0
        tiempoDeVuelo = 0.0
        estado = EstadoTrayectoria.PREPARADA
    }

    fun calcularVelocidad(objetivoX: Double, objetivoY: Double, rapidez: Double): VelocidadInicial? =
    // Calcula la velocidad inicial necesaria para alcanzar un objetivo dado la rapidez y el tipo de trayectoria (alta o baja). Devuelve null si no es posible calcular la velocidad.
        calculadora.calcular(
            SolicitudTrayectoria(
                // Crea una solicitud de trayectoria con los parámetros necesarios para calcular la velocidad inicial
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
        // Dispara el proyectil hacia un objetivo dado la rapidez, calculando la velocidad inicial necesaria y actualizando el estado de la simulación. Devuelve null si no es posible calcular la velocidad.
        objetivoX: Double,
        objetivoY: Double,
        rapidez: Double
    ): VelocidadInicial? {
        if (estado == EstadoTrayectoria.EN_VUELO) return null
        // Calcula la velocidad inicial necesaria para alcanzar el objetivo dado la rapidez y el tipo de trayectoria (alta o baja). Si no se puede calcular la velocidad, devuelve null.
        val velocidad = calcularVelocidad(objetivoX, objetivoY, rapidez) ?: return null

        // Actualiza el estado de la simulación a EN_VUELO, establece el objetivo y el tiempo de vuelo, reinicia el proyectil en el origen y lo lanza con la velocidad calculada
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
        // Avanza la simulación del proyectil si el estado es EN_VUELO y el tiempo transcurrido es válido. Actualiza la posición del proyectil y verifica si ha alcanzado el objetivo, en cuyo caso lo coloca en la posición final, lo desactiva y cambia el estado a COMPLETADA.
        if (estado != EstadoTrayectoria.EN_VUELO || !deltaSegundos.isFinite() || deltaSegundos <= 0.0) {
            return
        }
        // Actualiza el tiempo transcurrido y la posición del proyectil según la gravedad y los pixeles por metro

        tiempoTranscurrido += deltaSegundos
        proyectil.actualizar(deltaSegundos, gravedad = -gravedad * pixelesPorMetro)
        if (tiempoTranscurrido >= tiempoDeVuelo) {
            // Si el tiempo transcurrido supera el tiempo de vuelo, coloca el proyectil en la posición final del objetivo, lo desactiva y cambia el estado a COMPLETADA
            proyectil.colocarEn(objetivoX, objetivoY)
            proyectil.desactivar()
            estado = EstadoTrayectoria.COMPLETADA
        }
    }

    fun detener() {
        // Detiene la simulación del proyectil si el estado es EN_VUELO, desactiva el proyectil y cambia el estado a COMPLETADA
        proyectil.desactivar()
        estado = EstadoTrayectoria.COMPLETADA
    }

    private companion object {
        const val ORIGEN_X = 100.0
    }
}