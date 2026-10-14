package org.typ.view;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Textes en rouge
 */
public class TextFalse extends Text {

    public TextFalse(String word) {
        super(word);
        String colorStr = "#eb4034";
        setFill(Color.rgb(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) ));
    }
}
