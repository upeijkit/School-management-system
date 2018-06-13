package edu.kit.informatik.parsing;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * a parser for the command with(or without) arguments.
 * 
 * @author baikai
 * @version 1.0
 */
public class CommandParser extends TokenParser<String> {
    /*
     * use commandSeparator(String) to separate command and arguments; use
     * argumentsSeparator(String) to separate the different arguments.
     */
    private String commandSeparator;
    private String argumentsSeparator;
    private StringParser commandNameParser = new StringParser();
    private NoTokenParser noToken = new NoTokenParser();

    /**
     * Creates a command parser without source scanner set.
     */
    public CommandParser() {
        this(null);
    }

    /**
     * Creates a command parser with a specified {@code sourceScanner} scanner.
     * 
     * @param sourceScanner
     *            the scanner to retrieve the token from. The token should be at
     *            the scanner's momentary position.
     * 
     */
    public CommandParser(Scanner sourceScanner) {
        super(sourceScanner);
        commandNameParser.setSourceScanner(sourceScanner);
        noToken.setSourceScanner(sourceScanner);
    }

    /**
     * sets valid commands which can be considered by parser.
     * 
     * @param commands
     *            the commands that this parser can accept.
     * @return {@code this}
     */
    public CommandParser commands(String... commands) {
        commandNameParser.validTokens(commands);
        return this;
    }

    /**
     * sets the separator for the command and its arguments.
     * 
     * @param commandSeparator
     *            the String that separates the command and its arguments.
     * @return {@code this}
     */
    public CommandParser commandSeparator(String commandSeparator) {
        this.commandSeparator = commandSeparator;
        return this;
    }

    /**
     * sets the separator for arguments
     * 
     * @param argumentsSeparator
     *            The String that separates different arguments.
     * @return {@code this}
     */
    public CommandParser argumentsSeparator(String argumentsSeparator) {
        this.argumentsSeparator = argumentsSeparator;
        return this;

    }

    /**
     * parser for asserting the command separator; if a command with arguments
     * {@code hasArguments == true} is expected, there should be a command
     * separator; if a command without arguments {@code has Arguments == false}
     * is expected, no more character are expected; argument separator is used
     * as delimiter of scanner.
     * 
     * @param hasArguments
     *            whether the command is expected to have arguments.
     * @throws ParseException
     *             {@code hasArguments == true} If the command separator is not
     *             found at the scanner's position, ignoring delimiters.
     *             {@code hasArguments == false} If any character is found at
     *             the source scanner's position, ignoring delimiters.
     * 
     */
    public void expectArguments(boolean hasArguments) throws ParseException {
        if (hasArguments) {
            if (sourceScanner().findWithinHorizon(Pattern.quote(commandSeparator),
                    Pattern.quote(commandSeparator).length()) == null) {
                if (!sourceScanner().hasNext()) {
                    throw new ParseException(
                            String.format("Unexpected end of input, %s", expected("the command's arguments")));
                }
                throw parseException(expected("'%s' to separate command and arguments. ", commandSeparator), true);
            }
            sourceScanner().useDelimiter(Pattern.quote(argumentsSeparator));

        } else {
            noToken.end();

        }
    }

    @Override
    public void setSourceScanner(Scanner sourceScanner) {
        super.setSourceScanner(sourceScanner);
        commandNameParser.setSourceScanner(sourceScanner);
        noToken.setSourceScanner(sourceScanner);
    }

    @Override
    protected String saveNext() throws ParseException {
        return commandNameParser.next();
    }

    @Override
    protected String description() {
        return "Another command: " + commandNameParser.description();
    }

}
