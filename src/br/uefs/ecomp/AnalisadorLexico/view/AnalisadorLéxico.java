package br.uefs.ecomp.AnalisadorLexico.view;

import br.uefs.ecomp.AnalisadorLexico.controller.AnalisadorLexicoController;

import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
 

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
        int count = 1;
        
        try{
            String path = "input\\entrada" + count + ".txt";
            String arq = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
            while (!arq.isEmpty()){
                controller.analiseArq(arq);
                arq = null;
                count++;
                path = "input\\entrada" + count + ".txt";
                arq = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
            }
            
        } catch (IOException e) {
             System.err.printf("Erro na abertura do arquivo: %s.\n",e.getMessage());
        }
        
    }
    
}
