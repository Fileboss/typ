package org.typ.tests.modelTests;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.typ.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ClassicCorrectorTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    public static ClassicTextGenerator ctg;
    public static List<String> text;

    public ClassicCorrector corrector;

    @BeforeClass
    public static void init() throws Exception {
        ctg = new ClassicTextGenerator("src/main/resources/mots_courants_en.csv", 50, 50);
        text = ctg.generateText();
    }

    @Before
    public void setUp() throws Exception {
        corrector = new ClassicCorrector(ctg);
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
        assertTrue(text.containsAll(result));
    }

    @Test
    /**
     * Test Case : Le nombre de mot renvoyé doit correpondre on nombre de mot du texte de départ
     */
    public void getNbWordsTest() {
        // CONDITIONS DU TEST
        int nbExpectedWords = text.size();

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
    public void getStatsTest() throws GameOverException {
        // CONDITIONS DU TEST
        corrector.evaluateWord(corrector.getText().get(0));
        corrector.nextWord();
        corrector.evaluateWord(corrector.getText().get(1) + "grw");
        corrector.nextWord();
        corrector.evaluateWord(corrector.getText().get(2));
        corrector.nextWord();
        corrector.evaluateWord(corrector.getText().get(3) + "s");
        corrector.nextWord();
        corrector.evaluateWord(corrector.getText().get(3)); // Le 3 est normal
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
    public void evaluateWordTest_01() throws GameOverException {
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
        corrector.evaluateWord(corrector.getText().get(0));
        corrector.nextWord();
        corrector.evaluateWord(corrector.getText().get(1) + "grw");
        corrector.nextWord();
        corrector.evaluateWord(corrector.getText().get(2));
        corrector.nextWord();
        corrector.evaluateWord(corrector.getText().get(3) + "s");
        corrector.nextWord();
        corrector.evaluateWord(corrector.getText().get(3)); // Le 3 est normal
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
     * Test Case : On évalue un mot mais currentPosition n'existe pas dans le texte
     */
    public void evaluateWordTest_02() throws GameOverException {
        // CONDITIONS DU TEST
        for (int i = 0; i < text.size(); i++){
            corrector.nextWord();
        }

        // CHECK EXCEPTION
        exceptionRule.expect(EndOfTextException.class);

        // EXECUTION DU TEST
        corrector.evaluateWord("Test");

        // CHECK DU RESULTAT
    }

    @Test
    /**
     * Test Case : Pour le cas normal
     */
    public void nextWordTest() throws EndOfTextException {
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
     * Test Case : Quand la partie n'est pas terminé renvoie faux
     */
    public void isGameOverTest_01() throws EndOfTextException {
        // CONDITIONS DU TEST
        for(int i = 0; i < text.size() - 2; i++){
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
    public void isGameOverTest_02() {
        // CONDITIONS DU TEST
        for(int i = 0; i < text.size(); i++){
            corrector.nextWord();
        }

        // CHECK EXCEPTION

        // EXECUTION DU TEST
        boolean resultGameOver = corrector.isGameOver();

        // CHECK DU RESULTAT
        assertTrue(resultGameOver);
    }
}