package org.philhosoft.mif.parser;

import java.util.ArrayList;
import java.util.List;

import org.philhosoft.mif.model.MifFileContent;
import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.parser.data.ArcParser;
import org.philhosoft.mif.parser.data.EllipseParser;
import org.philhosoft.mif.parser.data.LineParser;
import org.philhosoft.mif.parser.data.MifDataParser;
import org.philhosoft.mif.parser.data.PointParser;
import org.philhosoft.mif.parser.data.PolylineParser;
import org.philhosoft.mif.parser.data.RectangleParser;
import org.philhosoft.mif.parser.data.RegionParser;
import org.philhosoft.mif.parser.data.RoundedRectangleParser;
import org.philhosoft.mif.parser.data.TextParser;

public class MifFileContentParser
{
	private List<MifDataParser> parsers = new ArrayList<>();

	public MifFileContentParser()
	{
		parsers.add(new RegionParser());
		parsers.add(new PolylineParser());
		parsers.add(new PointParser());
		parsers.add(new TextParser());
		parsers.add(new LineParser());
		parsers.add(new RectangleParser());
		parsers.add(new RoundedRectangleParser());
		parsers.add(new PointParser());
		parsers.add(new EllipseParser());
		parsers.add(new ArcParser());
	}

	public MifFileContent parseContent(ParsingContext context)
	{
		HeaderParser headerParser = new HeaderParser();
		MifFileContent fileContent = headerParser.parse(context);
		if (context.getMessageCollector().hasErrors())
		{
			return fileContent; // Partial content?
		}

		while (context.readNextLine())
		{
			if (context.getCurrentLine().trim().isEmpty())
				continue;
			boolean parsed = false;
			for (MifDataParser parser : parsers)
			{
				if (parser.canParse(context))
				{
					MifData data = parser.parseData(context);
					if (data == null)
						return fileContent; // Error. Give a partial content?

					fileContent.add(data);
					parsed = true;
					break;
				}
			}
			if (!parsed)
			{
				context.addWarning("Unrecognized line: ignored");
			}
		}

		return fileContent;
	}
}
