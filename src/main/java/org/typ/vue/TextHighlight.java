package org.typ.vue;

import javafx.scene.effect.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class TextHighlight extends Text {

    public TextHighlight(String word) {
        super(word);
        this.setFill(Color.BLUE);

    }
}
