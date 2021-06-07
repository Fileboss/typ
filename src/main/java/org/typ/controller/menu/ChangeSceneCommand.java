package org.typ.controller.menu;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChangeSceneCommand implements Command {
    private final Scene oldScene;
    private final Scene newScene;

    public ChangeSceneCommand(Scene oldScene, Scene newScene) {
        this.oldScene = oldScene;
        this.newScene = newScene;
    }

    @Override
    public void execute() {
        Stage stage = (Stage) this.oldScene.getWindow();
        stage.setScene(this.newScene);
    }
}
