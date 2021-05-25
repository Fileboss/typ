package org.typ.tests.modelTests;

import org.junit.Test;
import org.typ.model.ClassicTextGenerator;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ClassicTextGeneratorTest {

    private ClassicTextGenerator classicTextGenerator1;

    @org.junit.Before
    public void setUp() throws Exception {
        classicTextGenerator1 = new ClassicTextGenerator("src/main/resources/mots_courants_fr.csv", 1500, 200);
    }



    @Test
    public void testReturn200Words() {
        try {
            assertEquals(200, classicTextGenerator1.generateText().size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReturndifferentSequences() {
        try {
            assertNotEquals(classicTextGenerator1.generateText(), classicTextGenerator1.generateText());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}