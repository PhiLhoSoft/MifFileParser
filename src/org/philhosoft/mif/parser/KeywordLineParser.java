package org.philhosoft.mif.parser;

/*
 KEYWORD someparameter
 */
public class KeywordLineParser
{
	/**
	 * Ensures the current line starts with the given keyword, and if so, returns the following parameter.
	 * @param keyword  the keyword to check
	 * @param context  parsing context
	 * @return parameter(s) following the keyword
	 */
	public String parse(String keyword, ParsingContext context)
	{
		String line = context.getCurrentLine();
		if (line != null && line.toUpperCase().startsWith(keyword))
		{
			return line.substring(keyword.length()).trim();
		}
		return null;
	}

	/**
	 * Finds if the current line starts with one of the given keywords.
	 * @param keywords  array of keywords
	 * @param context  parsing context
	 * @return the found keyword, or null if not found
	 */
	public String findKeyword(String[] keywords, ParsingContext context)
	{
		String line = context.getCurrentLine();
		if (line == null)
			return null;
		line = line.toUpperCase();
		for (String keyword : keywords)
		{
			if (line.startsWith(keyword))
			{
				return keyword;
			}
		}
		return null;
	}

	/**
	 * Allows to skip lines with known keywords (ignored) until an unknown line is found,
	 * so that we can continue to the next section.
	 * @param keywords  array of known keywords
	 * @param context  parsing context
	 */
	public void skipKnownKeywordLines(String[] keywords, ParsingContext context)
	{
		boolean successful = true;
		while (successful && context.readNextLine())
		{
			if (context.getCurrentLine().isEmpty())
				continue;
			String keyword = findKeyword(keywords, context);
			successful = keyword != null;
		}
		context.pushBackLine();
	}
}
