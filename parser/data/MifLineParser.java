package org.philhosoft.mif.parser.data;


import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.model.data.MifLine;
import org.philhosoft.mif.model.parameter.MifPen;
import org.philhosoft.mif.parser.MifReader;
import org.philhosoft.mif.parser.parameter.MifPenParser;


/*
 LINE x1 y1 x2 y2
 [ PEN (width, pattern, color) ]
 */
public class MifLineParser extends MifFourCoordinatesDataParser implements MifDataParser
{
	public static final String KEYWORD = "LINE";

	@Override
	public String getKeyword()
	{
		return KEYWORD;
	}

	@Override
	public MifData parseData(MifReader reader)
	{
		parseCoordinates(reader);

		MifLine mifLine = new MifLine(coordinates1, coordinates2);
		if (reader.readNextLine())
		{
			MifPenParser parser = new MifPenParser();
			if (parser.canParse(reader))
			{
				MifPen pen = (MifPen) parser.parseParameter(reader);
				mifLine.setPen(pen);
			}
			else // It is optional
			{
				reader.pushBackLine();
			}
		}

		return mifLine;
	}
}
