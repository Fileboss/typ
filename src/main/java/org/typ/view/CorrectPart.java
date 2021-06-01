package org.typ.view;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class CorrectPart extends Text {
    public CorrectPart(String wordPart) {
        super(wordPart);
        this.setFill(Color.GREEN);
    }
}
