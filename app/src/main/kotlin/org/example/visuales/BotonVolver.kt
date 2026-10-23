package org.example.visuales

import javafx.scene.control.Button

//crea un boton con estilo de flecha para volver a la pantalla anterior, 
//el "(onClick: () -> Unit)" declara que la clase va a recibir un parametro,
//pero no devuelve nada, solo lo va a usar para el setOnAction


class BotonVolver(onClick: () -> Unit) : Button("🡰") { 
    init { 
        style = ESTILO_NORMAL
        setOnMouseEntered { style = ESTILO_HOVER }
        setOnMouseExited { style = ESTILO_NORMAL }
        setOnAction { onClick() }
    }
    //el init son los parametros que se ejecutan apenas se crea la clase, 
    //en este caso, el estilo del boton y el setOnAction que ejecuta la funcion que se le pasa como parametro


    private companion object {
        const val ESTILO_NORMAL = """
            -fx-background-color: transparent;
            -fx-text-fill: white;
            -fx-font-size: 40px;
            -fx-font-weight: bold;
            -fx-cursor: hand;
        """
        const val ESTILO_HOVER = """
            -fx-background-color: transparent;
            -fx-text-fill: #A0A0A0;
            -fx-font-size: 40px;
            -fx-font-weight: bold;
            -fx-cursor: hand;
        """
        //aca se esta definiendo que para el boton, cuando el mouse esta encima, el color del texto cambia a gris, y cuando no esta encima, es blanco
        //ademas de darle otros parametros, como el tipo de cursor (mano en este caso)
    }
}