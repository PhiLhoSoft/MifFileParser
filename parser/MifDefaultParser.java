package org.philhosoft.mif.parser;


public abstract class MifDefaultParser implements MifParser
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
