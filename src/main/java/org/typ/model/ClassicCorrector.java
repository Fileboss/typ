package org.typ.model;


import java.io.FileNotFoundException;

public class ClassicCorrector extends AbstractCorrector{


    /**
     * Initialise un correcteur avec pour première position 0,
     * les listes de mots correctes et incorrectes vides.
     *
     * @param classicTextGenerator le texte qui permet d'évaluer les mots à vérifier
     */
    public ClassicCorrector(ClassicTextGenerator classicTextGenerator) throws FileNotFoundException {
        super(new ClassicStatistics(), classicTextGenerator);
    }

    @Override
    protected void evaluateWordTreatment(String word) {
        // Si le début du mot correspond
        if(getText().get(positionCurrentWord).equals(word)){
            correctWordsPosition.add(positionCurrentWord);
            stats.incrementNbCorrectWords();
        }
        // S'il ne correspond pas
        else{
            incorrectWordsPosition.add(positionCurrentWord);
            stats.incrementNbIncorrectWords();
        }
    }

    @Override
    public void evaluateCharactersTreatment(String partialWord) {
        // Cas où rien est écrit
        if(partialWord.length() == 0){
            this.positionFirstTypo = -1;
            this.positionLastCorrectCharacter = -1;
        }
        // Cas où le mot écrit est (pour le moment) correct
        // On met à jour l'indice du dernier caractère correct
        else if (getText().get(positionCurrentWord).startsWith(partialWord)){
            stats.incrementNbCorrectInput();
            this.positionLastCorrectCharacter = partialWord.length() - 1;
            this.positionFirstTypo = -1;
        } else {
            // Cas où il y a une erreur
            // On met à jour l'indice du premier caractère faux
            for (int i = 0; i < partialWord.length(); i++){
                if (i < this.getText().get(this.positionCurrentWord).length() && partialWord.charAt(i) != this.getText().get(this.positionCurrentWord).charAt(i)) {
                    this.positionFirstTypo = i;
                    break;
                }
                if(i >= this.getText().get(this.positionCurrentWord).length()){
                    this.positionFirstTypo = i;
                    break;
                }
            }
        }
    }

    @Override
    public boolean isGameOver() {
        return positionCurrentWord == getText().size();
    }

    @Override
    public void start(){
        ((ClassicStatistics) stats).startChrono();
    }
}
