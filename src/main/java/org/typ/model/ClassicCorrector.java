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
        super(new SimpleTimedStatistics(), classicTextGenerator);
    }

    @Override
    protected void wordEvaluationProcess(String word) {
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
    protected void characterEvaluationProcess(String partialWord) {
        // On tape u nouveau caractère
        if (partialWord.length() - 1 > currentWord.getPositionLastEvaluatedCharacter()){
            // Le caractère est bon
            if (currentWord.evaluateWord(partialWord)){
                stats.incrementNbCorrectInputs();
            }
            // Le caractère est mauvais
            else{
                stats.incrementNbIncorrectInputs();
            }
        }
        // On fait un retour arrière
        else{
            // On évalue le mot mais on fait rien avec
            currentWord.evaluateWord(partialWord);
        }
    }

    @Override
    public boolean isGameOver() {
        return positionCurrentWord == getText().size();
    }

    @Override
    public Statistics getStats(){
        return new SimpleTimedStatisticsProxy(stats);
    }

}
