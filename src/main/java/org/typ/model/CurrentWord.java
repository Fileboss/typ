package org.typ.model;

import java.beans.PropertyChangeListener;

public interface CurrentWord {
    public void addPropertyChangeListener(PropertyChangeListener pcl);

    public void removePropertyChangeListener(PropertyChangeListener pcl);

    /**
     * Compare le mot Word avec le mot courant et met à jour la position du
     * dernier bon caractère, de la première et dernière erreur.
     * @param word le mot à comparer
     */
    public void evaluateWord(String word);

    /**
     * Change de mot à évaluer, set à 0 les autres attributs.
     * @param word le nouveau mot courant
     */
    public void setWord(String word);

    /**
     * Retourne le mot courant.
     * @return word
     */
    public String getWord();

    /**
     * Retourne la position de la première erreur.
     * @return positionFirstTypo
     */
    public int getPositionFirstTypo();

    /**
     * Retourne la position du dernier caractère correcte.
     * @return positionLastCorrectCharacter
     */
    public int getPositionLastCorrectCharacter();

    /**
     * Retourne la position de la dernière erreur.
     * @return positionLastTypo
     */
    public int getPositionLastTypo();
}
