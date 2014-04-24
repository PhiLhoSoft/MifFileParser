package org.philhosoft.mif.parser;

public interface MifParser
{
	/** Returns the keyword (starting the line) for this parser. Always in upper case. */
	String getKeyword();
	/** Returns true if this parser can parse the current line. */
	boolean canParse(ParsingContext context);
}
