package org.typ.controller.menu;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.typ.controller.ControllerClassicMode;
import org.typ.model.ClassicCorrector;
import org.typ.model.ClassicTextGenerator;
import org.typ.view.ViewClassicMode;

import java.io.FileNotFoundException;
import java.io.IOException;

public class StartClassicModeCommand implements Command {

    private Scene playScene;

    public StartClassicModeCommand(Scene playScene) {
        this.playScene = playScene;
    }

    @Override
    public void execute() {
        ViewClassicMode view = new ViewClassicMode();

        ClassicTextGenerator ctg = new ClassicTextGenerator("src/main/resources/org/typ/dictionaries/mots_courants_fr.csv", 1500, 15);

        ClassicCorrector model = null;
        try {
            model = new ClassicCorrector(ctg);
            model.addPropertyChangeListener(view);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ControllerClassicMode controller = new ControllerClassicMode(model);

        FXMLLoader loaderView = new FXMLLoader(getClass().getResource("/org/typ/fxmlViewClassic.fxml"));
        FXMLLoader loaderController = new FXMLLoader(getClass().getResource("/org/typ/fxmlControllerClassic.fxml"));

        loaderView.setController(view);
        loaderController.setController(controller);

        VBox layout = null;
        try {
            layout = new VBox(20, (AnchorPane)loaderView.load(), (AnchorPane)loaderController.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        layout.setBackground(new Background(new BackgroundFill(Color.rgb(7, 39, 69), CornerRadii.EMPTY, Insets.EMPTY)));
        Scene scene = new Scene(layout);


        controller.start(view);

        model.initialize();

        Stage stage = (Stage) this.playScene.getWindow();
        stage.setScene(scene);
    }
}
