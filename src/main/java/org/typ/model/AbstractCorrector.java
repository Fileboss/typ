package org.typ.model;

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
    /** Décrit l'état du correcteur **/
    protected enum State{RESET, STARTED, STOPPED}

    /** La position du mot en cours d'évaluation. */
    protected int positionCurrentWord;

    /** La position de la première erreur */
    protected int positionFirstTypo;

    /** La position du dernier caractère correct */
    protected int positionLastCorrectCharacter;

    /** Liste des positions des mots correctements saisies. */
    protected final List<Integer> correctWordsPosition;

    /** Liste des positions des mots incorrectements saisies. */
    protected final List<Integer>  incorrectWordsPosition;

    /** Le texte complet qui permet d'évaluer si les mots donnés sont correctes ou non. */
    protected final ObservableList<String> text;

    /** C'est le générateur de texte. **/
    protected final TextGenerator textGenerator;

    /** Les statistiques concernant l'évaluation d'une partie. */
    protected final Statistics stats;

    /** S'occupe de notifier la vue  **/
    private final PropertyChangeSupport support;

    protected Struct data;

    /** État courant des statistics **/
    private State state;

    /** Initialise un correcteur avec pour première position 0,
     * les listes de mots correctes et incorrectes vides.
     *
     * @param textGenerator le texte qui permet d'évaluer les mots à vérifier
     */
    public AbstractCorrector(Statistics stats, TextGenerator textGenerator){
        this.state = State.STOPPED;
        this.support = new PropertyChangeSupport(this);
        this.textGenerator = textGenerator;
        this.stats = stats;

        this.correctWordsPosition = new ArrayList<>();
        this.incorrectWordsPosition = new ArrayList<>();
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

    /** Retourne la position du mot en cours d'évaluation.
     *
     * @return la position du mot courant
     */
    public int getPositionCurrentWord() {
        return positionCurrentWord;
    }

    /** Retourne les statistiques concernant la partie
     *
     * @return les statistiques
     */
    public abstract Statistics getStats();

    /** Retourne la liste non modifiable des position des mots correctes
     *
     * @return correctWordPosition
     */
    public List<Integer> getCorrectWordsPosition() {
        return new ArrayList<>(correctWordsPosition);
    }

    /** Retourne la liste des positions des mots incorrectes
     *
     * @return incorrectWordsPosition
     */
    public List<Integer> getIncorrectWordsPosition() {
        return new ArrayList<>(incorrectWordsPosition);
    }

    /** Evalue le mot word avec le mot correpondant à la position positionCurrentWord du texte
     * et notifie la vue associé.
     *
     * @param word le mot à évaluer
     * @throws GameOverException une exception de fin de partie
     */
    public void evaluateWord(String word) throws GameOverException{
        if (positionCurrentWord == text.size()){
            throw new EndOfTextException(positionCurrentWord);
        }
        wordEvaluationProcess(word);
    }

    /** Réalise uniquement le traitment relatif à l'évaluation d'un mot
     *
     * @param word le mot à évaluer
     * @throws GameOverException une exception de fin de partie
     */
    protected abstract void wordEvaluationProcess(String word) throws GameOverException;

    /** Evalue le mot partialWord par rapport au début du mot
     * correspond à l'indice posCurrentWord dans le texte.
     * Permet l'évaluation d'un mot caractère par caractère
     *
     * @param partialWord le mot à comparer
     */
    public final void evaluateCharacters(String partialWord){
        if(state == State.RESET){
            start();
        }
        characterEvaluationProcess(partialWord);
        Struct data = generateData();
        notifyView(data);
    }

    /** Réalise uniquement le traitment relatif à l'évaluation du début d'un mot.
     *
     * @param partialWord le mot à comparer
     */
    protected abstract void characterEvaluationProcess(String partialWord);

    /** Passe au mot suivant en incrémentant posCurrentWord de 1.
     *
     */
    public void nextWord() {
        positionCurrentWord++;
        Struct data = generateData();
        if (positionCurrentWord < text.size()){
            notifyView(data);
        }
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
        if (state != State.STOPPED){
            throw new UnsupportedStateException(State.STOPPED.toString(), state.toString());
        }
        state = State.RESET;
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
        Struct data = generateData();
        notifyView(data);
    }

    /** Démarre l'évaluation du texte.
     * Ne Peut être appelé qu'après l'appel à initialize()
     */
    private void start() {
        if (state != State.RESET){
            throw  new UnsupportedStateException(State.RESET.toString(), state.toString());
        }
        state = State.STARTED;
        stats.start();
    }

    /** Stoppe l'évaluation du texte.
     */
    public void stop(){
        state = State.STOPPED;
        stats.stop();
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
        return new Struct(positionCurrentWord,
                this.positionFirstTypo, this.positionLastCorrectCharacter, this.correctWordsPosition, this.incorrectWordsPosition);
    }
}
