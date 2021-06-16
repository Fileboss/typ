package org.typ.model;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

public class ClassicStatisticsReport {

    public static void generateFile(Statistics cs) throws IOException {
        SimpleTimedStatisticsProxy statsToGenerate = (SimpleTimedStatisticsProxy) cs;

        // Set writer path
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        options.setIndent(2);
        Yaml yaml = new Yaml(options);
        FileWriter writer = new FileWriter("src/main/resources/org/typ/stats/stats.yml");

        // Write data
        Map<String, Object> classicData = new HashMap<String, Object>();
        classicData.put("CorrectWords", statsToGenerate.getNbCorrectWords());
        classicData.put("IncorrectWords", statsToGenerate.getNbIncorrectWords());
        classicData.put("NumberInputs", statsToGenerate.getNbInputs());
        classicData.put("NumberCorrectInput", statsToGenerate.getNbCorrectInputs());
        classicData.put("WPM", (float) statsToGenerate.getNbCorrectInputs() /60);
        classicData.put("PersonalBest", statsToGenerate.getTime());

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("ClassicMode", classicData);

        // Send file
        yaml.dump(data, writer);
    }
}
