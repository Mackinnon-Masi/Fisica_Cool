package org.example.visuales

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font

internal class PanelAyudaControles {
    val raiz: StackPane

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
        val contenido = VBox(
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
                maxWidth = ANCHO + 24.0
            }
        ).apply {
            prefWidth = ANCHO + 24.0
            maxWidth = ANCHO + 24.0
            minHeight = ALTO
            prefHeight = ALTO
            maxHeight = ALTO
            style = "-fx-background-color: black; -fx-padding: 6px; -fx-border-color: white; -fx-border-width: 1px;"
        }
        raiz = StackPane(contenido).apply {
            alignment = Pos.CENTER
            isVisible = false
            isManaged = false
            isPickOnBounds = false
        }
    }

    fun abrir() {
        raiz.isManaged = true
        raiz.isVisible = true
    }

    fun alternar() {
        if (raiz.isVisible) cerrar() else abrir()
    }

    private fun cerrar() {
        raiz.isVisible = false
        raiz.isManaged = false
    }

    private companion object {
        const val ANCHO = 280.0
        const val ALTO = 78.0
    }
}