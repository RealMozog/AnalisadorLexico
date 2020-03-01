package br.uefs.ecomp.AnalisadorLexico.controller;

import br.uefs.ecomp.AnalisadorLexico.model.Token;
import br.uefs.ecomp.AnalisadorLexico.model.TokenError;
import br.uefs.ecomp.AnalisadorLexico.model.TokensGenerator;
import br.uefs.ecomp.AnalisadorLexico.model.TokenListHandler;
import java.util.Iterator;
import java.util.List;
/**
 *
 * @author Alessandro Costa
 */
public class AnalisadorLexicoController {
    private TokensGenerator automato;
    private TokenListHandler tokens;
    
    public void analiseArq(String arq){
        this.automato = new TokensGenerator(arq);
        
        
        this.tokens = this.automato.stateZero(this.automato.nextChar());
    }
    
    public List<Token> listTokens (){  
        return this.tokens.getTokens().pegarTodos();
    }
    
    public Iterator<TokenError> iteratorErros(){
        Iterator<TokenError> errosIterator = tokens.getTokensErro().pegarTodos().iterator();
        
        return errosIterator;
    }
}
