/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uefs.ecomp.AnalisadorLexico.model;

/**
 *
 * @author sandr
 */
public class Token {
    private String codigo;
    private String lexema;
    
    public Token(String codigo, String lexema){
        this.codigo = codigo;
        this.lexema = lexema;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "< " + codigo + ", " + lexema + " >";
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getLexema() {
        return lexema;
    }
}
