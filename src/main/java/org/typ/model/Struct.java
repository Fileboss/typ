package org.typ.model;

import java.util.List;

/**
 * Classe utilisé par le modèle pour transmettre ses informations à la vue.
 */
public class Struct {

    /* Texte, stocké sous forme de tableau de mots */
    private List<String> text;

    /* Position du mot courant */
    private int position;

    /* Listes des indices des mots valides et des mots erronés */
    private List<Integer> correctList, falseList;

    /* Nombres de mots correct / faux totaux */
    private int nbCorrectTotal, nbFalseTotal;

    /**
     * Contruit une Struct
     * @param text : le texte
     * @param position : position du mot courant
     * @param correctList : liste des indices des mots valdies
     * @param falseList : liste des indices des mots erronés
     * @param nbCorrectTotal : nombre total de mots correct durant la partie
     * @param nbFalseTotal : nombre total des mots erronés durant la partie
     */
    public Struct(List<String> text, int position, List<Integer> correctList, List<Integer> falseList, int nbCorrectTotal, int nbFalseTotal) {
        this.text = text;
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
