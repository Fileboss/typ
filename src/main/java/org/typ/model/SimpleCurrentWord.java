package org.typ.model;

public class SimpleCurrentWord implements CurrentWord {

    /** Chaîne de caractères du mot **/
    private String word;

    /** La position de la première erreur */
    private int positionFirstTypo;

    /** La position du dernier caractère évalué **/
    private int positionLastEvaluatedCharacter;

    /** La position du dernier caractère correct */
    private int positionLastCorrectCharacter;

    /**
     * Construit un mot courant avec une chaîne vide
     * pour position de première erreur 0
     * et pour position de dernier caractère correct 0.
     * @param word la chaîne de caractère du mot courant
     */
    public SimpleCurrentWord(String word){
        this.word = word;
        positionFirstTypo = -1;
        positionLastEvaluatedCharacter = -1;
        positionLastCorrectCharacter = -1;
    }

    @Override
    public boolean evaluateWord(String partialWord) {
        positionLastEvaluatedCharacter = partialWord.length() - 1;
        // Retour arrière ou rien écrit
        if(partialWord.length() - 1 < positionLastEvaluatedCharacter){
            if (positionLastEvaluatedCharacter < positionFirstTypo){
                positionFirstTypo = -1;
            }
            if (positionLastEvaluatedCharacter < positionLastCorrectCharacter){
                positionLastEvaluatedCharacter--;
            }
            return true;
        }
        // C'est un nouveau caractère
        else{
            // Cas où le mot écrit est (pour le moment) correct
            if (this.word.startsWith(partialWord)){
                positionLastCorrectCharacter = partialWord.length() - 1;
                positionFirstTypo = -1;
                return true;
            }
            // Cas où il y a une erreur
            else{
                // On met à jour l'indice du premier caractère faux
                for (int i = 0; i < partialWord.length(); i++){
                    if (i > word.length()-1){
                        positionFirstTypo = i;
                        break;
                    }
                    if(partialWord.charAt(i) != this.word.charAt(i)){
                        positionFirstTypo = i;
                    }
                }
            }
        }
        return false;
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
    public int getPositionLastEvaluatedCharacter() {
        return positionLastEvaluatedCharacter;
    }
}
