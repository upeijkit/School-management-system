package edu.kit.informatik.parsing;

import java.util.Scanner;

/**
 * a parser for token from scanner.
 * 
 * @author baikai
 * @version 1.0
 * @param <T>
 *            The type of token.
 */
public abstract class TokenParser<T> {
    private static String lastRead = null;
    /**
     * The scanner provided by the user.
     */
    private Scanner sourceScanner;
    private T lastToken = null;

    /**
     * Creates a parser that retrieves the token from the {@code source}
     * scanner.
     * 
     * @param sourceScanner
     *            The scanner to retrieve the token from. The token should be at
     *            the scanner's momentary position.
     */
    public TokenParser(Scanner sourceScanner) {
        this.sourceScanner = sourceScanner;
    }

    /**
     * 
     * @return The source scanner to get tokens from.
     */
    protected Scanner sourceScanner() {
        return sourceScanner;
    }

    /**
     * Tries to parse the token described by {@code this} from the source
     * scanner.
     * 
     * @return The token described by {@code this}.
     * @throws ParseException
     *             If the token cannot be parsed for any reason.
     */
    public T next() throws ParseException {
        if (!sourceScanner.hasNext()) {
            if (lastRead != null) {
                throw new ParseException(
                        "Unexpected end of input. " + expected("%s after '%s'", description(), lastRead));
            } else {
                throw new ParseException("Did not find any input. " + expected(description()));
            }

        }

        lastToken = saveNext();
        lastRead = sourceScanner.match().group();
        return lastToken;
    }

    /**
     * Runs {@link #next()}, and asserts that no more input can be found in the
     * source scanner afterwards.
     * 
     * @return What {@link #next()} would return.
     * @throws ParseException
     *             If any input, including delimiters, is found after calling
     *             {@link #next()} or if {@link #next()} would throw a parse
     *             exception.
     */
    public T end() throws ParseException {
        T endToken = next();
        if (!"".equals(sourceScanner.findWithinHorizon(".*", 0))) {
            throw parseException(expected("end of input"), true);
        }
        return endToken;
    }

    /**
     * Handles the actual parsing of the token.
     * 
     * @return The parse result.
     * @throws ParseException
     *             If the token cannot be parsed for any reason.
     */
    protected abstract T saveNext() throws ParseException;

    /**
     *
     * @return A description of the token that is to be parsed by this token
     *         parser.
     */
    protected abstract String description();

    /**
     * 
     * @return What {@link #next()} returned the last time it was called.
     */
    public T lastToken() {
        return lastToken;
    }

    /**
     * Sets this parser's source scanner.
     * 
     * @param sourceScanner
     *            The scanner to retrieve the token from. The token should be at
     *            the scanner's momentary position.
     */
    public void setSourceScanner(Scanner sourceScanner) {
        this.sourceScanner = sourceScanner;
    }

    /**
     * Generates a Parse Exception
     * 
     * @param reason
     *            Why parsing failed
     * @param matched
     *            Whether any of the {@code Scanner#next...}-methods have
     *            already been called since the start of {@link #saveNext()}.
     * @return A prepared Parse Exception to throw for {@code reason}.
     */
    protected ParseException parseException(String reason, boolean matched) {
        if (!matched) {
            sourceScanner.next();
        }
        lastRead = sourceScanner.match().group();
        return new ParseException(String.format("Unexpected token '%s', %s", lastRead, reason));
    }

    /**
     * Shortcut for generating a "Expected..." string, using
     * {@link String#format(String, Object...)}.
     * 
     * @param s
     *            The string to pass to {@link String#format(String, Object...)}
     * 
     * @param arguments
     *            The arguments to pass to
     *            {@link String#format(String, Object...)}.
     * @return {@code "Expected " + String.format(s, arguments) + "."}.
     */
    protected String expected(String s, Object... arguments) {
        return "Expected " + String.format(s, arguments) + ".";
    }

}
