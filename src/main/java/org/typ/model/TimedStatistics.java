package org.typ.model;

import javafx.beans.property.ReadOnlyIntegerProperty;

/**
 * Définition des réquêtes et commandes nécessaires
 * pour enregistrer le temps passé lors d'une évaluation.
 */
public interface TimedStatistics {

    /**
     * Retourne la propriété enveloppant la valeur de time.
     * @return la valeur de time, le temps passé depuis le début de l'évaluation
     */
    ReadOnlyIntegerProperty timeProperty();

    /**
     * Retourne le temps passer depuis le début de l'évaluation.
     * @return la valeur de time
     */
    int getTime();
}
