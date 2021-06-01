package org.typ.view;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class NotYetWrittenPart extends Text {

    public NotYetWrittenPart(String wordPart){
        super(wordPart);
        this.setFill(Color.BLUE);
    }
}
