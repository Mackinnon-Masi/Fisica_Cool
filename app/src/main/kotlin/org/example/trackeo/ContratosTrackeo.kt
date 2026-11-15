package org.example.trackeo

import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.scene.layout.Pane

/** Contratos de los lectores de entrada (mouse y teclado). */

interface IRastreadorMouse {
    val lastX: Double
    val lastY: Double
    fun attachTo(pane: Pane)
    fun mostrarEtiqueta(visible: Boolean)
}

interface ILectorTeclado {
    fun attachTo(scene: Scene)
    fun isKeyPressed(keyCode: KeyCode): Boolean
    fun wasJustPressed(keyCode: KeyCode): Boolean
    fun detach()
}
