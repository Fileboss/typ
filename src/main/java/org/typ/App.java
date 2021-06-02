package org.typ;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.typ.controller.menu.*;

import java.io.IOException;


/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("Typ: classic mode");

        MenuController controller = new MenuController();
        MenuController playController = new MenuController();
        MenuController settingsController = new MenuController();
        MenuController statisticsController = new MenuController();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/typ/menu.fxml"));
        FXMLLoader playLoader = new FXMLLoader(getClass().getResource("/org/typ/menu.fxml"));
        FXMLLoader settingsLoader = new FXMLLoader(getClass().getResource("/org/typ/menu.fxml"));
        FXMLLoader statisticsLoader = new FXMLLoader(getClass().getResource("/org/typ/menu.fxml"));

        loader.setController(controller);
        playLoader.setController(playController);
        settingsLoader.setController(settingsController);
        statisticsLoader.setController(statisticsController);

        Scene scene = new Scene(loader.load());
        Scene playScene = new Scene(playLoader.load());
        Scene settingsScene = new Scene(settingsLoader.load());
        Scene statisticsScene = new Scene(statisticsLoader.load());

        controller.setTitle("Menu");
        controller.addCommandButton("play", new ChangeSceneCommand(scene, playScene));
        controller.addCommandButton("statistics", new ChangeSceneCommand(scene, statisticsScene));
        controller.addCommandButton("settings", new ChangeSceneCommand(scene, settingsScene));
        controller.addCommandButton("exit", new Command() {
            @Override
            public void execute() {
                System.exit(0);
            }
        });

        settingsController.setTitle("Settings");
        settingsController.addCommandButton("back", new ChangeSceneCommand(settingsScene, scene));

        playController.setTitle("Play");
        playController.addCommandButton("Classic Mode", new StartClassicModeCommand(playScene));
        //playController.addCommandButton("Classic Mode", null);
        playController.addCommandButton("back", new ChangeSceneCommand(playScene, scene));

        statisticsController.setTitle("Statistics");
        statisticsController.addCommandButton("back", new ChangeSceneCommand(statisticsScene, scene));

        stage.setTitle("Typ");
        stage.setScene(scene);
        stage.show();

        // Fin FT-12

        /*
        ViewClassicMode view = new ViewClassicMode();

        ClassicTextGenerator ctg = new ClassicTextGenerator("src/main/resources/mots_courants_en.csv", 1500, 50);

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

         */
    }

    public static void main(String[] args) {
        launch();
    }

}