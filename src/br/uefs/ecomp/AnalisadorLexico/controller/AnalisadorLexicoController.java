package br.uefs.ecomp.AnalisadorLexico.controller;

import br.uefs.ecomp.AnalisadorLexico.model.TokensGenerator;
/**
 *
 * @author sandr
 */
public class AnalisadorLexicoController {
    private TokensGenerator automato;
    Character c;
    
    public void analiseArq(String path){
        this.automato = new TokensGenerator();
        
        this.automato.setText("-   1.  12.56");
        
        this.automato.stateZero(this.automato.nextChar());
    }
}
