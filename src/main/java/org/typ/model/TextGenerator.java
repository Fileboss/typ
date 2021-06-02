package org.typ.model;

import java.io.FileNotFoundException;
import java.util.List;

public interface TextGenerator {

    List<String> generateText() throws FileNotFoundException;
}
