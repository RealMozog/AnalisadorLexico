package br.uefs.ecomp.AnalisadorLexico.model;

import br.uefs.ecomp.AnalisadorLexico.model.TokensErrorList;
import br.uefs.ecomp.AnalisadorLexico.model.TokensList;
/**
 *
 * @author sandr
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
