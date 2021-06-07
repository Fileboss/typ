package org.typ.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Implémentation des statistiques pour le mode classique
 */
public class ClassicStatistics implements Statistics{

    ReadOnlyIntegerWrapper nbCorrectWords;     // Nombre des mots corrects entrés par l'utilisateur
    ReadOnlyIntegerWrapper nbIncorrectWords;   // Nombre des mots incorrects entrés par l'utilisateur
    ReadOnlyIntegerWrapper nbInput;            // Nombre des entrées de l'utilisateur
    ReadOnlyIntegerWrapper nbCorrectInput;     // Nombre des entrées correctes de l'utilisateur

    public ClassicStatistics() {
        // Début de partie définition des variables à 0
        this.nbCorrectWords = new ReadOnlyIntegerWrapper();
        this.nbIncorrectWords = new ReadOnlyIntegerWrapper();
        this.nbInput = new ReadOnlyIntegerWrapper();
        this.nbCorrectInput = new ReadOnlyIntegerWrapper();
    }

    @Override
    public void reset(){
        this.nbCorrectWords.set(0);
        this.nbIncorrectWords.set(0);
        this.nbInput.set(0);
        this.nbCorrectInput.set(0);
    }

    @Override
    public int getNbCorrectWords() {
        return this.nbCorrectWords.getValue();
    }

    public ReadOnlyIntegerProperty nbCorrectWordsProperty(){
        return this.nbCorrectWords.getReadOnlyProperty();
    }

    @Override
    public int getNbIncorrectWords() {
        return this.nbIncorrectWords.getValue();
    }

    public ReadOnlyIntegerProperty nbIncorrectWordsProperty(){
        return this.nbIncorrectWords.getReadOnlyProperty();
    }

    @Override
    public int getNbInput() {
        return this.nbInput.getValue();
    }

    public ReadOnlyIntegerProperty nbInputProperty(){
        return this.nbInput.getReadOnlyProperty();
    }

    @Override
    public int getNbCorrectInput() {
        return this.nbCorrectInput.getValue();
    }

    public ReadOnlyIntegerProperty nbCorrectInputProperty(){
        return this.nbInput.getReadOnlyProperty();
    }

    @Override
    public void incrementNbCorrectWords() {
        this.nbCorrectWords.setValue(nbCorrectWords.getValue() + 1);
    }

    @Override
    public void incrementNbIncorrectWords() {
        this.nbIncorrectWords.setValue(nbIncorrectWords.getValue() + 1);
    }

    @Override
    public void incrementNbInput() {
        this.nbInput.setValue(nbInput.getValue() + 1);
    }

    @Override
    public void incrementNbCorrectInput() {
        this.nbCorrectInput.setValue(nbCorrectInput.getValue() + 1);
    }
}
