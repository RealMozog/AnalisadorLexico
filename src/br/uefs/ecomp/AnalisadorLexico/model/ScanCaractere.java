package br.uefs.ecomp.AnalisadorLexico.model;

import java.util.Arrays;
import java.util.List;
/**
 *
 * @author sandr
 */
public class ScanCaractere {
    private final String letras = "abcdefghijklmnopkrstuvxzABCDEFGHIJKLMOPQRSTUVXZ";
    private final String digito = "1234567890";
    private final String[] palavras_reservadas;
    private final String op_aritmetico = "+-*/";
    private final String op_relacional = "!=<>";
    private final String delimitador = ";,()[]{}.|";

    public ScanCaractere() {
        this.palavras_reservadas = new String[]{
            "var", "const", "typedef", "struct", "extends", "procedure", "fuction",
            "start", "return", "print", "if", "else", "then", "while", "read", "print",
            "int", "real", "boolean", "string", "true", "false", "global", "local"
        };
    }
    
    public boolean isLetter(CharSequence caractere){
        return this.letras.contains(caractere);
    }
    
    public boolean isDigit(CharSequence caractere){
        return this.digito.contains(caractere);
    }
    
    public boolean isValidSymbol(int code){
        return (code != 34) || (code >= 32 && code <= 126) ;
    }
    
    public boolean isReservedWords (String word){
        List<String> words = Arrays.asList(this.palavras_reservadas);
        
        return words.contains(word);
    }
    
    public boolean isCharOfOpAritmetico (CharSequence caractere){
        return this.op_aritmetico.contains(caractere);
    }
    
    public boolean isCharOfOpRelacional (CharSequence caractere){
        return this.op_relacional.contains(caractere);
    }
    
    public boolean delimitador (CharSequence caractere){
        return this.delimitador.contains(caractere);
    }
}
