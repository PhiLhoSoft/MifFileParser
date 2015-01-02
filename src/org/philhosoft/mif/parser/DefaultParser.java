package org.philhosoft.mif.parser;


public abstract class DefaultParser implements MifParser
{
	@Override
	public boolean canParse(ParsingContext context)
	{
		String line = context.getCurrentLine();
		if (line == null)
			return false;
		return line.toUpperCase().startsWith(getKeyword());
	}
}
