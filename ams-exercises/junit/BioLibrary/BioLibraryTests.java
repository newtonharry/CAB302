package junit.BioLibrary;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class BioLibraryTests {

        Sequence DNA;
    Sequence RNA;

    @Test
    public void testConstruction() throws SequenceException{
        this.DNA = new Sequence("GATACCA", Type.DNA);
        this.RNA = new Sequence("AUGCGCUACUAG", Type.RNA);
    }

    @Test
    public void testValidDNA() throws SequenceException{
        this.DNA = new Sequence("GATACCA", Type.DNA);
       assertTrue(this.DNA.toString().equals("GATACCA") && this.DNA.getType().equals(Type.DNA));
    }

    @Test
    public void testInvalidDNA(){
        assertThrows(SequenceException.class,() -> {
         this.DNA = new Sequence("",Type.DNA);
        });
    }

    @Test
    public void testValidRNA() throws SequenceException{
        this.RNA = new Sequence("AUGCGUA", Type.RNA);
        assertTrue(this.DNA.toString().equals("AUGCGUA") && this.RNA.getType().equals(Type.RNA));
    }

    @Test
    public void testInvalidRNA(){
        assertThrows(SequenceException.class,() -> {
            this.RNA = new Sequence("",Type.RNA);
        });
    }

    @Test
    public void testDNAReverseCompliment() throws SequenceException{
        this.DNA = new Sequence("GATACCA",Type.DNA);
        assertTrue(this.DNA.reverseComplement().equals("CTATGGT"));
    }

    @Test
    public void testRNAReverseCompliment() throws SequenceException{
        this.RNA = new Sequence("AUGCGUA",Type.RNA);
        assertTrue(this.DNA.reverseComplement().equals("UACGCAU"));
    }

    @Test
    public void testInvalidReverseCompliment(){
        assertThrows(SequenceException.class,() -> {
            this.DNA = new Sequence("AUGCGA",Type.Peptide);
        });
    }

    @Test
    public void testPalindromeDNA() throws SequenceException{
        this.DNA = new Sequence("AGGA",Type.DNA);
        assertTrue(this.DNA.palindrome());
    }

    @Test
    public void testInvalidPalindromeDNA() throws SequenceException{
        this.DNA = new Sequence("AGCGATGA",Type.DNA);
        assertTrue(!this.DNA.palindrome());
    }

    @Test
    public void testPalindromeRNA() throws SequenceException{
        this.RNA = new Sequence("UGGU",Type.RNA);
        assertTrue(this.RNA.palindrome());
    }

    @Test
    public void testInvalidPalindromeRNA() throws SequenceException{
        this.DNA = new Sequence("AGCGATGA",Type.RNA);
        assertTrue(!this.RNA.palindrome());
    }


}
