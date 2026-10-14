package org.typ.view;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Texte mis en valeur (utilisé pour le mot courant)
 */
public class TextRemaining extends Text {

    public TextRemaining(String word) {
        super(word);
        String colorStr = "#c5b028";
        setFill(Color.rgb(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) ));

    }
}
