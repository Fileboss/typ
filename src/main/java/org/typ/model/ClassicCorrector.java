package org.typ.model;

import org.typ.view.ViewClassicMode;
import org.typ.view.ViewMode;

import java.io.FileNotFoundException;
import java.util.List;

public class ClassicCorrector extends AbstractCorrector{


    /**
     * Initialise un correcteur avec pour première position 0,
     * les listes de mots correctes et incorrectes vides.
     *
     * @param classicTextGenerator le texte qui permet d'évaluer les mots à vérifier
     */
    public ClassicCorrector(ClassicTextGenerator classicTextGenerator, ViewMode view) throws FileNotFoundException {
        super(classicTextGenerator, view);

    }

    @Override
    public void evaluateWord(String word) throws EndOfTextException {
        if (positionCurrentWord >= text.size()){
            throw new EndOfTextException(positionCurrentWord);
        }

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

        // Notifier la vue avec les informations nécessaires
        Struct data = new Struct(getText(), positionCurrentWord,
                correctWordsPosition, incorrectWordsPosition,
                correctWordsPosition.size(), incorrectWordsPosition.size(), this.positionFirstTypo, this.positionLastCorrectCharacter);
        setChanged();
        notifyObservers(data);

    }

    @Override
    public void evaluateCharacters(String partialWord) {
        // Cas où rien est écrit
        if(partialWord.length() == 0){
            this.positionFirstTypo = -1;
            this.positionLastCorrectCharacter = -1;
        }
        // Cas où le mot écrit est (pour le moment) correct
        // On met à jour l'indice du dernier caractère correct
        else if (getText().get(positionCurrentWord).startsWith(partialWord)){
            stats.incrementCorrectInput();
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

        //Notifier la vue avec les informations nécessaires
        Struct data = new Struct(getText(), positionCurrentWord,
                correctWordsPosition, incorrectWordsPosition,
                correctWordsPosition.size(), incorrectWordsPosition.size(), positionFirstTypo, positionLastCorrectCharacter);

        setChanged();
        notifyObservers(data);

    }

    @Override
    public boolean isGameOver() {
        return positionCurrentWord == getText().size();
    }


    @Override
    public void initialize() {
        super.initialize();
        stats = new ClassicStatistics();
    }

}
