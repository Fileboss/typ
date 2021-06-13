package org.typ.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.event.ActionEvent;
import javafx.util.Duration;

/**
 * Implémentation des statistiques pour le mode classique
 */
public class SimpleStatistics implements Statistics{

    /** Décrit l'état des statistiques.
     *  RESET : les stats sont toutes à zéros
     *  STARTED : on a commencé l'évaluation et l'on peut incrémenter les stats
     *  STOPPED : on a terminé l'évaluation, les stats ne peuvent plus être modifiées
     *            jusqu'à l'appel à reset()
     */
    protected enum State{RESET, STARTED, STOPPED}

    /** Nombre des mots corrects entrés par l'utilisateur **/
    private ReadOnlyIntegerWrapper nbCorrectWords;

    /** Nombre des mots incorrects entrés par l'utilisateur **/
    private ReadOnlyIntegerWrapper nbIncorrectWords;

    /** Nombre des entrées correctes de l'utilisateur **/
    private ReadOnlyIntegerWrapper nbCorrectInputs;

    /** Nombre des entrées de l'utilisateur **/
    private ReadOnlyIntegerWrapper nbIncorrectInputs;

    /** État courant des stats **/
    protected State state;

    /** Construit des stats avec toute les valeurs à zéro.
     */
    public SimpleStatistics() {
        this.state = State.STOPPED;
        // Création des 4 attributs de base de toutes les stats
        this.nbCorrectWords = new ReadOnlyIntegerWrapper();
        this.nbIncorrectWords = new ReadOnlyIntegerWrapper();
        this.nbCorrectInputs = new ReadOnlyIntegerWrapper();
        this.nbIncorrectInputs = new ReadOnlyIntegerWrapper();

    }

    @Override
    public int getNbCorrectWords() {
        return nbCorrectWords.getValue();
    }

    @Override
    public int getNbIncorrectWords() {
        return nbIncorrectWords.getValue();
    }

    @Override
    public int getNbInputs() {
        return nbCorrectInputs.getValue() + nbIncorrectInputs.getValue();
    }

    @Override
    public int getNbCorrectInputs() {
        return nbCorrectInputs.getValue();
    }

    @Override
    public int getNbIncorrectInputs() {
        return nbIncorrectInputs.getValue();
    }

    @Override
    public void incrementNbCorrectWords() {
        if (state == State.STARTED)
        nbCorrectWords.setValue(nbCorrectWords.getValue() + 1);
    }

    @Override
    public void incrementNbIncorrectWords() {
        if (state == State.STARTED)
        nbIncorrectWords.setValue(nbIncorrectWords.getValue() + 1);
    }

    @Override
    public void incrementNbCorrectInputs() {
        if (state == State.STARTED)
        nbCorrectInputs.setValue(nbCorrectInputs.getValue() + 1);
    }

    @Override
    public void incrementNbIncorrectInputs(){
        if (state == State.STARTED)
        nbIncorrectInputs.setValue(nbIncorrectInputs.getValue() + 1);
    }

    @Override
    public void reset(){
        if (state != State.STOPPED){
            throw new UnsupportedStateException(State.STOPPED.toString(), state.toString());
        }
        // Remet à zéro les 4 attributs de base
        state = State.RESET;
        nbCorrectWords.set(0);
        nbIncorrectWords.set(0);
        nbIncorrectInputs.set(0);
        nbCorrectInputs.set(0);
    }

    @Override
    public void stop(){
        state = State.STOPPED;
    }

    @Override
    public void start(){
        if (state != State.RESET){
            throw new UnsupportedStateException(State.RESET.toString(), state.toString());
        }
        state = State.STARTED;
    }

    @Override
    public ReadOnlyIntegerProperty nbCorrectWordsProperty(){
        return nbCorrectWords.getReadOnlyProperty();
    }

    @Override
    public ReadOnlyIntegerProperty nbIncorrectWordsProperty(){
        return nbIncorrectWords.getReadOnlyProperty();
    }

    @Override
    public ReadOnlyIntegerProperty nbCorrectInputsProperty(){
        return nbIncorrectInputs.getReadOnlyProperty();
    }

    @Override
    public ReadOnlyIntegerProperty nbIncorrectInputsProperty(){
        return nbIncorrectInputs.getReadOnlyProperty();
    }

}
