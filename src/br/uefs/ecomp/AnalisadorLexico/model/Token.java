package br.uefs.ecomp.AnalisadorLexico.model;
/**
 *
 * @author Alessandro Costa
 */
public class Token {
    private String codigo;
    private String lexema;
    private int line;
    
    public Token(String lexema, int line){
        this.lexema = lexema;
        this.line = line;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "<" + line / 2 + ": " + codigo + ", " + lexema + ">";
    }

    public void setLexema(Character c) {
        this.lexema = lexema += c.toString();
    }
    
    public int getLine() {
        return line;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getLexema() {
        return lexema;
    }
}
