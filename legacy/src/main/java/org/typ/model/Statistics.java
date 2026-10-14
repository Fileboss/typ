package org.typ.model;

import javafx.beans.property.ReadOnlyIntegerProperty;

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
    int getNbInputs();

    /**
     * Récupérer le nombre d'entrées corrects de l'utilisateur.
     * @return Nombre d'entrées correctes de l'utilisateur
     */
    int getNbCorrectInputs();

    /**
     * Retourne le nombre d'input incorrecte entrées par l'utilisateur.
     * @return la valeur de nbIncorrectInputs
     */
    int getNbIncorrectInputs();

    /**
     * Incrémente le nombre des mots corrects entrés par l'utilisateur.
     */
    void incrementNbCorrectWords();

    /**
     * Incrémente le nombre des mots incorrects entrés par l'utilisateur.
     */
    void incrementNbIncorrectWords();

    /**
     * Incrémente le nombre des entrées correctes de l'utilisateur.
     */
    void incrementNbCorrectInputs();

    /**
     * Incrémente le nombre des entrées incorrectes de l'utilisateur.
     */
    void incrementNbIncorrectInputs();

    /**
     * Remet les statistics dans un état initial
     */
    void reset();

    /**
     * Stop l'enregistrement des stats.
     * Toutes les opérations d'incrémentations sont désactivés
     */
    void stop();

    /**
     * Commence l'enregistrement des stats
     * Toutes les opérations de modifications des stats sont activées
     */
    void start();

    /**
     * Retourne la propriété enveloppant la valeur de nbCorrectWords en lecture seul.
     * @return nbCorrectWordsProperty en lecture seul
     */
    ReadOnlyIntegerProperty nbCorrectWordsProperty();

    /**
     * Retourne la propriété enveloppant la valeur de nbIncorrectWords en lecture seul.
     * @return nbIncorrectWordsProperty en lecture seul
     */
    ReadOnlyIntegerProperty nbIncorrectWordsProperty();

    /**
     * Retourne la propriété enveloppant la valeur de nbCorrectInputs en lecture seul.
     * @return  nbCorrectInputProperty en lecture seul
     */
    ReadOnlyIntegerProperty nbCorrectInputsProperty();

    /**
     * Retourne la propriété enveloppant la valeur de nbIncorrectInputs en lecture seul.
     * @return nbIncorrectInputsProperty en lecture seul
     */
    ReadOnlyIntegerProperty nbIncorrectInputsProperty();

}
