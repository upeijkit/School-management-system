package edu.kit.informatik.parsing;

import java.util.Scanner;

/**
 * Generic parser for numerical tokens. The token may be specified by its
 * maximum value and its minimum value.
 * 
 * @author baikai
 * @version 1.0
 * @param <N>
 *            The number token's type.
 */
public abstract class NumberParser<N extends Number & Comparable<N>> extends TokenParser<N> {
    private N min;
    private N max;

    /**
     * Creates a number parser.
     * 
     * @param sourceScanner
     *            The source scanner.
     * @param min
     *            The default minimum value.
     * @param max
     *            The default maximum value.
     */
    protected NumberParser(Scanner sourceScanner, N min, N max) {
        super(sourceScanner);
        this.max = max;
        this.min = min;
    }

    @Override
    protected N saveNext() throws ParseException {

        N number = saveNumber();
        if (number.compareTo(max) > 0) {
            throw parseException(expected("%s is no higher than %s", description(), max), true);
        }
        if (number.compareTo(min) < 0) {
            throw parseException(expected("%s is no lower than %s", description(), min), true);
        }

        return number;
    }

    /**
     * The actual number parsing. Subclasses should implement this method
     * instead of {@link #saveNext()}.
     * 
     * @return The parsed number token.
     * @throws ParseException
     *             If the token cannot be parsed for any reason.
     * 
     */
    protected abstract N saveNumber() throws ParseException;

    /**
     * Set the token's minimum value.
     * 
     * @param min
     *            The token's minimum value.
     * @return {@code this}
     */
    public NumberParser<N> minimun(N min) {
        this.min = min;
        return this;
    }

    /**
     * Set the token's maximum value.
     * 
     * @param max
     *            the token' maximum value.
     * @return {@code this}
     */
    public NumberParser<N> maximun(N max) {
        this.max = max;
        return this;
    }

}