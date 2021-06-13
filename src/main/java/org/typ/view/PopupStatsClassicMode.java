package org.typ.view;

import javafx.scene.control.Label;
import javafx.stage.Popup;
import org.typ.model.Statistics;
import org.typ.model.TimedStatistics;

public class PopupStatsClassicMode extends Popup {
    private Label timeLabel;

    private Label nbCorrectWordsLabel;

    private Label nbIncorrectWordsLabel;

    private Label nbCorrectInputsLabel;

    private Label nbIncorrectInputsLabel;

    public PopupStatsClassicMode(Statistics stats) {
        this.timeLabel = new Label("Temps : " + ((TimedStatistics)stats).getTime());
        this.nbCorrectWordsLabel = new Label("Correct words : " + stats.getNbCorrectWords());
        this.nbIncorrectWordsLabel = new Label("Incorrect words : " + stats.getNbIncorrectWords());
        this.nbCorrectInputsLabel = new Label("Correct inputs : " + stats.getNbCorrectInputs());
        this.nbIncorrectInputsLabel = new Label("Incorrect inputs : " + stats.getNbIncorrectInputs());

        this.getContent().add(timeLabel);
        this.getContent().add(nbCorrectWordsLabel);
        this.getContent().add(nbIncorrectWordsLabel);
        this.getContent().add(nbCorrectInputsLabel);
        this.getContent().add(nbIncorrectInputsLabel);
    }
}
