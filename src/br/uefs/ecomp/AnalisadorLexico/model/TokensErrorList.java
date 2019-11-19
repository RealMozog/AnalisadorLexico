package br.uefs.ecomp.AnalisadorLexico.model;

import br.uefs.ecomp.AnalisadorLexico.model.TokenError;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author sandr
 */
public class TokensErrorList {
    private ArrayList<LinkedList<TokenError>> tabelaErros = new ArrayList<LinkedList<TokenError>>();

    public TokensErrorList(int tam) {
        for (int i = 0; i < tam; i++) {
          LinkedList<TokenError> grupo = new LinkedList<>();
          tabelaErros.add(grupo);
        }
    }
    
    public void addToken (TokenError token){
        int indice = this.calcularIndice(token.getLine());
        
        List<TokenError> lista = this.tabelaErros.get(indice);
        lista.add(token);
    } 
    
    public List<TokenError> pegarTodos(){
        List<TokenError> erros = new ArrayList<>();
        
        for (int i = 0; i < this.tabelaErros.size(); i++){
            erros.addAll(this.tabelaErros.get(i));
        }
        
        return erros;
    }

    private int calcularIndice (int codigo){
        return codigo--;
    }
}
