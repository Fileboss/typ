package org.typ;

import javafx.application.Application;
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


/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("Typ: classic mode");

        ViewClassicMode view = new ViewClassicMode();

        ClassicTextGenerator ctg = new ClassicTextGenerator("src/main/resources/mots_courants_en.csv", 1500, 10);

        ClassicCorrector model = null;
        try {
            model = new ClassicCorrector(ctg, view);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ControllerClassicMode controller = new ControllerClassicMode(model);

        FXMLLoader loaderView = new FXMLLoader(getClass().getResource("fxmlViewClassic.fxml"));
        FXMLLoader loaderController = new FXMLLoader(getClass().getResource("fxmlControllerClassic.fxml"));

        loaderView.setController(view);
        loaderController.setController(controller);


        VBox layout = new VBox(20, (AnchorPane)loaderView.load(), (AnchorPane)loaderController.load());
        layout.setBackground(new Background(new BackgroundFill(Color.rgb(7, 39, 69), CornerRadii.EMPTY, Insets.EMPTY)));
        scene = new Scene(layout);


        controller.start();
        model.start();

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}