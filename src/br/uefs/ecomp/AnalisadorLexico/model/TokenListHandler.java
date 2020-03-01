package br.uefs.ecomp.AnalisadorLexico.model;

/**
 *
 * @author Alessandro Costa
 */
public class TokenListHandler {
    private TokensList tokens;
    private TokensErrorList tokensErro;
    
    TokenListHandler (int tam){
        this.tokens = new TokensList(tam);
        this.tokensErro = new TokensErrorList(tam);
    }

    public TokensList getTokens() {
        return tokens;
    }

    public TokensErrorList getTokensErro() {
        return tokensErro;
    }

}
