package br.uefs.ecomp.AnalisadorLexico.model;

import br.uefs.ecomp.AnalisadorLexico.model.Token;
import br.uefs.ecomp.AnalisadorLexico.model.TokenError;
import br.uefs.ecomp.AnalisadorLexico.model.ScanCaractere;
/**
 *
 * @author sandr
 */
public class TokensGenerator {
    private TokenError tokenError;
    private ScanCaractere scan;
    private String text;
    private int line = 1;
    
    private enum codigos {
        PRE, IDE, CDC, NRO, DEL, REL, LOG, ART;
    }
    
    private enum codigosErro {
        CMF, COMF, IDEMF;
    }

    public TokensGenerator() {
        this.tokenError = new TokenError();
        scan = new ScanCaractere();
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public Character nextChar (){
        if(!this.text.isEmpty()){
            Character next = this.text.charAt(0);

            if(scan.isLineFeed(next)){
                this.line++;
            }
            
            
            return next;
        }
        
        return null;
    }
    
    private boolean lookahead(Character expected){
        return this.text.length() > 1 ? this.text.charAt(1) == expected : false;
    }

    public void stateZero (Character c){
        
        if(c != null){
            if(scan.isSpace(c) || scan.isLineFeed(c)){
                this.text = this.text.substring(1);
                c = nextChar();
                while (scan.isSpace(c) || scan.isLineFeed(c)){
                    this.text = this.text.substring(1);
                    if(this.text.isEmpty()){
                        break;
                    }
                    c = nextChar();
                }
            }
            if(this.text.isEmpty()){
                stateZero(nextChar());
            } else
                if(c.equals('"')){
                    stateOne(c);
            } else
                if(c.equals('/') && (lookahead('/') || lookahead('*'))) {
                    stateTwo(c);
            } else
                if(scan.isLetter(c)){
                    stateThree(c);
            }
        }
 
    }
    
    // Cadeia de caracteres !!
    private void stateOne (Character c){
        Token token = new Token(c.toString(), this.line);
        this.tokenError.setLine(this.line);
        
        this.text = this.text.substring(1);
        c = nextChar();
        
        if(c != null){
            while (c != '"' &&  (scan.isLetter(c) || scan.isDigit(c)
                || scan.isValidSymbol(c) || scan.isLineFeed(c) || scan.isSpace(c))){
                if((int)c == 92 && lookahead('"')){
                    token.setLexema(c);
                    this.text = this.text.substring(1);
                    c = nextChar();
                    token.setLexema(c);
                    this.text = this.text.substring(1);
                } else {
                    token.setLexema(c);
                    this.text = this.text.substring(1);
                }
                if(this.text.isEmpty()){
                    break;
                }
                
                c = nextChar(); 
            }
        
            if(c == null){
                this.tokenError.setCodigo(codigosErro.CMF.toString());
                this.tokenError.setMal_formed_lexema(token.getLexema());
                System.out.print(this.tokenError.toString());
            }else 
                if (c.equals('"')){
                token.setLexema(c);
                this.text = this.text.substring(1);
                token.setCodigo(codigos.CDC.toString());
                System.out.print(token.toString()+ '\n');
                stateZero (nextChar ());
            } else {
                token.setLexema(c);
                this.tokenError.setCodigo(codigosErro.CMF.toString());
                this.tokenError.setMal_formed_lexema(token.getLexema());
                System.out.print(this.tokenError.toString()+ '\n');
                stateZero (nextChar ());
                // colocar na tabela de erros
            }
            
        }    
    }
     // comentarios!!
    private void stateTwo (Character c){
        Token token = new Token(c.toString(), this.line);
        this.tokenError.setLine(this.line);
        
        this.text = this.text.substring(1);
        c = nextChar();
        
        if(c != null){
            if(c.equals('/')){
                while (!scan.isLineFeed(c)){
                    this.text = this.text.substring(1);
                    if(this.text.isEmpty()){
                        break;
                    }
                    
                    c = nextChar();
                }
                this.text = this.text.substring(1);
                stateZero (nextChar ());
            } else {
                while (!this.text.isEmpty()){
                    token.setLexema(c);
                    this.text = this.text.substring(1);
                    if(this.text.isEmpty()){
                        token.setLexema(c);
                        this.tokenError.setCodigo(codigosErro.COMF.toString());
                        this.tokenError.setMal_formed_lexema(token.getLexema());
                        System.out.print(this.tokenError.toString()+ '\n');
                        break;
                    }
                    c = nextChar();
                    
                    if(c.equals('*') && lookahead('/')){
                        this.text = this.text.substring(2);
                        break;
                    }
                }
                stateZero (nextChar ());
            }
        }
    }
    
    public void stateThree (Character c){
        Token token = new Token(c.toString(), this.line);
        this.tokenError.setLine(this.line);
        
        this.text = this.text.substring(1);
        c = nextChar();
        
        if(c != null){
            while(scan.isLetter(c) || scan.isDigit(c) || c.equals('_')){
                token.setLexema(c);
                this.text = this.text.substring(1);
                
                if(this.text.isEmpty()){
                    break;
                }
                
                c = nextChar();
            }
            
            System.out.print(c);
            
            if(scan.delimitador(c) || scan.isCharOfOpAritmetico(c) || scan.isCharOfOpRelacional(c) || 
                    scan.isCharOfOpAritmetico(c) || scan.isLineFeed(c) || scan.isSpace(c)){
                
                if(scan.isReservedWords(token.getLexema())){
                    token.setCodigo(codigos.PRE.toString());
                } else {
                    token.setCodigo(codigos.IDE.toString());
                }
                System.out.print(token.toString()+ '\n');
                stateZero (c);
            } else 
                if(this.text.isEmpty()){
                    if(scan.isReservedWords(token.getLexema())){
                        token.setCodigo(codigos.PRE.toString());
                    } else {
                        token.setCodigo(codigos.IDE.toString());
                    }

                    System.out.print(token.toString()+ '\n');
                    stateZero (nextChar());
            } else {
                token.setLexema(c);
                this.tokenError.setCodigo(codigosErro.IDEMF.toString());
                this.tokenError.setMal_formed_lexema(token.getLexema());
                System.out.print(this.tokenError.toString()+ '\n');
                
                stateZero(nextChar());
            }
        }
    }
}
