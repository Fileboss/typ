package org.typ.model;

import java.io.FileNotFoundException;
import java.util.List;

public interface TextGenerator {

    /** Retourne une liste de String, chaque élément est un mot à évaluer.
     *
     * @return Une liste de mots
     * @throws FileNotFoundException si fichier lu n'est pas trouvé
     */
    List<String> generateText() throws FileNotFoundException;
}
