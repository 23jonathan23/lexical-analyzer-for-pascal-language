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

    private List<TokenSpec> invalidTokens;

    public Token() {
        this.reservedWords = Arrays.asList("var", "string", "char", "boolean", "integer", "real", "if", "then", 
            "else", "begin", "end", "read", "writeln", "program", "uses", "crt");
        
        this.operators = Arrays.asList("+", "-", "/", "*", "%", "^");

        this.symbols = Arrays.asList("<", ">", ":", "|", "&", ";", ".");

        this.tokens = new ArrayList<>();

        this.invalidTokens = new ArrayList<>();
    }

    public void mapTokensId(List<String> tokens) {
        for(var token : tokens)
            if(Pattern.matches(REGEX_IDENTIFIER, token) && !this.reservedWords.contains(token)
                && !Pattern.matches(REGEX_NSTRING, token))
                this.tokens.add(new TokenSpec(TokenType.ID, token));
    }

    public void mapTokensNumber(List<String> tokens) {
        for(var token : tokens)
            if(Pattern.matches(REGEX_NUMBER, token)) 
                this.tokens.add(new TokenSpec(TokenType.NUMBER, token));
    }

    public void mapTokensOperator(List<String> tokens) {
        for(var token : tokens)
            if(this.operators.contains(token)) 
                this.tokens.add(new TokenSpec(TokenType.OPERATOR, token));
    }

    public void mapTokensReservedWords(List<String> tokens) {
        for(var token : tokens)
            if(this.reservedWords.contains(token)) 
                this.tokens.add(new TokenSpec(TokenType.KEYWORD, token));
    }

    public void mapTokensSymbol(List<String> tokens) {
        for(var token : tokens)
            if(this.symbols.contains(token)) 
                this.tokens.add(new TokenSpec(TokenType.SYMBOL, token));
    }

    public void mapTokensParameter(List<String> tokens) {
        for(var token : tokens)
            if(token.equals("("))
                this.tokens.add(new TokenSpec(TokenType.LPAR, token));
            else if(token.equals(")"))
                this.tokens.add(new TokenSpec(TokenType.RPAR, token));
    }

    public void mapCurlyBrackets(List<String> tokens) {
        for (var token : tokens)
            if (token.equals("{")) 
                this.tokens.add(new TokenSpec(TokenType.LCURB, token));
            else if (token.equals("}"))
                this.tokens.add(new TokenSpec(TokenType.RCURB, token));
    }

    public void mapTokensNString(List<String> tokens) {
        for(var token : tokens)
            if(Pattern.matches(REGEX_NSTRING, token)) 
                this.tokens.add(new TokenSpec(TokenType.NSTRING, token));
    }

    public void mapTokensAssignment(List<String> tokens) {
        for(var token : tokens)
            if(token.equals("="))
                this.tokens.add(new TokenSpec(TokenType.ASSIGNMENT, token));
    }

    public void mapInvalidTokens(List<String> tokens) {
        for(var token : tokens)
            this.invalidTokens.add(new TokenSpec(TokenType.INVALID, token));
    }

    public List<TokenSpec> getTokensResult() {
        return this.tokens;
    }
}
