package org.typ.controller.menu;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class ExitGameCommand implements Command {

    private Parent layout;
    private Pane pane;

    public ExitGameCommand(Pane pane, Parent layout) {
        this.layout = layout;
        this.pane = pane;
    }

    @Override
    public void execute() {
        this.pane.getChildren().remove(layout);
    }
}
