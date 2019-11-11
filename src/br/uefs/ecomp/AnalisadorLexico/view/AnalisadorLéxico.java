package br.uefs.ecomp.AnalisadorLexico.view;

import br.uefs.ecomp.AnalisadorLexico.controller.AnalisadorLexicoController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
 

/**
 *
 * @author sandr
 */

public class AnalisadorLéxico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        AnalisadorLexicoController controller = new AnalisadorLexicoController();
        
        try{
            FileReader arq = new FileReader("arquivos_de_entrada\\entrada_exemplo_teste_lexico.txt");
            BufferedReader readArq = new BufferedReader(arq);
 
            String linha = readArq.readLine();
            
            while(linha != null){
                System.out.printf("%s\n", linha);
                
 
                linha = readArq.readLine();
            }
        } catch (IOException e) {
             System.err.printf("Erro na abertura do arquivo: %s.\n",e.getMessage());
        }
    }
    
}
