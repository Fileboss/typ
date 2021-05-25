package org.typ.tests.modelTests;

import org.junit.Test;
import org.typ.model.ClassicTextGenerator;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;


public class ClassicTextGeneratorTest {

    private ClassicTextGenerator classicTextGenerator;

    @org.junit.Before
    public void setUp() throws Exception {
        classicTextGenerator = new ClassicTextGenerator("src/main/resources/mots_courants_fr.csv", 1500, 200);
    }



    @Test
    public void testReturn200Words() {
        try {
            assertEquals(200, classicTextGenerator.generateText().size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}