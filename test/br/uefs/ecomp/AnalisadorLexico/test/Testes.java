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
    public void testLerLetras (){
        alpha = "abcdefghijklmnopkrstuvxzABCDEFGHIJKLMOPQRSTUVXZ";
        
        for (int i = 0; i < alpha.length(); i++){
            String c = Character.toString(alpha.charAt(i));
            
            assertTrue(scan.isLetter(c));
        }
    }
    
    @Test
    public void isValidSymbol (){
        alpha = "jhjda$%#$´~.";
        
        for (int i = 0; i < alpha.length(); i++){
            int charCode = (int)alpha.charAt(i);
            
            assertTrue(scan.isValidSymbol(charCode));
        }
    }
    
    @Test
    public void isPalavraReservada (){
        alpha = "var";
        
        assertTrue(scan.isReservedWords(alpha));
        
        alpha = "local";
        
        assertTrue(scan.isReservedWords(alpha));
    }
}
