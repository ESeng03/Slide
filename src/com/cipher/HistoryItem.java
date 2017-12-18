package com.cipher;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HistoryItem {
    private StringProperty input;
    private StringProperty result;

    public HistoryItem(String input, String result) {
        this.input = new SimpleStringProperty(input);
        this.result = new SimpleStringProperty(result);
    }

    public StringProperty inputProperty() {
        return input;
    }

    public StringProperty resultProperty() {
        return result;
    }

    public final String getInput() {
        return input.get();
    }

    public final String getResult() {
        return input.get();
    }

    public final void setInput(String input) {
        this.input.set(input);
    }

    public final void setResult(String result) {
        this.input.set(result);
    }

    @Override
    public String toString() {
        return input.get() + " | " + result.get();
    }
}
