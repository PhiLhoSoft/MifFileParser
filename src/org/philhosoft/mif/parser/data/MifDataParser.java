package org.philhosoft.mif.parser.data;

import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.parser.MifParser;
import org.philhosoft.mif.parser.ParsingContext;

public interface MifDataParser extends MifParser
{
	MifData parseData(ParsingContext context);
}
