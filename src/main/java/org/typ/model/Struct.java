package org.typ.model;

import java.util.List;

/**
 * Classe utilisé par le modèle pour transmettre ses informations à la vue.
 */
public class Struct {

    private List<String> text;
    private int position;
    private List<Integer> correctList, falseList;
    private int nbCorrectTotal, nbFalseTotal;

    public Struct(List<String> texte, int position, List<Integer> correctList, List<Integer> falseList, int nbCorrectTotal, int nbFalseTotal) {
        this.text = texte;
        this.position = position;
        this.correctList = correctList;
        this.falseList = falseList;
        this.nbCorrectTotal = nbCorrectTotal;
        this.nbFalseTotal = nbFalseTotal;
    }

    public List<String> getText() {
        return text;
    }

    public int getPosition() {
        return position;
    }

    public List<Integer> getCorrectList() {
        return correctList;
    }

    public List<Integer> getFalseList() {
        return falseList;
    }

    public int getNbCorrectTotal() {
        return nbCorrectTotal;
    }

    public int getNbFalseTotal() {
        return nbFalseTotal;
    }
}
