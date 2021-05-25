package org.typ.view;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Texte mis en valeur (utilisé pour le mot courant)
 */
public class TextHighlight extends Text {

    public TextHighlight(String word) {
        super(word);
        this.setFill(Color.BLUE);

    }
}
