package org.philhosoft.mif.parser.data;

import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.parser.MifParser;
import org.philhosoft.mif.parser.MifReader;

public interface MifDataParser extends MifParser
{
	MifData parseData(MifReader reader);
}
