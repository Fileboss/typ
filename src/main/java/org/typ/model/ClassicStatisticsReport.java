package org.typ.model;

import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ClassicStatisticsReport {

    /**
     * Objet pour manipuler les fichiers Yaml
     */
    Yaml yaml;

    /**
     * Stream d'ecriture de fichier
     */
    FileWriter writer;

    /**
     * Contenu du fichier
     */
    Map<String, Object> data = new HashMap<String, Object>();

    public ClassicStatisticsReport(ClassicStatistics cs) {
        this.yaml = new Yaml();
        try {
            this.generateFile(cs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateFile(ClassicStatistics cs) throws IOException {
        // Set writer path
        this.writer = new FileWriter("src/main/resources/org/typ/stats/stats.yml");

        // Write data
        this.data.put("ClassicMode", "");
        this.data.put("CorrectWords", cs.getNbCorrectWords());
        this.data.put("IncorrectWords", cs.getNbIncorrectWords());
        this.data.put("NumberInputs", cs.getNbInput());
        this.data.put("NumberCorrectInput", cs.getNbCorrectInput());
        this.data.put("WPM", (cs.getNbCorrectInput() / 5)/60);
        this.data.put("Personal Best", cs.getTimer());

        // Send file
        this.yaml.dump(this.data, this.writer);
    }
}
