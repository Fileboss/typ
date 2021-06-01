package org.typ.view;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class IncorrectPart extends Text {
    public IncorrectPart(String wordPart) {
        super(wordPart);
        this.setFill(Color.RED);
    }
}
