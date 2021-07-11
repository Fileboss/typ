package org.typ.model;

public interface CurrentWord {

    /**
     * Compare le mot Word avec le mot courant et met à jour la position du
     * dernier bon caractère, de la première et dernière erreur.
     * @param partialWord le mot à comparer
     * @return True si partial word est identique au début du mot courant sinon False
     */
    public boolean evaluateWord(String partialWord);

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
     * Retourne la position du dernier caractère évalué.
     * @return positionLastCharacter
     */
    public int getPositionLastEvaluatedCharacter();
}
