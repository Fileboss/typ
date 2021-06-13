package org.typ.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class SimpleTimedStatistics extends SimpleStatistics implements TimedStatistics {

    /** Nombre de secondes écoulées depuis le début de l'évaluation
     * jusqu'à l'appel à stop().
     */
    private ReadOnlyIntegerWrapper timer;

    /** Permet d'incrémenter chaque seconde le chronometre **/
    private Timeline timerHandler;

    public SimpleTimedStatistics() {
        super();
        this.timer = new ReadOnlyIntegerWrapper();
        this.timerHandler = new Timeline(
                new KeyFrame(Duration.seconds(1), this::incrementTime));
        timerHandler.setCycleCount(Timeline.INDEFINITE);
    }

    @Override
    public void reset(){
        super.reset();
        // Remet à zéro le chronomètre
        timer.set(0);
    }

    public void stop(){
        super.stop();
        timerHandler.stop();
    }

    @Override
    public void start(){
        super.start();
        timerHandler.playFromStart();
    }

    @Override
    public ReadOnlyIntegerProperty timeProperty() {
        return timer.getReadOnlyProperty();
    }

    @Override
    public int getTime() {
        return timer.getValue();
    }

    private void incrementTime(ActionEvent event) {
        if (state == State.STARTED)
            this.timer.set(timer.getValue() +1 );
    }
}
