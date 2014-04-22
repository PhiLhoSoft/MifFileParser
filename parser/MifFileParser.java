package org.philhosoft.mif.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.philhosoft.mif.model.MifFileContent;
import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.parser.data.MifArcParser;
import org.philhosoft.mif.parser.data.MifDataParser;
import org.philhosoft.mif.parser.data.MifEllipseParser;
import org.philhosoft.mif.parser.data.MifLineParser;
import org.philhosoft.mif.parser.data.MifPointParser;
import org.philhosoft.mif.parser.data.MifPolylineParser;
import org.philhosoft.mif.parser.data.MifRectangleParser;
import org.philhosoft.mif.parser.data.MifRegionParser;
import org.philhosoft.mif.parser.data.MifRoundedRectangleParser;
import org.philhosoft.mif.parser.data.MifTextParser;

public class MifFileParser
{
	public static void main(String[] args) throws FileNotFoundException
	{
		MifReader reader = new MifReader(new File(""));

		MifHeaderParser headerParser = new MifHeaderParser();
		MifFileContent fileContent = headerParser.parse(reader);
		if (reader.getErrorCollector().hasErrors())
		{
			// Output errors some way...

			return;
		}

		List<MifDataParser> parsers = new ArrayList<>();
		parsers.add(new MifRegionParser());
		parsers.add(new MifPolylineParser());
		parsers.add(new MifPointParser());
		parsers.add(new MifTextParser());
		parsers.add(new MifLineParser());
		parsers.add(new MifRectangleParser());
		parsers.add(new MifRoundedRectangleParser());
		parsers.add(new MifPointParser());
		parsers.add(new MifEllipseParser());
		parsers.add(new MifArcParser());

		while (reader.readNextLine())
		{
			for (MifDataParser parser : parsers)
			{
				if (parser.canParse(reader))
				{
					MifData data = parser.parseData(reader);
					fileContent.add(data);
					break;
				}
			}
		}
	}
}
