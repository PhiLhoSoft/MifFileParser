package org.philhosoft.mif.parser;

public interface MifParser
{
	String getKeyword();
	boolean canParse(MifReader reader);
}
