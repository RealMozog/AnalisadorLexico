package br.uefs.ecomp.AnalisadorLexico.model;

import br.uefs.ecomp.AnalisadorLexico.model.Token;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Alessandro Costa
 */
public class TokensList {
    private ArrayList<LinkedList<Token>> tabelaDeSimbolos = new ArrayList<LinkedList<Token>>();

    public TokensList(int tam) {
        for (int i = 0; i < tam; i++) {
          LinkedList<Token> grupo = new LinkedList<Token>();
          tabelaDeSimbolos.add(grupo);
        }
    }
    
    public void addToken (Token token){
        int indice = this.calcularIndice(token.getLine());
        
        List<Token> lista = this.tabelaDeSimbolos.get(indice);
        lista.add(token);
    } 
    
    public List<Token> pegarTodos(){
        List<Token> tokens = new ArrayList<>();
        
        for (int i = 0; i < this.tabelaDeSimbolos.size(); i++){
            tokens.addAll(this.tabelaDeSimbolos.get(i));
        }
        
        return tokens;
    }

    private int calcularIndice (int codigo){
        return codigo--;
    }

}
