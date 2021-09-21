package src.domain.core.Instance;

import src.domain.core.TokenType;

public class TokenSpec {
    
    private TokenType type;
    private String value;

    public TokenSpec(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return type.toString() + ", " + value;
    }
}
