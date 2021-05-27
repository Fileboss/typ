package org.typ;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.typ.controller.ControllerClassicMode;
import org.typ.model.AbstractCorrector;
import org.typ.model.ClassicCorrector;
import org.typ.model.ClassicTextGenerator;
import org.typ.model.ModelTest;
import org.typ.view.ViewClassicMode;

import java.io.FileNotFoundException;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {

        stage.setTitle("Typ: classic mode");

        ViewClassicMode view = new ViewClassicMode();

        ClassicTextGenerator ctg = new ClassicTextGenerator("src/main/resources/mots_courants_en.csv", 1500, 100);

        ClassicCorrector model = null;
        try {
            model = new ClassicCorrector(ctg, view);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ControllerClassicMode controller = new ControllerClassicMode(model);

        VBox layout = new VBox(20, view, controller);


        layout.setPadding(new Insets(20,20,20,20));
        var scene = new Scene(layout, 800, 600);

        stage.setMinHeight(300);
        stage.setMinWidth(700);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}