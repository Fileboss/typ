package org.typ;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.typ.view.menu.Command;

class changeSceneCommand implements Command {
    private final Scene oldScene;
    private final Scene newScene;

    public changeSceneCommand(Scene oldScene, Scene newScene) {
        this.oldScene = oldScene;
        this.newScene = newScene;
    }

    @Override
    public void execute() {
        Stage stage = (Stage) this.oldScene.getWindow();
        stage.setScene(this.newScene);
    }
}
