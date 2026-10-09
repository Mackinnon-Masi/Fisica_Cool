package org.example.Trackeo

import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent

class KeyboardReader {
    private val pressedKeys = mutableSetOf<KeyCode>()
    private var attachedScene: Scene? = null

    private val keyPressedHandler = EventHandler<KeyEvent> { event ->
        pressedKeys.add(event.code)
    }
    private val keyReleasedHandler = EventHandler<KeyEvent> { event ->
        pressedKeys.remove(event.code)
    }

    fun attachTo(scene: Scene) {
        detach()
        attachedScene = scene
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyPressedHandler)
        scene.addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedHandler)
    }

    fun isKeyPressed(keyCode: KeyCode): Boolean = keyCode in pressedKeys

    fun detach() {
        attachedScene?.let { scene ->
            scene.removeEventFilter(KeyEvent.KEY_PRESSED, keyPressedHandler)
            scene.removeEventFilter(KeyEvent.KEY_RELEASED, keyReleasedHandler)
        }
        attachedScene = null
        pressedKeys.clear()
    }
}
