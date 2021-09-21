package src.domain.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import src.domain.core.Instance.TokenSpec;

public class Token {
    private final String REGEX_IDENTIFIER = "[a-zA-Z_][a-zA-Z_0-9]*";

    private final String REGEX_NUMBER = "\\d+";

    private final String REGEX_NSTRING = "\"([^\"]*)\"";

    private List<String> symbols;

    private List<String> operators;

    private List<String> reservedWords;

    private List<TokenSpec> tokens;

    private List<String> invalidTokens;

    public Token() {
        this.reservedWords = Arrays.asList("var", "string", "char", "boolean", "integer", "real", "if", "then", 
            "else", "begin", "end", "read", "writeln", "program", "uses", "crt");
        
        this.operators = Arrays.asList("+", "-", "/", "*", "%", "^");

        this.symbols = Arrays.asList("<", ">", ":", "|", "&", ";", ".");

        this.tokens = new ArrayList<>();

        this.invalidTokens = new ArrayList<>();
    }

    public void mapTokens(List<String> tokens) {
        for (var token : tokens) {
            boolean isNotInvalid = false;

            isNotInvalid = checkTokenId(token);

            isNotInvalid = checkTokenNumber(token);

            isNotInvalid = checkTokenOperator(token);

            isNotInvalid = checkTokenReservedWords(token);

            isNotInvalid = checkTokenSymbol(token);

            isNotInvalid = checkTokenParameter(token);

            isNotInvalid = checkTokenCurlyBrackets(token);

            isNotInvalid = checkTokenNString(token);

            isNotInvalid = checkTokenAssignment(token);

            if(!isNotInvalid)
                invalidTokens.add(token);
        }
    }

    private boolean checkTokenId(String token) {
        if(Pattern.matches(REGEX_IDENTIFIER, token) && !this.reservedWords.contains(token)
                && !Pattern.matches(REGEX_NSTRING, token)) {
            this.tokens.add(new TokenSpec(TokenType.ID, token));
            return true;
        }

        return false;
    }

    private boolean checkTokenNumber(String token) {
        if(Pattern.matches(REGEX_NUMBER, token)) {
            this.tokens.add(new TokenSpec(TokenType.NUMBER, token));
            return true;
        }

        return false;
    }

    private boolean checkTokenOperator(String token) {
        if(this.operators.contains(token)) { 
            this.tokens.add(new TokenSpec(TokenType.OPERATOR, token));
            return true;
        }

        return false;
    }

    private boolean checkTokenReservedWords(String token) {
        if(this.reservedWords.contains(token)) {
            this.tokens.add(new TokenSpec(TokenType.KEYWORD, token));
            return true;
        }

        return false;
    }

    private boolean checkTokenSymbol(String token) {
        if(this.symbols.contains(token)) {
            this.tokens.add(new TokenSpec(TokenType.SYMBOL, token));
            return true;
        }

        return false;
    }

    private boolean checkTokenParameter(String token) {
        if(token.equals("(")) {
            this.tokens.add(new TokenSpec(TokenType.LPAR, token));
            return true;
        }
        if(token.equals(")")) {
            this.tokens.add(new TokenSpec(TokenType.RPAR, token));
            return true;
        }

        return false;
    }

    private boolean checkTokenCurlyBrackets(String token) {
        if (token.equals("{")) {
            this.tokens.add(new TokenSpec(TokenType.LCURB, token));
            return true;
        }
        if (token.equals("}")) {
            this.tokens.add(new TokenSpec(TokenType.RCURB, token));
            return true;
        }

        return false;
    }

    private boolean checkTokenNString(String token) {
        if(Pattern.matches(REGEX_NSTRING, token)) {
            this.tokens.add(new TokenSpec(TokenType.NSTRING, token));
            return true;
        }

        return false;
    }

    private boolean checkTokenAssignment(String token) {
        if(token.equals("=")) {
            this.tokens.add(new TokenSpec(TokenType.ASSIGNMENT, token));
            return true;
        }

        return false;
    }

    public List<TokenSpec> getTokensResult() {
        return this.tokens;
    }
}
