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
import src.domain.core.TokenType;
import src.domain.core.Instance.TokenSpec;

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

        boolean valid = headers0(tokensResult);

        if (valid) {
            System.out.println("Valido");
            return;
        }

        System.out.println("Invalido");
    }

    private boolean headers0(List<TokenSpec> tokens) {

        if (tokens.isEmpty()) {
            return true;
        }

        TokenSpec token = tokens.remove(0);

        if (token.getType() == TokenType.KEYWORD && token.getValue().equals("program")) {
            return headers1(tokens);
        }

        return false;
    }

    private boolean headers1(List<TokenSpec> tokens) {
        
        if (tokens.isEmpty()) {
            return false;
        }

        TokenSpec token = tokens.remove(0);

        if (token.getType() == TokenType.ID) {
            return headers2(tokens);
        }

        return false;
    }

    private boolean headers2(List<TokenSpec> tokens) {

        if (tokens.isEmpty()) {
            return false;
        }

        TokenSpec token = tokens.remove(0);

        if (token.getType() == TokenType.SYMBOL && token.getValue().equals(";")) {
            return headers3(tokens);
        }

        return false;
    }

    private boolean headers3(List<TokenSpec> tokens) {

        if (tokens.isEmpty()) {
            return true;
        }

        TokenSpec token = tokens.remove(0);

        if (token.getType() == TokenType.KEYWORD && token.getValue().equals("uses")) {
            return headers4(tokens);
        } else if (token.getType() == TokenType.KEYWORD && token.getValue().equals("var")) {
            return headerVar0(tokens);
        } else if (token.getType() == TokenType.KEYWORD && token.getValue().equals("const")) {
            //TODO
        } else if (token.getType() == TokenType.KEYWORD && token.getValue().equals("begin")) {
            //TODO
        }

        return false;
    }

    private boolean headers5(List<TokenSpec> tokens) {
        
        if (tokens.isEmpty()) {
            return true;
        }

        TokenSpec token = tokens.remove(0);

        if (token.getType() == TokenType.KEYWORD && token.getValue().equals("var")) {
            return headerVar0(tokens);
        } else if (token.getType() == TokenType.KEYWORD && token.getValue().equals("const")) {
            //TODO
        } else if (token.getType() == TokenType.KEYWORD && token.getValue().equals("begin")) {
            //TODO
        }

        return false;

    }

    private boolean headerVar0(List<TokenSpec> tokens) {

        if (tokens.isEmpty()) {
            return false;
        }

        TokenSpec token = tokens.remove(0);

        if (token.getType() == TokenType.ID) {
            return headerVar1(tokens);
        }

        return false;
    }

    private boolean headerVar1(List<TokenSpec> tokens) {

        if (tokens.isEmpty()) {
            return false;
        }

        TokenSpec token = tokens.remove(0);

        if (token.getType() == TokenType.SYMBOL && token.getValue().equals(",")) {
            return headerVar0(tokens);
        } else if (token.getType() == TokenType.SYMBOL && token.getValue().equals(":")) {
            return headerVar2(tokens);
        }

        return false;
    }

    private boolean headerVar2(List<TokenSpec> tokens) {

        if (tokens.isEmpty()) {
            return false;
        }

        TokenSpec token = tokens.remove(0);

        if (token.getType() == TokenType.KEYWORD && (
                token.getValue().equals("string") ||
                token.getValue().equals("char") ||
                token.getValue().equals("boolean") ||
                token.getValue().equals("integer") ||
                token.getValue().equals("real"))) {

            return headerVar3(tokens);
        }

        return false;
    }

    private boolean headerVar3(List<TokenSpec> tokens) {

        if (tokens.isEmpty()) {
            return false;
        }

        TokenSpec token = tokens.remove(0);

        if (token.getType() == TokenType.SYMBOL && token.getValue().equals(";")) {
            return headerVar4(tokens);
        }

        return false;
    }

    private boolean headerVar4(List<TokenSpec> tokens) {

        if (tokens.isEmpty()) {
            return true;
        }

        TokenSpec token = tokens.get(0);

        if (token.getType() == TokenType.ID) {
            return headerVar0(tokens);
        } else if (token.getType() == TokenType.KEYWORD) {
            return headers5(tokens);
        }

        return false;
    }

    private boolean headers4(List<TokenSpec> tokens) {

        if (tokens.isEmpty()) {
            return false;
        }

        TokenSpec token = tokens.remove(0);

        if (token.getType() == TokenType.KEYWORD && token.getValue().equals("crt")) {
            return headers2(tokens);
        }

        return false;
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