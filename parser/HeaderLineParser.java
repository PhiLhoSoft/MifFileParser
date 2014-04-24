package org.philhosoft.mif.parser;

/*
 KEYWORD someparameter
 */
public class HeaderLineParser
{
	public String parse(String keyword, ParsingContext context)
	{
		String line = context.getCurrentLine();
		if (line != null && line.toUpperCase().startsWith(keyword))
		{
			return line.substring(keyword.length()).trim();
		}
		context.pushBackLine();
		return null;
	}
}
