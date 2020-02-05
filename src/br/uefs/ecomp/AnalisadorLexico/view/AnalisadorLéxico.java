package br.uefs.ecomp.AnalisadorLexico.view;

import br.uefs.ecomp.AnalisadorLexico.controller.AnalisadorLexicoController;
import br.uefs.ecomp.AnalisadorLexico.model.Token;
import br.uefs.ecomp.AnalisadorLexico.model.TokenError;

import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

/**
 *
 * @author Alessandro Costa
 * MI - Compiladores - UEFS
 */

public class AnalisadorLéxico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        AnalisadorLexicoController controller = new AnalisadorLexicoController();
        Iterator<Token> iterator;
        Iterator<TokenError> iteratorErr;
        int count = 1;
        String w = "";
        
        try{
            String path = "input\\entrada" + count + ".txt";
            String arq = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
 
            while (!arq.isEmpty()){
                
                controller.analiseArq(arq);
                
                iterator = controller.iteratorTokens();
                w += tokensValidos(iterator);
                
                iteratorErr = controller.iteratorErros();
                
                if(!iteratorErr.hasNext()){
                    System.out.printf("Nenhum erro encontrado no arquivo de entrada" + count + "\n");
                } else {
                    w += tokensInValidos(iteratorErr);
                }
                
                System.out.print(w);
                
                writeOutput (w, count);
                
                arq = null;
                w = "";
                count++;
                path = "input\\entrada" + count + ".txt";
                arq = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
            }
            
        } catch (IOException e) {
             System.err.printf("Erro na abertura do arquivo: %s.\n",e.getMessage());
        }
        
    }
    
    public static void writeOutput(String token, int count) 
        throws IOException {
        
        Path path = Paths.get("output\\saida" + count + ".txt");
        
        byte[] strToBytes = token.getBytes();
        
        Files.write(path, strToBytes);
    }
    
    public static String tokensValidos(Iterator<Token> iterator){
        String w = "";
        
        while(iterator.hasNext()){
            Token t = iterator.next();
            w += t.toString() + '\n';
        }
        
        return w;
    }
    
    public static String tokensInValidos(Iterator<TokenError> iterator){
        String err = "\n\n";
        
        while(iterator.hasNext()){
            TokenError e = iterator.next();
            err += e.toString() + '\n';
        }
        
        return err;
    }
}
