package br.uefs.ecomp.AnalisadorLexico.model;

/**
 *
 * @author sandr
 */
public class TokenError {
    private String codigo;
    private String mal_formed_lexema;
    private int line;

    @Override
    public String toString() {
        return "< " + line + ": " + codigo + ", " + mal_formed_lexema + ">";
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setMal_formed_lexema(String mal_formed_lexema) {
        this.mal_formed_lexema = mal_formed_lexema;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getMal_formed_lexema() {
        return mal_formed_lexema;
    }

    public int getLine() {
        return line;
    }

}
