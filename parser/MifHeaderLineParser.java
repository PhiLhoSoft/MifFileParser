package org.philhosoft.mif.parser;

/*
 KEYWORD someparameter
 */
public class MifHeaderLineParser
{
	public String parse(String keyword, MifReader reader)
	{
		String line = reader.getCurrentLine();
		if (line != null && line.toUpperCase().startsWith(keyword))
		{
			return line.substring(keyword.length()).trim();
		}
		reader.pushBackLine();
		return null;
	}
}
