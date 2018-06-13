package edu.kit.informatik.parsing;

import java.util.Scanner;

/**
 * parses a integer value.
 * 
 * @author baikai
 * @version 1.0
 */
public class IntegerParser extends NumberParser<Integer> {
    /**
     * Creates an integer parser without a source scanner set.
     */
    public IntegerParser() {
        this(null);
    }

    /**
     * Creates an integer parser using the specified {@code sourceScanner}
     * scanner.
     * 
     * @param sourceScanner
     *            the scanner to retrieve the token from .The token should be at
     *            the scanner's momentary position.
     */
    public IntegerParser(Scanner sourceScanner) {
        super(sourceScanner, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    @Override
    protected Integer saveNumber() throws ParseException {
        if (!sourceScanner().hasNextInt() || !sourceScanner().hasNext("\\-?[0-9]+")) {
            throw parseException(expected(description()), false);
        }
        return sourceScanner().nextInt();
    }

    @Override
    protected String description() {
        return String.format("an Integer");
    }
}
