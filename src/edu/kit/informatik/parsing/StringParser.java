package edu.kit.informatik.parsing;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Parses a simple String token.The token may be specified by
 * 
 * Defining allowed strings through {@link #allowedTokens}. Defining a regular
 * expression that the token must match through {@link #regex()}.
 * 
 * @author baikai
 * @version 1.0
 *
 */
public class StringParser extends TokenParser<String> {
    private Set<String> allowedTokens = null;
    private String regex = null;

    /**
     * Creates a string parser without a source scanner set.
     */
    public StringParser() {
        this(null);
    }

    /**
     * Creates a string parser using the specified {@code source} scanner.
     * 
     * @param sourceScanner
     *            The scanner to retrieve the token from. The token should be at
     *            the scanner's momentary position.
     */
    public StringParser(Scanner sourceScanner) {
        super(sourceScanner);
    }

    /**
     * Specifies valid values for this string token.
     * 
     * @param tokens
     *            The string values this token may have.
     * @return {@code this}.
     */
    public StringParser validTokens(String... tokens) {
        allowedTokens = new HashSet<String>(Arrays.asList(tokens));
        return this;
    }

    /**
     * add valid tokens
     * 
     * @param tokens
     *            Tokens that should be considered valid additionally to the
     *            ones already specified.
     * @return {@code this}.
     */
    public StringParser addValidTokens(String... tokens) {
        allowedTokens.addAll(Arrays.asList(tokens));
        return this;
    }

    /**
     * Specifies a regular expression for this string token.
     * 
     * @param regex
     *            The regular expression this token must match.
     * @return {@code this}.
     */
    public StringParser regex(String regex) {
        this.regex = regex;
        return this;
    }

    @Override
    protected String saveNext() throws ParseException {
        String next = sourceScanner().next();
        if (((allowedTokens != null) && (!allowedTokens.contains(next)))
                || ((regex != null) && (!next.matches(regex)))) {
            throw parseException(expected(description()), true);
        }
        return next;
    }

    @Override
    protected String description() {
        String description = "";
        if (allowedTokens == null) {
            description += "a token";

        } else {
            description += "one of " + Arrays.toString(allowedTokens.toArray());
        }

        if (regex != null) {
            description += String.format(" matching the regular expression '%s' ", regex);
        }

        return description;
    }

}
