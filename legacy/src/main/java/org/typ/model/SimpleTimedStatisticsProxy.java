package org.typ.model;

import javafx.beans.property.ReadOnlyIntegerProperty;

public class SimpleTimedStatisticsProxy implements Statistics, TimedStatistics {

    /** les stats **/
    private Statistics stats;

    public SimpleTimedStatisticsProxy(Statistics stats){
        this.stats = stats;
    }

    @Override
    public int getNbCorrectWords() {
        return stats.getNbCorrectWords();
    }

    @Override
    public int getNbIncorrectWords() {
        return stats.getNbIncorrectWords();
    }

    @Override
    public int getNbInputs() {
        return stats.getNbInputs();
    }

    @Override
    public int getNbCorrectInputs() {
        return stats.getNbCorrectInputs();
    }

    @Override
    public int getNbIncorrectInputs() {
        return stats.getNbIncorrectInputs();
    }

    @Override
    public void incrementNbCorrectWords() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void incrementNbIncorrectWords() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void incrementNbCorrectInputs() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void incrementNbIncorrectInputs() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void stop() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void start() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ReadOnlyIntegerProperty nbCorrectWordsProperty() {
        return stats.nbCorrectWordsProperty();
    }

    @Override
    public ReadOnlyIntegerProperty nbIncorrectWordsProperty() {
        return stats.nbIncorrectWordsProperty();
    }

    @Override
    public ReadOnlyIntegerProperty nbCorrectInputsProperty() {
        return stats.nbCorrectInputsProperty();
    }

    @Override
    public ReadOnlyIntegerProperty nbIncorrectInputsProperty() {
        return stats.nbIncorrectInputsProperty();
    }

    @Override
    public ReadOnlyIntegerProperty timeProperty() {
        return ((TimedStatistics)stats).timeProperty();
    }

    @Override
    public int getTime() {
        return ((TimedStatistics)stats).getTime();
    }
}
