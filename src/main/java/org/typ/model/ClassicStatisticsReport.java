package org.typ.model;

import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ClassicStatisticsReport {

    public static void generateFile(Statistics cs) throws IOException {
        ClassicStatistics statsToGenerate = (ClassicStatistics) cs;

        // Set writer path
        Yaml yaml = new Yaml();
        FileWriter writer = new FileWriter("src/main/resources/org/typ/stats/stats.yml");

        // Write data
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("ClassicMode", "");
        data.put("CorrectWords", statsToGenerate.getNbCorrectWords());
        data.put("IncorrectWords", statsToGenerate.getNbIncorrectWords());
        data.put("NumberInputs", statsToGenerate.getNbInput());
        data.put("NumberCorrectInput", statsToGenerate.getNbCorrectInput());
        data.put("WPM", (statsToGenerate.getNbCorrectInput() / 5)/60);
       // data.put("Personal Best", statsToGenerate.getTimer());

        // Send file
        yaml.dump(data, writer);
    }
}
