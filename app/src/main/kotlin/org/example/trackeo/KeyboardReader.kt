package org.example.trackeo

import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent

class KeyboardReader {
    // Variables para almacenar el estado de las teclas
    private val pressedKeys = mutableSetOf<KeyCode>()
    private val consumedPresses = mutableSetOf<KeyCode>()
    private var attachedScene: Scene? = null

    private val keyPressedHandler = EventHandler<KeyEvent> { event ->
        // Agrega la tecla presionada al conjunto de teclas presionadas
        pressedKeys.add(event.code)
    }
    private val keyReleasedHandler = EventHandler<KeyEvent> { event ->
        // Elimina la tecla liberada del conjunto de teclas presionadas
        pressedKeys.remove(event.code)
    }

    fun attachTo(scene: Scene) {
        // Si ya está adjunto a otra escena, primero se desadjunta
        detach()
        attachedScene = scene
        // Agrega los manejadores de eventos para presionar y liberar teclas a la escena
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyPressedHandler)
        scene.addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedHandler)
    }

    fun isKeyPressed(keyCode: KeyCode): Boolean = keyCode in pressedKeys
    // Verifica si una tecla específica está actualmente presionada

    fun wasJustPressed(keyCode: KeyCode): Boolean {
        // Verifica si una tecla específica fue presionada recientemente y no ha sido consumida
        if (!isKeyPressed(keyCode)) {
            // Si la tecla no está presionada, se elimina de las teclas consumidas
            consumedPresses.remove(keyCode)
            return false
        }
        // Si la tecla está presionada y no ha sido consumida, se marca como consumida y se devuelve true
        return consumedPresses.add(keyCode)
    }

    fun detach() {
        // Si hay una escena adjunta, se eliminan los manejadores de eventos y se limpia el estado
        attachedScene?.let { scene ->
            // Elimina los manejadores de eventos de la escena adjunta
            scene.removeEventFilter(KeyEvent.KEY_PRESSED, keyPressedHandler)
            scene.removeEventFilter(KeyEvent.KEY_RELEASED, keyReleasedHandler)
        }
        // Limpia el estado de las teclas y la escena adjunta
        attachedScene = null
        pressedKeys.clear()
        consumedPresses.clear()
    }
}
