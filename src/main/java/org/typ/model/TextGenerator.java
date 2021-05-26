package org.typ.model;

import java.io.FileNotFoundException;
import java.util.List;

public interface TextGenerator<T> {

    public List<T> generateText() throws FileNotFoundException;
}
