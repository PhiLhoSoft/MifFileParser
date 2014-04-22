package org.philhosoft.mif.parser.data;


import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.model.data.Line;
import org.philhosoft.mif.model.parameter.Pen;
import org.philhosoft.mif.parser.MifReader;
import org.philhosoft.mif.parser.parameter.PenParser;


/*
 LINE x1 y1 x2 y2
 [ PEN (width, pattern, color) ]
 */
public class LineParser extends FourCoordinatesDataParser implements MifDataParser
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

		Line mifLine = new Line(coordinates1, coordinates2);
		if (reader.readNextLine())
		{
			PenParser parser = new PenParser();
			if (parser.canParse(reader))
			{
				Pen pen = (Pen) parser.parseParameter(reader);
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
