package org.typ.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilisé par le modèle pour transmettre ses informations à la vue.
 */
public class Struct {

    /* Position du mot courant */
    protected int position;

    private int positionFirstTypo, positionLastCorrectCharacter;

    private List<Integer> correctWordsPosition, incorrectWordsPosition;

    /**
     * Contruit une Struct
     * @param position : position du mot courant
     */
    public Struct(int position, int positionFirstTypo, int positionLastCorrectCharacter, List<Integer> correctWordsPosition, List<Integer> incorrectWordsPosition) {
        this.position = position;
        this.positionFirstTypo = positionFirstTypo;
        this.positionLastCorrectCharacter = positionLastCorrectCharacter;
        this.correctWordsPosition = new ArrayList<>(correctWordsPosition);
        this.incorrectWordsPosition = new ArrayList<>(incorrectWordsPosition);
    }

    public Struct(Struct copy){
        this(copy.position,
                copy.positionFirstTypo,
                copy.positionLastCorrectCharacter,
                copy.correctWordsPosition,
                copy.incorrectWordsPosition);
    }

    public int getPosition() {
        return position;
    }

    public int getPositionFirstTypo() {
        return positionFirstTypo;
    }

    public int getPositionLastCorrectCharacter() {
        return positionLastCorrectCharacter;
    }

    public List<Integer> getCorrectWordsPosition() {
        return correctWordsPosition;
    }

    public List<Integer> getIncorrectWordsPosition() {
        return incorrectWordsPosition;
    }
}
