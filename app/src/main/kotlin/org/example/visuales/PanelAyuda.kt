package org.example.visuales

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font

/** Capa emergente con la ayuda de controles. */
class PanelAyuda : IPanelAyuda {

    override val nodo = StackPane()

    init {
        val botonCerrar = Button("×").apply {
            minWidth = 24.0
            prefWidth = 24.0
            maxWidth = 24.0
            minHeight = 24.0
            prefHeight = 24.0
            maxHeight = 24.0
            style = "-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0; -fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold; -fx-cursor: hand;"
            setOnAction { cerrar() }
        }
        val panel = VBox(
            2.0,
            BorderPane().apply {
                left = Label("Controles").apply {
                    textFill = Color.WHITE
                    style = "-fx-font-size: 14px; -fx-font-weight: bold;"
                }
                right = botonCerrar
            },
            Label("E: editar/salir  |  Q: cambiar trayectoria  |  Espacio: lanzar").apply {
                textFill = Color.WHITE
                font = Font.font(12.0)
                isWrapText = true
                maxWidth = ANCHO_PANEL_AYUDA
            }
        ).apply {
            prefWidth = ANCHO_PANEL_AYUDA + 24.0
            maxWidth = ANCHO_PANEL_AYUDA + 24.0
            minHeight = ALTO_PANEL_AYUDA
            prefHeight = ALTO_PANEL_AYUDA
            maxHeight = ALTO_PANEL_AYUDA
            style = "-fx-background-color: black; -fx-padding: 6px; -fx-border-color: white; -fx-border-width: 1px;"
        }

        nodo.children.add(panel)
        nodo.alignment = Pos.CENTER
        nodo.isVisible = false
        nodo.isManaged = false
        nodo.isPickOnBounds = false
    }

    override fun abrir() {
        nodo.isManaged = true
        nodo.isVisible = true
    }

    override fun cerrar() {
        nodo.isVisible = false
        nodo.isManaged = false
    }

    override fun alternar() {
        if (nodo.isVisible) cerrar() else abrir()
    }

    private companion object {
        const val ANCHO_PANEL_AYUDA = 280.0
        const val ALTO_PANEL_AYUDA = 78.0
    }
}
