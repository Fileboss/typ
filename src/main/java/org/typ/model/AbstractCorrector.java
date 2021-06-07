package org.typ.model;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/** Représente un arbitre/correcteur qui va valider ou
 * non les mots qu'on lui donne par rapport à un texte.
 *
 */
public abstract class AbstractCorrector{
    /** La position du mot en cours d'évaluation. */
    protected int positionCurrentWord;

    /** La position de la première erreur */
    protected int positionFirstTypo;

    /** La position du dernier caractère correct */
    protected int positionLastCorrectCharacter;

    /** Liste des positions des mots correctements saisies. */
    protected ObservableList<Integer> correctWordsPosition;

    /** Liste des positions des mots incorrectements saisies. */
    protected ObservableList<Integer>  incorrectWordsPosition;

    /** Le texte complet qui permet d'évaluer si les mots donnés sont correctes ou non. */
    protected ObservableList<String> text;

    /** C'est le générateur de texte. **/
    protected TextGenerator textGenerator;

    /** Les statistiques concernant l'évaluation d'une partie. */
    protected Statistics stats;

    /** S'occupe de notifier la vue  **/
    private PropertyChangeSupport support;

    private Struct data;

    /** Initialise un correcteur avec pour première position 0,
     * les listes de mots correctes et incorrectes vides.
     *
     * @param textGenerator le texte qui permet d'évaluer les mots à vérifier
     */
    public AbstractCorrector(Statistics stats, TextGenerator textGenerator){
        this.support = new PropertyChangeSupport(this);
        this.textGenerator = textGenerator;
        this.stats = stats;

        this.correctWordsPosition = FXCollections.observableArrayList();
        this.incorrectWordsPosition = FXCollections.observableArrayList();
        this.text = FXCollections.observableArrayList();

        this.initialize();
    }

    //TODO add javadoc or refactor
    public final void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public final void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    /** Renvoie la liste de mot du textWrapper.
     *
     * @return le text du TextWrapper
     */
    public ObservableList<String> getText(){
        return FXCollections.unmodifiableObservableList(text);
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

    /** Retourne la liste non modifiable des position des mots correctes
     *
     * @return correctWordPosition
     */
    public ObservableList<Integer> getCorrectWordsPosition() {
        return FXCollections.unmodifiableObservableList(correctWordsPosition);
    }

    /** Retourne la liste des positions des mots incorrectes
     *
     * @return incorrectWordsPosition
     */
    public ObservableList<Integer> getIncorrectWordsPosition() {
        return FXCollections.unmodifiableObservableList(incorrectWordsPosition);
    }

    /** Evalue le mot word avec le mot correpondant à la position positionCurrentWord du texte
     * et notifie la vue associé.
     *
     * @param word le mot à évaluer
     * @throws GameOverException une exception de fin de partie
     */
    public void evaluateWord(String word) throws GameOverException{
        if (positionCurrentWord >= text.size()){
            throw new EndOfTextException(positionCurrentWord);
        }
        evaluateWordTreatment(word);
        Struct data = generateData();
        notifyView(data);
    }

    /** Réalise uniquement le traitment relatif à l'évaluation d'un mot
     *
     * @param word le mot à évaluer
     * @throws GameOverException une exception de fin de partie
     */
    protected abstract void evaluateWordTreatment(String word) throws GameOverException;

    /** Evalue le mot partialWord par rapport au début du mot
     * correspond à l'indice posCurrentWord dans le texte.
     * Permet l'évaluation d'un mot caractère par caractère
     *
     * @param partialWord le mot à comparer
     */
    public void evaluateCharacters(String partialWord){
        evaluateCharactersTreatment(partialWord);
        Struct data = generateData();
        notifyView(data);
    }

    /** Réalise uniquement le traitment relatif à l'évaluation du début d'un mot.
     *
     * @param partialWord le mot à comparer
     */
    public abstract void evaluateCharactersTreatment(String partialWord);

    /** Passe au mot suivant en incrémentant posCurrentWord de 1.
     *
     */
    public void nextWord() {
        positionCurrentWord++;
        Struct data = generateData();
        notifyView(data);
    }

    /** Indique si la partie est terminé ou pas.
     *
     * @return vrai si la partie est terminé et faux sinon
     */
    public abstract boolean isGameOver();

    /** Initialise le correcteur dans l'état nécessaire pour commencer l'évaluation.
     *
     */
    public void initialize() {
        positionCurrentWord = 0;
        correctWordsPosition.clear();
        incorrectWordsPosition.clear();
        positionFirstTypo = -1;
        positionLastCorrectCharacter = -1;
        stats.reset();
        try {
            text.setAll(textGenerator.generateText());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /** Démarre l'évaluation du texte et notifie la vue.
     * Ne Peut être appelé qu'après l'appel à initialize()
     */
    public void start() {
        Struct data = generateData();
        notifyView(data);
    }

    /** Notifie la vue avec les données data
     *
     * @param data les données
     */
    private void notifyView(Struct data){
        support.firePropertyChange("correctorData", this.data, data);
        this.data = data;
    }

    /** Retourne les données mises à jour de l'état courant du correcteur
     *
     * @return les données de vue
     */
    protected Struct generateData(){
        return new Struct(getText(), positionCurrentWord,
                getCorrectWordsPosition(), incorrectWordsPosition,
                this.positionFirstTypo, this.positionLastCorrectCharacter);
    }
}
