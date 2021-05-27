package org.typ.model;

import org.typ.view.ViewClassicMode;
import org.typ.view.ViewMode;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/** Représente un arbitre/correcteur qui va valider ou
 * non les mots qu'on lui donne par rapport à un texte.
 *
 */
public abstract class AbstractCorrector extends Observable {
    /** La position du mot en cours d'évaluation. */
    protected int positionCurrentWord;

    /** Liste des positions des mots correctements saisies. */
    protected List<Integer> correctWordsPosition;

    /** Liste des positions des mots incorrectements saisies. */
    protected List<Integer> incorrectWordsPosition;

    /** Le texte complet qui permet d'évaluer si les mots donnés sont correctes ou non */
    //protected TextWrapper textWrapper;

    protected List<String> text;

    protected TextGenerator textGenerator;

    /** Les statistiques concernant l'évaluation d'une partie */
    protected Statistics stats;

    /** Initialise un correcteur avec pour première position 0,
     * les listes de mots correctes et incorrectes vides.
     *
     * @param textGenerator le texte qui permet d'évaluer les mots à vérifier
     */
    public AbstractCorrector(TextGenerator textGenerator, ViewClassicMode view){

        // Initialisation des attributs
        /*positionCurrentWord = 0;
        correctWordsPosition = new ArrayList<>();
        incorrectWordsPosition = new ArrayList<>();
        textWrapper = new ClassicTextWrapper(text);
        stats = new ClassicStatistics();
        this.addObserver(view);

        Struct data = new Struct(getText(), positionCurrentWord,
                correctWordsPosition, incorrectWordsPosition,
                correctWordsPosition.size(), incorrectWordsPosition.size());
        setChanged();
        notifyObservers(data);*/
        this.addObserver(view);
        this.textGenerator = textGenerator;

        this.initialize();


    }

    /** Renvoie la liste de mot du textWrapper.
     *
     * @return le text du TextWrapper
     */
    public List<String> getText(){
        return text;
    }

    /** Retourne le nombre de mots dans le text complet.
     *
     * @return le nombre de mot du textWrapper
     */
    public int getNbWords(){
        return getText().size();
    }

    public TextGenerator getTextGenerator() {
        return textGenerator;
    }

    /** Retourne la position du mot en cours d'évaluation.
     *
     * @return la position du mot courant
     */
    public int getPositionCurrentWord() {
        return positionCurrentWord;
    }

    /** Retourne les statistiques concerant la partie
     *
     * @return les statistiques
     */
    public Statistics getStats() {
        return stats;
    }

    /** Retourne la liste des position des mots correctes
     *
     * @return correctWordPosition
     */
    public List<Integer> getCorrectWordsPosition() {
        return correctWordsPosition;
    }

    /** Retourne la liste des positions des mots incorrectes
     *
     * @return incorrectWordsPosition
     */
    public List<Integer> getIncorrectWordsPosition() {
        return incorrectWordsPosition;
    }

    /** Evalue le mot word avec le mot correpondant à la position pos dans le textWrapper.
     *
     * @param word le mot à évaluer
     */
    public abstract void evaluateWord(String word);

    /** Passe au mot suivant.
     *
     * @throws EndOfTextException si on a déjà atteint la fin du texte
     */
    public void nextWord() throws EndOfTextException{
        if(positionCurrentWord + 1 >= getText().size()){
            throw new EndOfTextException();
        }
        positionCurrentWord++;
        Struct data = new Struct(getText(), positionCurrentWord,
                correctWordsPosition, incorrectWordsPosition,
                correctWordsPosition.size(), incorrectWordsPosition.size());
        setChanged();
        notifyObservers(data);
    }

    /** Indique si la partie est terminé ou pas.
     *
     * @return vrai si la partie est terminé et faux sinon
     */
    public abstract boolean isGameOver();

    public void initialize() {
        positionCurrentWord = 0;
        correctWordsPosition = new ArrayList<>();
        incorrectWordsPosition = new ArrayList<>();
        try {
            text = textGenerator.generateText();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void start() {
        Struct data = new Struct(getText(), positionCurrentWord,
                correctWordsPosition, incorrectWordsPosition,
                correctWordsPosition.size(), incorrectWordsPosition.size());
        setChanged();
        notifyObservers(data);
    }


}