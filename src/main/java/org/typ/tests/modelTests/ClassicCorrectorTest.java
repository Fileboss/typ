package org.typ.tests.modelTests;

import org.junit.Before;
import org.junit.Test;
import org.typ.model.ClassicCorrector;
import org.typ.model.EndOfTextException;
import org.typ.model.Statistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ClassicCorrectorTest {

    private ClassicCorrector corrector;
    private String text = "Bonjour je suis le test de la classe ClassicCorrector";
    private List<String> textList;

    @Before
    public void setUp() throws Exception {
        textList = Arrays.asList(text.split(" "));
        corrector = new ClassicCorrector(textList);
    }

    @Test
    /**
     * Test Case : On doit retrouver la liste de mot original
     */
    public void getTextTest() {
        // CONDITIONS DU TEST

        // CHECK EXCEPTION

        // EXECUTION DU TEST
        List<String> result = corrector.getText();

        // CHECK DU RESULTAT
        assertEquals(textList, result);
    }

    @Test
    /**
     * Test Case : Le nombre de mot renvoyé doit correpondre on nombre de mot du texte de départ
     */
    public void getNbWordsTest() {
        // CONDITIONS DU TEST
        int nbExpectedWords = text.split(" ").length;

        // CHECK EXCEPTION

        // EXECUTION DU TEST
        int nbResultWord = corrector.getNbWords();

        // CHECK DU RESULTAT
        assertEquals(nbExpectedWords, nbResultWord);
    }

    @Test
    /**
     * Test Case : La position doit correpsondre, en l'occurence 0 quand on initialise le corrector
     */
    public void getPositionCurrentWordTest() {
        // CONDITIONS DU TEST
        int expectedPos = 0; // When starting

        // CHECK EXCEPTION

        // EXECUTION DU TEST
        int resultPos = corrector.getPositionCurrentWord();

        // CHECK DU RESULTAT
        assertEquals(expectedPos, resultPos);
    }

    @Test
    /**
     * Test Case : Test quand on valide plusieurs mots, les stats doivent bien correspondre
     */
    public void getStatsTest() throws EndOfTextException {
        // CONDITIONS DU TEST
        corrector.evaluateWord("Bonjour");
        corrector.nextWord();
        corrector.evaluateWord("jea");
        corrector.nextWord();
        corrector.evaluateWord("suis");
        corrector.nextWord();
        corrector.evaluateWord("la");
        corrector.nextWord();
        corrector.evaluateWord("stte");
        corrector.nextWord();

        int expectedNbWrongWords = 3;
        int expectedNbRightWords = 2;

        // CHECK EXCEPTION

        // EXECUTION DU TEST
        Statistics resultStats = corrector.getStats();

        // CHECK DU RESULTAT
        assertEquals(expectedNbRightWords, resultStats.getNbCorrectWords());
        assertEquals(expectedNbWrongWords, resultStats.getNbIncorrectWords());
    }

    @Test
    /**
     * Test Case : On évalue des mots correctes et incorrectes,
     * les stats et les positions doivent correspondre.
     */
    public void evaluateWordTest() throws EndOfTextException {
        // CONDITIONS DU TEST
        int expectedNbWrongWords = 3;
        int expectedNbRightWords = 2;
        List<Integer> posCorrectWords = new ArrayList<>();
        posCorrectWords.add(0);
        posCorrectWords.add(2);

        List<Integer> posIncorrectWords = new ArrayList<>();
        posIncorrectWords.add(1);
        posIncorrectWords.add(3);
        posIncorrectWords.add(4);
        // CHECK EXCEPTION

        // EXECUTION DU TEST
        corrector.evaluateWord("Bonjour");
        corrector.nextWord();
        corrector.evaluateWord("jea");
        corrector.nextWord();
        corrector.evaluateWord("suis");
        corrector.nextWord();
        corrector.evaluateWord("la");
        corrector.nextWord();
        corrector.evaluateWord("stte");
        corrector.nextWord();

        // CHECK DU RESULTAT
        Statistics resultStats = corrector.getStats();
        assertEquals(expectedNbRightWords, resultStats.getNbCorrectWords());
        assertEquals(expectedNbWrongWords, resultStats.getNbIncorrectWords());
        assertEquals(posCorrectWords, corrector.getCorrectWordsPosition());
        assertEquals(posIncorrectWords, corrector.getIncorrectWordsPosition());
    }

    @Test
    /**
     * Test Case : Pour le cas normal
     */
    public void nextWordTest_01() throws EndOfTextException {
        // CONDITIONS DU TEST
        int expectedPos = 1;

        // CHECK EXCEPTION

        // EXECUTION DU TEST
        corrector.nextWord();

        // CHECK DU RESULTAT
        assertEquals(expectedPos, corrector.getPositionCurrentWord());
    }

    @Test
    /**
     * Test Case : Pour le cas d'exception lorsque l'on à déjà atteint la fin du text
     */
    public void nextWordTest_02() throws EndOfTextException {
        // CONDITIONS DU TEST
        for(int i = 0; i < textList.size() - 1; i++){
            corrector.nextWord();
        }

        // CHECK EXCEPTION
        assertThrows(EndOfTextException.class, () -> corrector.nextWord());

        // EXECUTION DU TEST

        // CHECK DU RESULTAT
    }

    @Test
    /**
     * Test Case : Quand la partie n'est pas terminé renvoie faux
     */
    public void isGameOverTest_01() throws EndOfTextException {
        // CONDITIONS DU TEST
        for(int i = 0; i < textList.size() - 2; i++){
            corrector.nextWord();
        }

        // CHECK EXCEPTION

        // EXECUTION DU TEST
        boolean resultGameOver = corrector.isGameOver();

        // CHECK DU RESULTAT
        assertFalse(resultGameOver);
    }

    @Test
    /**
     * Test Case : Quand la partie est terminé renvoie vrai
     */
    public void isGameOverTest_02() throws EndOfTextException {
        // CONDITIONS DU TEST
        for(int i = 0; i < textList.size() - 1; i++){
            corrector.nextWord();
        }

        // CHECK EXCEPTION

        // EXECUTION DU TEST
        boolean resultGameOver = corrector.isGameOver();

        // CHECK DU RESULTAT
        assertTrue(resultGameOver);
    }
}