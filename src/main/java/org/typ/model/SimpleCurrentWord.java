package org.typ.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SimpleCurrentWord implements CurrentWord {

    /** Chaîne de caractères du mot **/
    private String word;

    /** La position de la première erreur */
    private int positionFirstTypo;

    /** La position de la dernière erreur **/
    private int positionLastTypo;

    /** La position du dernier caractère correct */
    private int positionLastCorrectCharacter;

    /** S'occupe de notifier la vue  **/
    private final PropertyChangeSupport support;

    /**
     * Construit un mot courant avec une chaîne vide
     * pour position de première erreur 0
     * et pour position de dernier caractère correct 0.
     * @param word la chaîne de caractère du mot courant
     */
    public SimpleCurrentWord(String word){
        this.support = new PropertyChangeSupport(this);
        setWord(word);
    }

    @Override
    public final void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    @Override
    public final void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    @Override
    public void evaluateWord(String word) {
        for (int i = 0; i < word.length(); i++){
            // Le caractère est correcte
            if (word.charAt(i) == this.word.charAt(i)){
                positionLastCorrectCharacter = i;
            }
            else{
                
            }
        }
    }

    @Override
    public void setWord(String word){
        this.word = word;
        positionFirstTypo = -1;
        positionLastTypo = -1;
        positionLastCorrectCharacter = -1;
    }

    @Override
    public String getWord() {
        return word;
    }

    @Override
    public int getPositionFirstTypo() {
        return positionFirstTypo;
    }

    @Override
    public int getPositionLastCorrectCharacter() {
        return positionLastCorrectCharacter;
    }

    @Override
    public int getPositionLastTypo() {
        return positionLastTypo;
    }
}
