package org.typ.controller.menu;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class ChangeRootSceneCommand implements Command {
    private Parent root;

    public ChangeRootSceneCommand(Parent root) {
        this.root = root;
    }

    @Override
    public void execute() {
        this.root.toFront();
    }
}
