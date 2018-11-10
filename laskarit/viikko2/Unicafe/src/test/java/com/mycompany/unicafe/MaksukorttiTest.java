package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void oikeaSaldo() {
        assertEquals(kortti.saldo(), 10);
        assertEquals(kortti.toString(), "saldo: 0.10");
    }
    
    @Test
    public void saldonLisaysToimii() {
        kortti.lataaRahaa(2);
        assertEquals(kortti.saldo(), 12);
        kortti.lataaRahaa(29);
        assertEquals(kortti.saldo(), 41);
    }
    
    @Test
    public void rahanOttaminenToimii() {
        kortti.otaRahaa(5);
        assertEquals(kortti.saldo(), 5);
        kortti.otaRahaa(4);
        assertEquals(kortti.saldo(), 1);
    }
    
    @Test
    public void saldoEiMuutuJosEiTarpeeksiRahaa() {
        boolean works1 = kortti.otaRahaa(11);
        assertEquals(kortti.saldo(), 10);
        assertEquals(kortti.toString(), "saldo: 0.10");
        boolean works2 = kortti.otaRahaa(10);
        boolean works3 = kortti.otaRahaa(1);
        assertEquals(kortti.saldo(), 0);
        assertEquals(kortti.toString(), "saldo: 0.0");
        
        assertFalse(works1);
        assertTrue(works2);
        assertFalse(works3);
    }
}
