package org.typ.model;

/**
 * Définition des statistiques pour tous les modes de jeu.
 */
public interface Statistics {
    /**
     * Récupérer le nombre des mots corrects entrés par l'utilisateur.
     * @return Nombre des mots corrects entrés par l'utilisateur
     */
    int getNbCorrectWords();

    /**
     * Récupérer le nombre des mots incorrects entrés par l'utilisateur.
     * @return Nombre de mots incorrects entrés par l'utilisateur
     */
    int getNbIncorrectWords();

    /**
     * Récupérer le nombre d'entrées de l'utilisateur.
     * @return Nombre d'entrées de l'utilisateur
     */
    int getNbInput();

    /**
     * Récupérer le nombre d'entrées corrects de l'utilisateur.
     * @return Nombre d'entrées correctes de l'utilisateur
     */
    int getNbCorrectInput();

    /**
     * Incrémente le nombre des mots corrects entrés par l'utilisateur.
     */
    void incrementNbCorrectWords();

    /**
     * Incrémente le nombre des mots incorrects entrés par l'utilisateur.
     */
    void incrementNbIncorrectWords();

    /**
     * Incrémente le nombre des entrées de l'utilisateur.
     */
    void incrementNbInput();

    /**
     * Incrémente le nombre des entrées correctes de l'utilisateur.
     */
    void incrementNbCorrectInput();

    /**
     * Remet les statistics dans un état initial
     */
    void reset();

}
