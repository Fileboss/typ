package org.typ.controller;

import javafx.scene.control.TextField;

public class NoSpacetextField extends TextField {

    @Override public void replaceText(int start, int end, String text) {
        if (!text.matches("\\s")) {
            super.replaceText(start, end, text);
        }
    }
    @Override public void replaceSelection(String text) {
        if (!text.matches("\\s")) {
            super.replaceSelection(text);
        }
    }
}
