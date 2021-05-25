package org.typ.vue;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;

import java.util.Observable;
import java.util.Observer;

public class VueClassicModeCanvas extends StackPane implements Observer {

    Canvas canvas;


    public VueClassicModeCanvas() {
        super();

        canvas = new Canvas(600, 200);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText("Default",
                Math.round(canvas.getWidth()  / 2),
                Math.round(canvas.getHeight() / 2)
        );
        getChildren().add(canvas);

    }

    @Override
    public void update(Observable observable, Object o) {
        String text = (String) o;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.fillText(text,
                Math.round(canvas.getWidth()  / 2),
                Math.round(canvas.getHeight() / 2)
        );
        gc.strokeText("LOL", 50, 50, 88888888);
    }
}
