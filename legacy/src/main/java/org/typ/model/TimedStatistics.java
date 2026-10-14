package org.typ.model;

import javafx.beans.property.ReadOnlyIntegerProperty;

/**
 * Définition des réquêtes et commandes nécessaires
 * pour enregistrer le temps passé lors d'une évaluation.
 */
public interface TimedStatistics {

    /**
     * Retourne la propriété enveloppant la valeur de time.
     * @return la propriété en lecture seul enveloppant time
     */
    ReadOnlyIntegerProperty timeProperty();

    /**
     * Retourne le temps passer depuis le début de l'évaluation.
     * @return la valeur de time
     */
    int getTime();
}
