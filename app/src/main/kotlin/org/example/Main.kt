package org.example

import org.example.Fisicas.Proyectil
import org.example.Fisicas.Simulador
import kotlin.math.sin

/**
 * Punto de entrada principal - Simulación de lanzamiento parabólico en consola.
 *
 * Ejecuta una simulación paso a paso de un disparo de cañón.
 * Imprime la trayectoria completa hasta que el proyectil toca el suelo.
 */
fun main() {
    println("=" repeat 70)
    println("SIMULACIÓN DE LANZAMIENTO PARABÓLICO - CONSOLA (Kotlin)")
    println("=" repeat 70)

    // Configuración del cañón
    val canon = Canon(
        posicionX = 0.0,
        posicionY = 1.5,      // Altura de la boca del cañón (1.5 m)
        anguloGrados = 45.0,  // Ángulo óptimo para máximo alcance sin altura inicial
        velocidadInicial = 25.0  // 25 m/s ≈ 90 km/h
    )
    println("Configuración: $canon")
    println("Gravedad: 9.81 m/s² | Suelo en y=0.0 m")
    println("-" repeat 70)

    // Disparo: crear proyectil
    val proyectil: Proyectil = canon.disparar()
    println("Disparo realizado → $proyectil")
    println("-" repeat 70)

    // Configuración del simulador
    val simulador = Simulador(gravedad = 9.81, ySuelo = 0.0)
    simulador.agregarProyectil(proyectil)

    // Parámetros de simulación
    val dt = 0.05  // Paso de tiempo: 50 ms (20 FPS lógicos)
    var tiempoTotal = 0.0
    var paso = 0

    // Encabezado de tabla
    println("Paso |  t (s) |    x (m) |    y (m) |  vx (m/s) |  vy (m/s) | Estado")
    println("-" repeat 70)

    // Bucle principal de simulación
    while (simulador.obtenerProyectilesActivos().isNotEmpty()) {
        // Imprimir estado actual
        println(formatearEstado(proyectil, tiempoTotal))

        // Avanzar simulación
        simulador.actualizarSimulacion(dt)
        tiempoTotal += dt
        paso++

        // Seguridad: evitar bucle infinito si hay error lógico
        if (paso > 10000) {
            println("������ Límite de pasos alcanzado - abortando")
            break
        }
    }

    // Imprimir estado final (cuando toca suelo)
    println(formatearEstado(proyectil, tiempoTotal))
    println("-" repeat 70)

    // Resultados finales
    val alturaMaximaTeorica = (canon.velocidadInicial * canon.velocidadInicial *
        sin(Math.toRadians(canon.anguloGrados)).pow(2)) / (2 * 9.81) + canon.posicionY

    println("\n���� RESULTADOS FINALES:")
    println("   Tiempo de vuelo:     %.2f s".format(tiempoTotal))
    println("   Alcance horizontal:  %.2f m".format(proyectil.posicionX))
    println("   Altura máxima teórica: %.2f m".format(alturaMaximaTeorica))
    println("=" repeat 70)
}

/**
 * Formatea el estado del proyectil para salida por consola.
 */
fun formatearEstado(proyectil: Proyectil, tiempo: Double): String {
    val estado = if (proyectil.activo) "ACTIVO" else "SUELO"
    return "t=%5.2fs | x=%7.2fm | y=%7.2fm | vx=%6.2fm/s | vy=%6.2fm/s | %s".format(
        tiempo,
        proyectil.posicionX,
        proyectil.posicionY,
        proyectil.velocidadX,
        proyectil.velocidadY,
        estado
    )
}