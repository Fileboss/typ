package org.typ.tests.modelTests;

import org.junit.Before;
import org.junit.Test;
import org.typ.model.ClassicCorrector;
import org.typ.model.PositionDoesNotExistException;
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
     * Test Case : test pour le cas nominal.
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
     * Test Case : test pour le cas nominal.
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
     * Test Case : Test pour le cas nominal
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
     * Test Case : Test for nominal case
     */
    public void getStatsTest() throws PositionDoesNotExistException {
        // CONDITIONS DU TEST
        corrector.evaluateWord("Bonjour", 0);
        corrector.evaluateWord("jea", 1);
        corrector.evaluateWord("suis", 2);
        corrector.evaluateWord("la", 3);
        corrector.evaluateWord("stte", 4);

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
    public void evaluateWordTest() {
        // CONDITIONS DU TEST

        // CHECK EXCEPTION

        // EXECUTION DU TEST

        // CHECK DU RESULTAT
    }

    @Test
    public void nextWordTest() {
        // CONDITIONS DU TEST

        // CHECK EXCEPTION

        // EXECUTION DU TEST
        //TODO

        // CHECK DU RESULTAT
    }

    @Test
    public void isGameOverTest() {
        // CONDITIONS DU TEST

        // CHECK EXCEPTION

        // EXECUTION DU TEST

        // CHECK DU RESULTAT
    }
}