package org.typ.modele;

import org.typ.vue.VueClassicMode;

import java.util.*;

public class ModeleTest extends Observable {

    private List<String> text;
    private int currentWordIndice;
    private List<Integer> correctIndicesList, falseIndicesList;
    private int  currentTextIndice = -1;

    private int cptValidTotal, cptFalseTotal;

    public static final String TEXT1 = "Bonjour je suis Philippe le meilleur codeur de sa génération";
    public static final String TEXT2 = "Non en réalité Tetris est probablement le meilleur codeur de la planete entière";
    public static final String TEXT3 = "Ce qui est sur c'est que Pierre est le meilleur à Mario Kart";

    public ModeleTest(VueClassicMode vue) {
        text = new ArrayList<>();

        changeText();
        currentWordIndice = 0;

        cptFalseTotal = 0;
        cptValidTotal = 0;


        this.addObserver(vue);
        StringBuilder wholeText = new StringBuilder();
        for (String s : text) {
            wholeText.append(s);
            wholeText.append(" ");
        }
        setChanged();
        notifyObservers((new Struct(text, currentWordIndice, correctIndicesList, falseIndicesList, cptValidTotal, cptFalseTotal)));
    }

    /**
     * Vérifie que le mot passé en paramètre est identique au mot courant.
     * @param mot : mot à vérifier
     * @return true si le mot est identique, false dans le cas contraire
     */
    public boolean isValidWord(String mot) {
        boolean valid = mot.equals(text.get(currentWordIndice) + " ");
        if (valid) {
            cptValidTotal++;
            if (!correctIndicesList.contains(currentWordIndice)) {
                correctIndicesList.add(currentWordIndice);
            }
        } else {
            cptFalseTotal++;
            if (!falseIndicesList.contains(currentWordIndice)) {
                falseIndicesList.add(currentWordIndice);
            }
        }
        return  valid;
    }

    /**
     * Incrément l'indice du mot courant.
     */
    public void incrementIndice() {
        /* Cas d'incrémentation simple */
        if (currentWordIndice == text.size()-1) {
            changeText();
        /* Cas d'incrémentation lrosqu'on atteint la fin du texte actuel */
        } else {
            currentWordIndice ++;
            StringBuilder wholeText = new StringBuilder();
            for (String s : text) {
                wholeText.append(s);
                wholeText.append(" ");
            }
            setChanged();
            notifyObservers((new Struct(text, currentWordIndice, correctIndicesList, falseIndicesList, cptValidTotal, cptFalseTotal)));
        }
        System.out.println("Expected word : " + text.get(currentWordIndice));

    }

    /**
     * Change le texte alétoirement
     */
    public void changeText() {
        Random rnd = new Random();
        int number = -1;
        do {
            number = rnd.nextInt(2 - 0 + 1);
        } while (currentTextIndice == number);
        currentTextIndice = number;
        currentWordIndice = 0;
        correctIndicesList = new ArrayList<>();
        falseIndicesList = new ArrayList<>();

        switch (number) {
            case 0: text = Arrays.asList(TEXT1.split(" "));
                setChanged();
                notifyObservers(new Struct(text, currentWordIndice, correctIndicesList, falseIndicesList, cptValidTotal, cptFalseTotal));
                break;
            case 1: text = Arrays.asList(TEXT2.split(" "));
                setChanged();
                notifyObservers(new Struct(text, currentWordIndice, correctIndicesList, falseIndicesList, cptValidTotal, cptFalseTotal));
                break;
            case 2: text = Arrays.asList(TEXT3.split(" "));
                setChanged();
                notifyObservers(new Struct(text, currentWordIndice, correctIndicesList, falseIndicesList, cptValidTotal, cptFalseTotal));
                break;
            default: text = Arrays.asList(TEXT1.split(" ")); break;
        }
    }

    /**
     * Réinitialise le modèle et change le texte.
     */
    public void reset() {
        cptValidTotal = 0;
        cptFalseTotal = 0;
        currentWordIndice = 0;
        changeText();
    }

}
