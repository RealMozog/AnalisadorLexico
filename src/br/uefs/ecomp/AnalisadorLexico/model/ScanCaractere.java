package br.uefs.ecomp.AnalisadorLexico.model;

import java.util.Arrays;
import java.util.List;
/**
 *
 * @author Alessandro Costa
 */
public class ScanCaractere {
    private final String letras = "abcdefghijklmnopkrstuvwyxzABCDEFGHIJKLMNOPQRSTUVWYXZ";
    private final String digito = "1234567890";
    private final String[] palavras_reservadas;
    private final String op_aritmetico = "+-*/";
    private final String op_relacional = "!=<>";
    private final String delimitador = ";,()[]{}.";

    public ScanCaractere() {
        this.palavras_reservadas = new String[]{
            "var", "const", "typedef", "struct", "extends", "procedure", "fuction",
            "start", "return", "print", "if", "else", "then", "while", "read", "print",
            "int", "real", "boolean", "string", "true", "false", "global", "local"
        };
    }
    
    public boolean isLetter(Character c){
        return c != null ? this.letras.contains(c.toString()) : false;
    }
    
    public boolean isDigit(Character c){
        return c != null ? this.digito.contains(c.toString()) : false;
    }
    
    public boolean isValidSymbol(Character c){
        return c != null ? ((int)c != 34) && ((int)c >= 32 && (int)c <= 126) : false ;
    }
    
    public boolean isSpace (Character c){
        return c != null ? (int)c == 9 || (int)c == 32 : false;
    }
    
    public boolean isLineFeed (Character c){
        return c != null ? (int)c == 10 || (int)c == 13 : false;
    }
    
    public boolean isReservedWords (String word){
        List<String> words = Arrays.asList(this.palavras_reservadas);
        
        return word != null ?  words.contains(word) : false;
    }
    
    public boolean isCharOfOpAritmetico (Character c){
        return c != null ? this.op_aritmetico.contains(c.toString()) : false;
    }
    
    public boolean isCharOfOpRelacional (Character c){
        return c != null ? this.op_relacional.contains(c.toString()) : false;
    }
    
    public boolean delimitador (Character c){
        return c != null ? this.delimitador.contains(c.toString()) : false;
    }
}
