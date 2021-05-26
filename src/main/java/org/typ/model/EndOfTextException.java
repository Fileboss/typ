package org.typ.model;

public class EndOfTextException extends Exception{
    public EndOfTextException(){
        super("Vous avez atteint la fin du texte");
    }
}
