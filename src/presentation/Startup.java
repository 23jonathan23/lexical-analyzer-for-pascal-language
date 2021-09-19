package src.presentation;

import src.domain.application.LexicalAnalyzer;

public class Startup {
    public static void main(String[] args) {
        var config = Configuration.getProperties();

        var program = new LexicalAnalyzer (config);

        program.execute();
    }
}
