package edu.kit.informatik.parsing;

import java.util.Scanner;

/**
 * A token parser for the expectation of no token.
 * 
 * @author baikai
 * @version 1.0
 *
 */
public class NoTokenParser extends TokenParser<Void> {
    /**
     * Creates a no token parser without a source scanner set.
     */
    public NoTokenParser() {
        this(null);
    }

    /**
     * Creates a simple decimal parser using the specified {@code source}
     * scanner.
     * 
     * @param sourceScanner
     *            The scanner to retrieve the token from . The token should be
     *            at the scanner's momentary position.
     */
    public NoTokenParser(Scanner sourceScanner) {
        super(sourceScanner);
    }

    @Override
    public Void next() throws ParseException {
        return saveNext();
    }

    @Override
    protected String description() {
        return "no token";
    }

    @Override
    protected Void saveNext() throws ParseException {
        if (sourceScanner().hasNext()) {
            throw parseException(expected(description()), false);
        }
        return null;
    }
}
