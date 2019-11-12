package br.uefs.ecomp.AnalisadorLexico.view;

import br.uefs.ecomp.AnalisadorLexico.controller.AnalisadorLexicoController;

import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
 

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
            List<String> arq = Files.readAllLines(Paths.get("arquivos_de_entrada\\entrada_exemplo_teste_lexico.txt"), StandardCharsets.UTF_8);
            String text = new String(Files.readAllBytes(Paths.get("arquivos_de_entrada\\entrada_exemplo_teste_lexico.txt")), StandardCharsets.UTF_8);
           // FileReader arq = new FileReader("arquivos_de_entrada\\entrada_exemplo_teste_lexico.txt");
          //  BufferedReader readArq = new BufferedReader(arq);
            System.out.printf("%s", text);
        } catch (IOException e) {
             System.err.printf("Erro na abertura do arquivo: %s.\n",e.getMessage());
        }
    }
    
}
