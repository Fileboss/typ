package org.typ.model;

/**
 * Implémentation des statistiques pour le mode classique
 */
public class ClassicStatistics implements Statistics{

    int nbCorrectWords;     // Nombre des mots corrects entrés par l'utilisateur
    int nbIncorrectWords;   // Nombre des mots incorrects entrés par l'utilisateur
    int nbInput;            // Nombre des entrées de l'utilisateur
    int nbCorrectInput;     // Nombre des entrées correctes de l'utilisateur

    public ClassicStatistics() {
        // Début de partie définition des variables à 0
        this.nbCorrectWords = 0;
        this.nbIncorrectWords = 0;
        this.nbInput = 0;
        this.nbCorrectInput = 0;
    }

    @Override
    public int getNbCorrectWords() {
        return this.nbCorrectWords;
    }

    @Override
    public int getNbIncorrectWords() {
        return this.nbIncorrectWords;
    }

    @Override
    public int getNbInput() {
        return this.nbInput;
    }

    @Override
    public int getNbCorrectInput() {
        return this.nbCorrectInput;
    }

    @Override
    public void incrementNbCorrectWords() {
        this.nbCorrectWords += 1;
    }

    @Override
    public void incrementNbIncorrectWords() {
        this.nbIncorrectWords += 1;
    }

    @Override
    public void incrementInput() {
        this.nbInput += 1;
    }

    @Override
    public void incrementCorrectInput() {
        this.nbCorrectInput += 1;
    }
}
