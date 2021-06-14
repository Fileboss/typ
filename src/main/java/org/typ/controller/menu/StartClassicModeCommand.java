package org.typ.controller.menu;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.typ.controller.ControllerClassicMode;
import org.typ.model.ClassicCorrector;
import org.typ.model.ClassicTextGenerator;
import org.typ.view.ViewClassicMode;

import java.io.FileNotFoundException;
import java.io.IOException;

public class StartClassicModeCommand implements Command {

    private Pane scene;

    public StartClassicModeCommand(Pane scene) {
        this.scene = scene;
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
        FXMLLoader loaderEntete = new FXMLLoader(getClass().getResource("/org/typ/entete.fxml"));

        loaderView.setController(view);
        loaderController.setController(controller);


        VBox layout = null;
        Parent entete = null;
        try {
            layout = new VBox(20, loaderView.load(), loaderController.load());
            entete = loaderEntete.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        layout.setBackground(new Background(new BackgroundFill(Color.rgb(7, 39, 69), CornerRadii.EMPTY, Insets.EMPTY)));

        BorderPane pane = new BorderPane();
        pane.setTop(entete);
        pane.setCenter(layout);

        controller.setActionExitButton(new ExitGameCommand(scene, pane));
        controller.start(view);

        model.stop();
        model.initialize();

        scene.getChildren().add(pane);
        pane.toFront();
    }
}
