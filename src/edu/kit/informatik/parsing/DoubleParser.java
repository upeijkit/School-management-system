package edu.kit.informatik.parsing;

import java.util.Scanner;

/**
 * parses a integer value.
 * 
 * @author baikai
 * @version 1.0
 */
public class DoubleParser extends NumberParser<Double> {
    /**
     * Creates an double parser without a source scanner set.
     */
    public DoubleParser() {
        this(null);
    }

    /**
     * Creates an double parser using the specified {@code sourceScanner}
     * scanner.
     * 
     * @param sourceScanner
     *            the scanner to retrieve the token from .The token should be at
     *            the scanner's momentary position.
     */
    public DoubleParser(Scanner sourceScanner) {
        super(sourceScanner, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    @Override
    protected Double saveNumber() throws ParseException {
        if (!sourceScanner().hasNextDouble()) {
            throw parseException(expected(description()), false);
        }
        return sourceScanner().nextDouble();
    }

    @Override
    protected String description() {
        return String.format("an Double");
    }
}
