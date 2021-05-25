package org.typ.model;

public class PositionDoesNotExistException extends Exception {
    public PositionDoesNotExistException(){
        super("La position n'existe pas dans le texte");
    }
}
