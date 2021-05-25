package org.typ.view;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Textes en rouge
 */
public class TextFalse extends Text {

    public TextFalse(String word) {
        super(word);
        this.setFill(Color.RED);
    }
}
