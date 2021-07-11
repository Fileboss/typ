package org.typ.model;

import java.beans.PropertyChangeListener;

public class CurrentWordProxy implements CurrentWord{

    /** l'objet sur lequel s'applique le proxy **/
    private CurrentWord currentWord;

    public CurrentWordProxy(CurrentWord currentWord){
        this.currentWord = currentWord;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener pcl) {

    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener pcl) {

    }

    @Override
    public void evaluateWord(String word) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setWord(String word) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getWord() {
        return currentWord.getWord();
    }

    @Override
    public int getPositionFirstTypo() {
        return currentWord.getPositionFirstTypo();
    }

    @Override
    public int getPositionLastCorrectCharacter() {
        return currentWord.getPositionLastCorrectCharacter();
    }

    @Override
    public int getPositionLastTypo() {
        return currentWord.getPositionLastTypo();
    }
}
