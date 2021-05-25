package org.typ.model;

import java.util.List;

public class ClassicTextWrapper implements TextWrapper{

    /**
     * Liste qui contient tous les mots d'un texte
     */
    private List<String> wordList;

    public ClassicTextWrapper(List<String> wordList){
        this.wordList = wordList;
    }

    /**
     * retourne le mot l'indice passé en paramètre dans la liste
     * @param position indice du mot que l'on veut retourner
     * @return mot à l'indice passé en paramètre
     */
    @Override
    public String getWord(int position) {
        return this.wordList.get(position);
    }

    /**
     * retourne la liste contenant les mots du texte
     * @return liste contenant les mots du texte
     */
    @Override
    public List<String> getText() {
        return this.wordList;
    }
}
