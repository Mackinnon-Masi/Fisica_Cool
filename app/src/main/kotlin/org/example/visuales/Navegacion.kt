package org.example.visuales

import javafx.scene.Scene
import javafx.scene.layout.Pane

/**
 * Navegacion entre las pantallas de la aplicacion.
 *
 * Responsabilidad unica: construir el menu y cambiar la vista visible.
 */
class Navegacion(private val escena: Scene) {
    private lateinit var raizInicio: Pane
    private val raizCreditos: Pane by lazy {
        Creditos().crearVista { mostrarInicio() }
    }

    fun configurarInicio(onIniciarClick: () -> Unit) {
        raizInicio = Inicio().crearVista(
            onIniciarClick = onIniciarClick,
            onCreditosClick = { mostrar(raizCreditos) }
        )
    }

    fun mostrarInicio() {
        mostrar(raizInicio)
    }

    fun mostrar(raiz: Pane) {
        escena.root = raiz
        raiz.applyCss()
        raiz.layout()
    }
}
