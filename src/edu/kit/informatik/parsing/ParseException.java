package edu.kit.informatik.parsing;

/**
 * Thrown if a string is to be parsed but is malformed, such that it cannot be
 * interpreted the way it was expected to be.
 * 
 * @author baikai
 * @version 1.0
 */
public class ParseException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * * Creates a parse exception with the given description.
     * 
     * @param errorDescription
     *            A description of what went wrong.
     */
    public ParseException(String errorDescription) {
        super(errorDescription);
    }
}
