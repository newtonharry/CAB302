package junit.BioLibrary;

import java.util.HashMap;
import java.util.Map;

/**
 * General exception for the Sequence class.
 */
class SequenceException extends Exception {
    public SequenceException() {
        super();
    }

    public SequenceException(String message) {
        super(message);
    }

    public SequenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public SequenceException(Throwable cause) {
        super(cause);
    }
}

/**
 * Specifies the type of the molecular sequence.
 */
enum Type {DNA, RNA, Peptide}

/**
 * Represents an arbitrary molecular sequence.
 */
public class Sequence {

    static Map<Character, Character> complementDNA = new HashMap<Character, Character>() {{
        put('A', 'T');
        put('C', 'G');
        put('G', 'C');
        put('T', 'A');
    }};

    static Map<Character, Character> complementRNA = new HashMap<Character, Character>() {{
        put('A', 'U');
        put('U', 'A');
        put('G', 'C');
        put('C', 'G');
    }};


    private Type type;
    private String sequence;

    /**
     * Constructs a sequence with the given string and type.
     *
     * A DNA sequence must contain only the letters A, C, G, and T.
     * An RNA sequence must contain only the letters A, U, G, and C.
     *
     * @param sequence the sequence's letters.
     * @param type     the sequence's type (DNA or RNA).
     * @throws SequenceException if the sequence contains invalid letters as per the above definition.
     */
    public Sequence(String sequence, Type type) throws SequenceException {
        if (type == Type.DNA && !sequence.matches("[ACGT]+")) {
            throw new SequenceException("DNA must contain only the letters A, C, G, and T.");
        }
        if (type == Type.RNA && !sequence.matches("[AUGC]+")) {
            throw new SequenceException("RNA must contain only the letters A, U, G, and C.");
        }
        this.sequence = sequence;
        this.type = type;
    }

    /**
     * Gets the string representation of this sequence.
     * @return string representation of this sequence.
     */
    public String toString() {
        return sequence;
    }

    /**
     * Gets the type of this sequence.
     * @return The Type representing this sequence's type.
     */
    public Type getType() {
        return type;
    }

    /**
     * The reverse complement of a sequence is equal to the interchanging of its base complement letters.
     *
     * The base complements for DNA are A=T and G≡C
     * The base complements for RNA are A=U and G≡C
     *
     * For example, the reverse complement of the DNA sequence ACCGT is achieved by replacing A with T,
     * the Cs with Gs, the G with C, and the T with A, resulting in TGGCA.
     *
     * Updates the state of this sequence's sequence to the reverse complement.
     *
     * @return the reverse complement of this sequence.
     * @throws SequenceException if the sequence type is neither DNA nor RNA.
     */
    public String reverseComplement() throws SequenceException {

        char[] rc = sequence.toCharArray();
        Map<Character, Character> table = null;
        switch (type) {
            case DNA:
                table = complementDNA;
                break;
            case RNA:
                table = complementRNA;
                break;
            default:
                throw new SequenceException("Undefined sequence type for reverse complement.");
        }
        for (int i = 0; i < sequence.length(); i++) {
            rc[i] = table.get(rc[i]);
        }
        sequence = new String(rc);
        return new String(rc);
    }

    /**
     * A sequence is a palindrome when it is equal to the reverse of its reverse complement.
     *
     * For example, ACCTAGGT is a palindrome because its reverse complement is TGGATCCA, which when reversed is
     * ACCTAGGT.
     *
     * @return true if this sequence is a palindrome.
     * @throws SequenceException if construction of the reverse complement fails.
     */
    public boolean palindrome() throws SequenceException {
        String reverse = new StringBuilder(sequence).reverse().toString();
        Sequence reverseSequence = new Sequence(reverse, type);
        return sequence.equals(reverseSequence.reverseComplement());
    }

    /**
     * Transcribe this sequence to RNA if it is DNA or to DNA if it is RNA.
     *
     * A transcription from DNA to RNA is the replacement of all Ts with Us.
     * A transcription from RNA to DNA is the replacement of all Us with Ts.
     *
     * Updates the state of this sequence's sequence and type to reflect the transcription.
     *
     * @throws SequenceException if the sequence type is neither DNA nor RNA.
     */
    public void transcribe() throws SequenceException {
        char[] transcription = sequence.toCharArray();

        char target = 0;
        char replacement = 0;

        switch (type) {
            case DNA:
                target = 'T';
                replacement = 'U';
                break;
            case RNA:
                target = 'U';
                replacement = 'T';
                break;
            default:
                throw new SequenceException("Undefined sequence type for transcription.");
        }

        for (int i = 0; i < transcription.length; i++)
        {
            if (transcription[i] == target)
            {
                transcription[i] = replacement;
            }
        }

        sequence = new String(transcription);
        type = type == Type.DNA ? Type.RNA : type.DNA;
    }
}
