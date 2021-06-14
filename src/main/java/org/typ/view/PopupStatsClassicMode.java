package org.typ.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import org.typ.model.Statistics;
import org.typ.model.TimedStatistics;

import java.text.SimpleDateFormat;

public class PopupStatsClassicMode extends Alert {
    private String timeLabel;

    private String nbCorrectWordsLabel;

    private String nbIncorrectWordsLabel;

    private String nbCorrectInputsLabel;

    private String nbIncorrectInputsLabel;

    public PopupStatsClassicMode(Statistics stats) {
        super(Alert.AlertType.INFORMATION);
        int time = ((TimedStatistics)stats).getTime();
        String formattedTime = new SimpleDateFormat("mm:ss").format(time * 1000);
        this.timeLabel = "Temps : " + formattedTime;
        this.nbCorrectWordsLabel = "Correct words : " + stats.getNbCorrectWords();
        this.nbIncorrectWordsLabel = "Incorrect words : " + stats.getNbIncorrectWords();
        this.nbCorrectInputsLabel = "Correct inputs : " + stats.getNbCorrectInputs();
        this.nbIncorrectInputsLabel = "Incorrect inputs : " + stats.getNbIncorrectInputs();

        StringBuilder builder = new StringBuilder();
        builder.append(timeLabel + "\n");
        builder.append(nbCorrectWordsLabel + "\n");
        builder.append(nbIncorrectWordsLabel + "\n");
        builder.append(nbCorrectInputsLabel + "\n");
        builder.append(nbIncorrectInputsLabel + "\n");

        this.setTitle("Game Over");
        this.setHeaderText(null);
        this.setContentText(builder.toString());
    }
}
