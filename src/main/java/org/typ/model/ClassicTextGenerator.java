package org.typ.model;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ClassicTextGenerator {

    private String filePath;

    private Integer numberOfWords;

    private Integer numberToSplit;

    private final CSVParser parser;

    private List<String[]> fileAsList;

    public ClassicTextGenerator(String filePath, Integer numberOfWords, Integer numberToSplit) {
        this.filePath = filePath;
        this.numberOfWords = numberOfWords;
        this.numberToSplit = numberToSplit;
        this.parser = new CSVParserBuilder()
                .withSeparator('\n')
                .withIgnoreQuotations(true)
                .build();
    }

    public List<String> generateText() throws FileNotFoundException {
        try(CSVReader reader = new CSVReaderBuilder(
                new FileReader(this.filePath))
                .withCSVParser(this.parser)   // custom CSV parser
                .build()){
            for (int i = 0; i < this.numberOfWords; i++) {
                this.fileAsList.add(reader.readNext());
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        List<String> textToShuffle = Arrays.asList(fileAsList.get(0));
        Collections.shuffle(textToShuffle);
        if (this.numberToSplit > 0) {
            List<String> text = textToShuffle.subList(0, this.numberToSplit);
            return text;
        }
        return textToShuffle;
    }

}