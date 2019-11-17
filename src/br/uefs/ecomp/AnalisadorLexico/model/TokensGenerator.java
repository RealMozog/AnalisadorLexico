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
        CMF, COMF, IDEMF, NROMF, LOGMF, CIN;
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
        this.tokenError.setLine(this.line);
        
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
                    automatoCadeiaCaractere(c);
            } else
                if(c.equals('/')) {
                    if(lookahead('/') || lookahead('*')){
                        automatoComentario(c);
                    } else {
                        automatoOpAritmetico(c);
                    }
                    
            } else
                if(scan.isLetter(c)){
                    automatoIdentificador(c);
            } else
                if(scan.isDigit(c) || c.equals('-')){
                    if(c.equals('-') && lookahead('-')){
                        automatoOpAritmetico(c);
                    } else {
                        automatoNumero(c);
                    }
            } else 
                if(c.equals('*') || c.equals('+')){
                    automatoOpAritmetico(c);
            } else 
                if(scan.delimitador(c)){
                    automatoDelimitador(c);
            } else 
                if(scan.isCharOfOpRelacional(c)){
                    if(c.equals('!') && !lookahead('=')){
                        automatoOpLogico(c);
                    } else {
                        automatoOpRelacional(c);
                    }      
            } else
                if(c.equals('&') || c.equals('|')){
                    automatoOpLogico(c);
            } else {
                this.tokenError.setCodigo(codigosErro.CIN.toString());
                this.tokenError.setMal_formed_lexema(c.toString());
                System.out.print(this.tokenError.toString()+ '\n');
                this.text = this.text.substring(1);
                stateZero (nextChar ());
            }
        }
 
    }
    
    // Cadeia de caracteres !!
    private void automatoCadeiaCaractere (Character c){
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
        } else {
            this.tokenError.setCodigo(codigosErro.CMF.toString());
            this.tokenError.setMal_formed_lexema(token.getLexema());
            System.out.print(this.tokenError.toString()+ '\n');
            stateZero (nextChar ());
        }
    }
    
     // comentarios!!
    private void automatoComentario (Character c){
        Token token = new Token(c.toString(), this.line);
        this.tokenError.setLine(this.line);
        
        this.text = this.text.substring(1);
        c = nextChar();
        
        if(c.equals('/')){
            while (!scan.isLineFeed(c)){
                this.text = this.text.substring(1);
                if(this.text.isEmpty()){
                    break;
                }

                c = nextChar();
            }
            stateZero (nextChar());
        } else {
            while (!this.text.isEmpty()){
                token.setLexema(c);
                this.text = this.text.substring(1);
                if(this.text.isEmpty()){
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
    
    // identificadores e palavras reservadas!!
    private void automatoIdentificador (Character c){
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
                }
                
                this.tokenError.setCodigo(codigosErro.IDEMF.toString());
                this.tokenError.setMal_formed_lexema(token.getLexema());
                System.out.print(this.tokenError.toString()+ '\n');
                stateZero(nextChar());
            }
        } else {
            token.setCodigo(codigos.IDE.toString());
            System.out.print(token.toString()+ '\n');
            stateZero (c);
        }
    }
    
    // numeros!!
    private void automatoNumero(Character c){
        Token token = new Token(c.toString(), this.line);
        this.tokenError.setLine(this.line);
        
        if(c.equals('-')){
            this.text = this.text.substring(1);
            c = nextChar();
            
            if(c != null){
                while (scan.isSpace(c)){
                    this.text = this.text.substring(1);
                    if(this.text.isEmpty()){
                        break;
                    }
                    
                    c = nextChar();
                }
                
                
                if(scan.isDigit(c)){
                    token.setLexema(c);
                    this.text = this.text.substring(1);
                    c = nextChar();

                    if(c != null){
                        if(scan.isDigit(c)){
                           while(scan.isDigit(c)){
                               token.setLexema(c);
                               this.text = this.text.substring(1);
                               if(this.text.isEmpty()){
                                   break;
                               }
                               
                               c = nextChar();
                           }
                        }
                        
                        if(c.equals('.')){
                            token.setLexema(c);
                            this.text = this.text.substring(1);
                            c = nextChar();
                            if(c != null){
                                if(scan.isDigit(c)){
                                    while(scan.isDigit(c)){
                                         token.setLexema(c);
                                         this.text = this.text.substring(1);
                                         if(this.text.isEmpty()){
                                             break;
                                         }

                                         c = nextChar();
                                     }

                                     token.setCodigo(codigos.NRO.toString());
                                     System.out.print(token.toString()+ '\n');
                                     stateZero (c);
                                } else {
                                   token.setLexema(c);
                                   this.text = this.text.substring(1);
                                   this.tokenError.setCodigo(codigosErro.NROMF.toString());
                                   this.tokenError.setMal_formed_lexema(token.getLexema());
                                   System.out.print(this.tokenError.toString()+ '\n');
                                   stateZero (nextChar());
                                }
                            } else {
                                this.tokenError.setCodigo(codigosErro.NROMF.toString());
                                this.tokenError.setMal_formed_lexema(token.getLexema());
                                System.out.print(this.tokenError.toString()+ '\n');
                                stateZero (c);
                            }
                        }
                    } else {
                        token.setCodigo(codigos.NRO.toString());
                        System.out.print(token.toString()+ '\n');
                        stateZero (nextChar());
                    }
                } else {
                    token.setCodigo(codigos.ART.toString());
                    System.out.print(token.toString()+ '\n');
                    stateZero (nextChar());
                }
            } else {
                token.setCodigo(codigos.ART.toString());
                System.out.print(token.toString()+ '\n');
                stateZero (c);
            }   
        } else {
            this.text = this.text.substring(1);
            c = nextChar();
            
            if(c != null){
                while(scan.isDigit(c)){
                    token.setLexema(c);
                    this.text = this.text.substring(1);
                    if(this.text.isEmpty()){
                        break;
                    }
                    
                    c = nextChar();
                }
                
                if(c.equals('.')){
                    token.setLexema(c);
                    this.text = this.text.substring(1);
                    c = nextChar();
                    if (c != null){
                        if(scan.isDigit(c)){
                            while(scan.isDigit(c)){
                                token.setLexema(c);
                                this.text = this.text.substring(1);
                                if(this.text.isEmpty()){
                                    break;
                               }

                                c = nextChar();
                            }

                            token.setCodigo(codigos.NRO.toString());
                            System.out.print(token.toString()+ '\n');
                            stateZero (c);
                        } else {
                            token.setLexema(c);
                            this.text = this.text.substring(1);
                            this.tokenError.setCodigo(codigosErro.NROMF.toString());
                            this.tokenError.setMal_formed_lexema(token.getLexema());
                            System.out.print(this.tokenError.toString()+ '\n');
                            stateZero (nextChar());
                        }
                    } else {
                        this.tokenError.setCodigo(codigosErro.NROMF.toString());
                        this.tokenError.setMal_formed_lexema(token.getLexema());
                        System.out.print(this.tokenError.toString()+ '\n');
                        stateZero (nextChar());
                    }
                }
            } else {
                token.setCodigo(codigos.NRO.toString());
                System.out.print(token.toString()+ '\n');
                stateZero (c);
            }
        }
    }
    
    // operador aritmetico
    private void automatoOpAritmetico(Character c){
        Token token = new Token(c.toString(), this.line);
        
        if(c.equals('-') && lookahead('-')){
            this.text = this.text.substring(1);
            c = nextChar();
            token.setLexema(c);
            this.text = this.text.substring(1);
        } else 
            if(c.equals('+') && lookahead('+')){
                this.text = this.text.substring(1);
                c = nextChar();
                token.setLexema(c);
                this.text = this.text.substring(1);
        } else
            if(c.equals('/') || c.equals('*')){
            this.text = this.text.substring(1);
        } else {
            this.text = this.text.substring(1);
        }

        token.setCodigo(codigos.ART.toString());
        System.out.print(token.toString()+ '\n');
        stateZero (nextChar());
    }
    
    // delimitadores 
    private void automatoDelimitador(Character c){
        Token token = new Token(c.toString(), this.line);
        this.text = this.text.substring(1);
        
        token.setCodigo(codigos.DEL.toString());
        System.out.print(token.toString()+ '\n');
        stateZero (nextChar());
    }
    
    // operadores relacionais
    private void automatoOpRelacional(Character c){
        Token token = new Token(c.toString(), this.line);
        
        if(c.equals('!') && lookahead('=')){
            this.text = this.text.substring(1);
            c = nextChar();
            token.setLexema(c);
        }
        if(c.equals('=') && lookahead('=')){
            this.text = this.text.substring(1);
            c = nextChar();
            token.setLexema(c);
        }
        if((c.equals('<') || c.equals('>')) && lookahead('=')){
            this.text = this.text.substring(1);
            c = nextChar();
            token.setLexema(c);
        } else {
            token.setCodigo(codigos.REL.toString());
            System.out.print(token.toString()+ '\n');
            this.text = this.text.substring(1);
            stateZero (nextChar());
        }
    }
    
    // operadores logicos
    private void automatoOpLogico(Character c){
        Token token = new Token(c.toString(), this.line);
        this.tokenError.setLine(this.line);
        
        if(c.equals('&') && lookahead('&')){
            this.text = this.text.substring(1);
            c = nextChar();
            token.setLexema(c);
        }
        if(c.equals('|') && lookahead('|')){
            this.text = this.text.substring(1);
            c = nextChar();
            token.setLexema(c);
        }
        if(c.equals('&') || c.equals('|')){
            this.tokenError.setCodigo(codigosErro.LOGMF.toString());
            this.tokenError.setMal_formed_lexema(token.getLexema());
            System.out.print(this.tokenError.toString()+ '\n');
            this.text = this.text.substring(1);
            stateZero (nextChar());
        } else {
            token.setCodigo(codigos.LOG.toString());
            System.out.print(token.toString()+ '\n');
            this.text = this.text.substring(1);
            stateZero (nextChar());
        }
    }
}
