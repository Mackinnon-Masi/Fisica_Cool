package org.example.trackeo

import javafx.scene.Scene
import javafx.scene.input.KeyCode

/**
 * Contrato de lectura de teclado.
 *
 * Permite al controlador consultar el estado de las teclas sin depender de
 * JavaFX y facilita sustituirlo por una implementacion de prueba.
 */
interface ILectorTeclado {
    fun attachTo(scene: Scene)
    fun isKeyPressed(keyCode: KeyCode): Boolean
    fun wasJustPressed(keyCode: KeyCode): Boolean
    fun detach()
}
