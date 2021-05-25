package org.typ.model;

import java.util.List;

public interface TextWrapper {

    /**
     * retourne le mot l'indice passé en paramètre dans la liste
     * @param position indice du mot que l'on veut retourner
     * @return mot à l'indice passé en paramètre
     */
    public String getWord(int position);

    /**
     * retourne la liste contenant les mots du texte
     * @return liste contenant les mots du texte
     */
    public List<String> getText();

}
