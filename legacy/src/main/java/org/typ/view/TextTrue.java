package org.typ.view;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Textes en vert
 */
public class TextTrue extends Text {

    public TextTrue(String s) {
        super(s);
        String colorStr = "#529c48";
        setFill(Color.rgb(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) ));
    }
}
