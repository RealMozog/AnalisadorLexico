package br.uefs.ecomp.AnalisadorLexico.test;

import br.uefs.ecomp.AnalisadorLexico.model.ScanCaractere;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Before;

/**
 *
 * @author sandr
 */
public class Testes {
    
    private ScanCaractere scan;
    private String alpha;
    
    @Before
    public void setUp(){
        scan = new ScanCaractere();
    }
    
    @Test
    public void stringContains (){
        alpha = "abcdefghijklmnopkrstuvxzABCDEFGHIJKLMOPQRSTUVXZ";
        
        for (int i = 0; i < alpha.length(); i++){
            Character c = (alpha.charAt(i));
            
            assertTrue(scan.isLetter(c));
        }
    }
    
    @Test
    public void charcodeFromChar (){
        alpha = "jhjda$%#$´~.";
        Character c;
        
        for (int i = 0; i < alpha.length(); i++){
            c = alpha.charAt(i);
            
            assertTrue(scan.isValidSymbol(c));
        }
        
        Character alpha = '\n';
        
        assertTrue(scan.isLineFeed(alpha));
        
        alpha ='"';
        
        assertTrue(scan.isValidSymbol(alpha));
    }
    
    @Test
    public void listContains (){
        alpha = "var";
        
        assertTrue(scan.isReservedWords(alpha));
        
        alpha = "local";
        
        assertTrue(scan.isReservedWords(alpha));
    }
}
