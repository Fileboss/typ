package org.typ.model;

public class EndOfTextException extends GameOverException{
    public EndOfTextException(int index) {
        super("Le mot d'indice " + index + " n'existe pas");
    }
}
