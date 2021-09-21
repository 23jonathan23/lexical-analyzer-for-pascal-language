package src.domain.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import src.domain.core.Token;

public class LexicalAnalyzer {
    private static final Pattern regexTokens = Pattern.compile("([0-9]+)|([a-zA-Z]+)|([=''~!@#$%^&*()_+{}\\[\\]:;,.<>\\/?-])|\"([^\"]*)\"");

    private Properties configuration;
    
    public LexicalAnalyzer(Properties configuration) {
        this.configuration = configuration;
    }
    
    public void execute() {
        var tokens = getTokens();

        var tokenWrapper = new Token();

        tokenWrapper.mapTokens(tokens);

        var tokensResult = tokenWrapper.getTokensResult();

        for(var token : tokensResult)
            System.out.println(token.toString());
    }

    private List<String> getTokens() {
        try {
            var file = new File(configuration.getProperty("file_path"));

            var br = new BufferedReader(new FileReader(file));

            List<String> tokens = new ArrayList<>();

            var line = "";

            while ((line = br.readLine()) != null) {
                var regexMatcher = regexTokens.matcher(line);
            
                while (regexMatcher.find()) {
                    tokens.add(regexMatcher.group());
                }
            }

            br.close();

            tokens.removeAll(Collections.singleton(""));

            return tokens;
        } catch(Exception e) { return null; }
    }
}