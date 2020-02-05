package br.uefs.ecomp.AnalisadorLexico.controller;

import br.uefs.ecomp.AnalisadorLexico.model.Token;
import br.uefs.ecomp.AnalisadorLexico.model.TokenError;
import br.uefs.ecomp.AnalisadorLexico.model.TokensGenerator;
import br.uefs.ecomp.AnalisadorLexico.model.TokenListHandler;
import java.util.Iterator;
/**
 *
 * @author Alessandro Costa
 */
public class AnalisadorLexicoController {
    private TokensGenerator automato;
    private TokenListHandler tokens;
    
    public void analiseArq(String arq){
        this.automato = new TokensGenerator(arq);
        
        
        tokens = this.automato.stateZero(this.automato.nextChar());
    }
    
    public Iterator<Token> iteratorTokens (){
        Iterator<Token> tokensIterator = tokens.getTokens().pegarTodos().iterator();
        
        return tokensIterator;
    }
    
    public Iterator<TokenError> iteratorErros(){
        Iterator<TokenError> errosIterator = tokens.getTokensErro().pegarTodos().iterator();
        
        return errosIterator;
    }
}
