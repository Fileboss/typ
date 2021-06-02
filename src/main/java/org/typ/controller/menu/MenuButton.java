package org.typ.controller.menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

public class MenuButton extends Button {

    public MenuButton(String title, Command command) {
        this.setText(title);

        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                command.execute();
            }
        });

        this.setMinHeight(39.0);
        this.setMinWidth(182.0);

        this.setBackground(new Background(new BackgroundFill(Color.rgb(35,82,126), null, null)));
        this.setTextFill(Color.rgb(197, 176, 40));

        this.setOnMouseEntered(this::mouseEnteredListener);
        this.setOnMouseExited(this::mouseExitedListener);
    }

    private void mouseEnteredListener(MouseEvent e ) {
        setBackground(new Background(new BackgroundFill(Color.rgb(47,114,176), null, null)));
    }

    private void mouseExitedListener(MouseEvent e ) {
        setBackground(new Background(new BackgroundFill(Color.rgb(35,82,126), null, null)));
    }

}
