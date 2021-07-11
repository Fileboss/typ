package org.typ.model;

public class CurrentWordProxy implements CurrentWord{

    /** l'objet sur lequel s'applique le proxy **/
    private CurrentWord currentWord;

    public CurrentWordProxy(CurrentWord currentWord){
        this.currentWord = currentWord;
    }

    @Override
    public boolean evaluateWord(String word) {
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
    public int getPositionLastEvaluatedCharacter() {
        return currentWord.getPositionLastEvaluatedCharacter();
    }
}
