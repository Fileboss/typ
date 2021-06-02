package org.typ.model;

public class GameOverException extends Exception{
    public GameOverException(String reason){
        super("Fin du jeu : " + reason);
    }
}
