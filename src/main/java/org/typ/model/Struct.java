package org.typ.model;

import java.util.List;

/**
 * Classe utilisé par le modèle pour transmettre ses informations à la vue.
 */
public class Struct {

    /* Texte, stocké sous forme de tableau de mots */
    private List<String> text;

    /* Position du mot courant */
    protected int position;

    /* Listes des indices des mots valides et des mots erronés */
    private List<Integer> correctList, falseList;


    private int positionFirstTypo, positionLastCorrectCharacter;

    /**
     * Contruit une Struct
     * @param text : le texte
     * @param position : position du mot courant
     * @param correctList : liste des indices des mots valdies
     * @param falseList : liste des indices des mots erronés
     */
    public Struct(List<String> text, int position, List<Integer> correctList, List<Integer> falseList, int positionFirstTypo, int positionLastCorrectCharacter) {
        this.text = text;
        this.position = position;
        this.correctList = correctList;
        this.falseList = falseList;
        this.positionFirstTypo = positionFirstTypo;
        this.positionLastCorrectCharacter = positionLastCorrectCharacter;
    }

    public Struct(Struct copie){
        this(copie.text,
                copie.position,
                copie.correctList,
                copie.falseList,
                copie.positionFirstTypo,
                copie.positionLastCorrectCharacter);
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

    public int getPositionFirstTypo() {
        return positionFirstTypo;
    }

    public int getPositionLastCorrectCharacter() {
        return positionLastCorrectCharacter;
    }
}
