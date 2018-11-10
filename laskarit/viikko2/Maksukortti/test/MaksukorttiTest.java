import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MaksukorttiTest {
    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void konstruktoriAsettaaSaldonOikein() {
        assertEquals("Kortilla on rahaa 10.0 euroa", kortti.toString());
    }

    @Test
    public void syoEdullisestiVahentaaSaldoaOikein() {
        kortti.syoEdullisesti();
        assertEquals("Kortilla on rahaa 7.5 euroa", kortti.toString());
    }

    @Test
    public void syoMaukkaastiVahentaaSaldoaOikein() {
        kortti.syoMaukkaasti();
        assertEquals("Kortilla on rahaa 6.0 euroa", kortti.toString());
    }

    @Test
    public void syoEdullisestiEiVieSaldoaNegatiiviseksi() {
        kortti.syoMaukkaasti();
        kortti.syoMaukkaasti();
        kortti.syoEdullisesti();
        assertEquals("Kortilla on rahaa 2.0 euroa", kortti.toString());
    }
    
    @Test
    public void syoMaukkaastiEiVieSaldoaNegatiiviseksi() {
        kortti.syoMaukkaasti();
        kortti.syoEdullisesti();
        kortti.syoMaukkaasti();
        assertEquals("Kortilla on rahaa 3.5 euroa", kortti.toString());
    }
    
    @Test
    public void negatiivisenSummanLataaminenEiNostaArvoa() {
        kortti.lataaRahaa(-2.5);
        kortti.lataaRahaa(-0.01);
        assertEquals("Kortilla on rahaa 10.0 euroa", kortti.toString());
    }
    
    @Test
    public void kortillaVoiOstaaEdullisenLounaan() {
        Maksukortti k2 = new Maksukortti(2.5);
        k2.syoEdullisesti();
        assertEquals("Kortilla on rahaa 0.0 euroa", k2.toString());
    }
    
    @Test
    public void kortillaVoiOstaaMaukkaanLounaan() {
        Maksukortti k2 = new Maksukortti(4.0);
        k2.syoMaukkaasti();
        assertEquals("Kortilla on rahaa 0.0 euroa", k2.toString());
    }

    @Test
    public void kortilleVoiLadataRahaa() {
        kortti.lataaRahaa(25);
        assertEquals("Kortilla on rahaa 35.0 euroa", kortti.toString());
    }

    @Test
    public void kortinSaldoEiYlitaMaksimiarvoa() {
        kortti.lataaRahaa(200);
        assertEquals("Kortilla on rahaa 150.0 euroa", kortti.toString());
    }
}