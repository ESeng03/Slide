package com.cipher;

public class HistoryItem {
    private String input;
    private String result;

    public HistoryItem(String input, String result) {
        this.input = input;
        this.result = result;
    }

    public String getInput() {
        return input;
    }

    public String getResult() {
        return result;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return input + " | " + result;
    }
}
