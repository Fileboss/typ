package org.typ.view;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class CurrentWord extends TextFlow {

    public CurrentWord(Text correctPart, Text remainingPart) {
        super();
        correctPart.setFont(Font.font ("Verdana", FontWeight.BOLD, 20));
        remainingPart.setFont(Font.font ("Verdana", FontWeight.BOLD, 20));
        this.getChildren().add(correctPart);
        this.getChildren().add(remainingPart);
    }
}

