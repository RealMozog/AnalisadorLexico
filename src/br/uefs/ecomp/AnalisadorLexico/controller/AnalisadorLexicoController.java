package br.uefs.ecomp.AnalisadorLexico.controller;

import br.uefs.ecomp.AnalisadorLexico.model.TokensGenerator;
/**
 *
 * @author sandr
 */
public class AnalisadorLexicoController {
    private TokensGenerator automato;
    Character c;
    
    public void analiseArq(String arq){
        this.automato = new TokensGenerator();
        
        this.automato.setText(arq);
        
        this.automato.stateZero(this.automato.nextChar());
    }
}
