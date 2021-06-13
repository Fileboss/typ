package org.typ.model;

public class UnsupportedStateException extends RuntimeException {

    public UnsupportedStateException(String expected, String current){
        super("Cette opération n'est pas disponible dans l'état courant : " + current +
                "\n" + "L'état suivant est nécessaire : " + expected);
    }
}
