package org.typ.model;

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
    protected TextWrapper textWrapper;

    /** Les statistiques concernant l'évaluation d'une partie */
    protected Statistics stats;

    /** Initialise un correcteur avec pour première position 0,
     * les listes de mots correctes et incorrectes vides.
     *
     * @param text le texte qui permet d'évaluer les mots à vérifier
     */
    public AbstractCorrector(List<String> text){

        // Initialisation des attributs
        positionCurrentWord = 0;
        correctWordsPosition = new ArrayList<>();
        incorrectWordsPosition = new ArrayList<>();
        textWrapper = new ClassicTextWrapper(text);
        stats = new ClassicStatistics();

    }

    /** Renvoie la liste de mot du textWrapper.
     *
     * @return le text du TextWrapper
     */
    public List<String> getText(){
        return textWrapper.getText();
    }

    /** Retourne le nombre de mots dans le text complet.
     *
     * @return le nombre de mot du textWrapper
     */
    public int getNbWords(){
        return getText().size();
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

    /** Evalue le mot word avec le mot correpondant à la position pos dans le textWrapper.
     *
     * @param word le mot à évaluer
     * @param pos la position du mot correcte
     * @throws PositionDoesNotExistException si pos n'est pas compris entre 0 et getText().size() - 1
     */
    public void evaluateWord(String word, int pos) throws PositionDoesNotExistException {
        if(pos < 0 || pos >= getText().size()){
            throw new PositionDoesNotExistException();
        }

        // Si le début du mot correspond
        if(getText().get(pos).startsWith(word)){
            correctWordsPosition.add(pos);
            stats.incrementNbCorrectWords();
        }
        // S'il ne correspond pas
        else{
            incorrectWordsPosition.add(pos);
            stats.incrementNbIncorrectWords();
        }
        positionCurrentWord++;
        // Notifier la vue avec les informations nécessaires
        Struct data = new Struct(getText(), positionCurrentWord,
                correctWordsPosition, incorrectWordsPosition,
                correctWordsPosition.size(), incorrectWordsPosition.size());
        notifyObservers(data);

    }

    /** Passe au mot suivant.
     * Utile lorsque l'on évalue caratctère par caractère
     *
     */
    public void nextWord(String word, int pos){
        //TODO
    }

    /** Indique si la partie est terminé ou pas.
     *
     * @return vrai si la partie est terminé et faux sinon
     */
    public abstract boolean isGameOver();
}
