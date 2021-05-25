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

    @Override
    public String getWord(int position) {
        return this.wordList.get(position);
    }

    @Override
    public List<String> getText() {
        return this.wordList;
    }
}
