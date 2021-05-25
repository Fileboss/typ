package org.typ.model;

import java.util.List;

public class ClassicCorrector extends AbstractCorrector{
    /**
     * Initialise un correcteur avec pour première position 0,
     * les listes de mots correctes et incorrectes vides.
     *
     * @param text le texte qui permet d'évaluer les mots à vérifier
     */
    public ClassicCorrector(List<String> text) {
        super(text);
    }

    @Override
    public boolean isGameOver() {
        return positionCurrentWord + 1 == getText().size();
    }
}
