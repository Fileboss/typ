package org.typ.controller.menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

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
    }
}
