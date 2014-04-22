package org.philhosoft.mif.parser;


public abstract class DefaultParser implements MifParser
{
	@Override
	public boolean canParse(MifReader reader)
	{
		String line = reader.getCurrentLine();
		if (line == null)
			return false;
		return line.toUpperCase().startsWith(getKeyword());
	}
}
