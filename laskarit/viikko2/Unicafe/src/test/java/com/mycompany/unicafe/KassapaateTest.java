package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {
    Kassapaate k;
    
    @Before
    public void setUp() {
        k = new Kassapaate();
    }
    
    @Test
    public void oikeatMaaratAlussa() {
        assertEquals(k.kassassaRahaa(), 100000);
        assertEquals(k.maukkaitaLounaitaMyyty(), 0);
        assertEquals(k.edullisiaLounaitaMyyty(), 0);
    }
    
    @Test
    public void kateisOstoToimii() {
        int ret = k.syoEdullisesti(500);
        assertEquals(ret, 260);
        assertEquals(k.edullisiaLounaitaMyyty(), 1);
        assertEquals(k.kassassaRahaa(), 100000 + 240);
        
        ret = k.syoMaukkaasti(ret);
        assertEquals(ret, 260);
        assertEquals(k.maukkaitaLounaitaMyyty(), 0);
        assertEquals(k.kassassaRahaa(), 100000 + 240);
        
        ret = k.syoMaukkaasti(400);
        assertEquals(ret, 0);
        assertEquals(k.maukkaitaLounaitaMyyty(), 1);
        assertEquals(k.kassassaRahaa(), 100000 + 240 + 400);
    }
    
    @Test
    public void korttiOstoToimii() {
        Maksukortti kortti = new Maksukortti(500);
        boolean passed = k.syoMaukkaasti(kortti);
        assertEquals(kortti.saldo(), 100);
        assertEquals(k.maukkaitaLounaitaMyyty(), 1);
        assertEquals(k.kassassaRahaa(), 100000);
        assertTrue(passed);
        
        passed = k.syoEdullisesti(kortti);
        assertEquals(kortti.saldo(), 100);
        assertEquals(k.edullisiaLounaitaMyyty(), 0);
        assertEquals(k.kassassaRahaa(), 100000);
        assertFalse(passed);
    }
    
    @Test
    public void kortilleLatausToimii() {
        Maksukortti kortti = new Maksukortti(500);
        k.lataaRahaaKortille(kortti, 50);
        assertEquals(k.kassassaRahaa(), 100000 + 50);
        assertEquals(kortti.saldo(), 550);
    }
    
    @Test
    public void negatiivinenLatausEiToimi() {
        int sum = k.kassassaRahaa();
        k.lataaRahaaKortille(new Maksukortti(10), -1);
        assertEquals(k.kassassaRahaa(), sum);
    }
}
