package org.typ.model;

import java.util.List;

/**
 * Classe utilisé par le modèle pour transmettre ses informations à la vue.
 */
public class Struct {

    /* Position du mot courant */
    protected int position;

    private int positionFirstTypo, positionLastCorrectCharacter;

    /**
     * Contruit une Struct
     * @param position : position du mot courant
     */
    public Struct(int position, int positionFirstTypo, int positionLastCorrectCharacter) {
        this.position = position;
        this.positionFirstTypo = positionFirstTypo;
        this.positionLastCorrectCharacter = positionLastCorrectCharacter;
    }

    public Struct(Struct copie){
        this(copie.position,
                copie.positionFirstTypo,
                copie.positionLastCorrectCharacter);
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
}
