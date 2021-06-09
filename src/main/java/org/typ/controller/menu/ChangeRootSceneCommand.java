package org.typ.controller.menu;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class ChangeRootSceneCommand implements Command {
    private Scene scene;
    private Parent root;

    public ChangeRootSceneCommand(Scene scene, Parent root) {
        this.scene = scene;
        this.root = root;
    }

    @Override
    public void execute() {
        this.scene.setRoot(root);
    }
}
