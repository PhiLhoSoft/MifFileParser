package org.philhosoft.mif.parser.data;


import org.philhosoft.mif.model.data.Arc;
import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.model.parameter.Pen;
import org.philhosoft.mif.parser.MifReader;
import org.philhosoft.mif.parser.parameter.PenParser;


/*
 ARC x1 y1 x2 y2
 a b
 [ PEN (width, pattern, color) ]
 */
public class ArcParser extends FourCoordinatesDataParser implements MifDataParser
{
	public static final String KEYWORD = "ARC";

	@Override
	public String getKeyword()
	{
		return KEYWORD;
	}

	@Override
	public MifData parseData(MifReader reader)
	{
		parseCoordinates(reader);

		Arc mifArc = new Arc(coordinates1, coordinates2);
		if (reader.readNextLine())
		{
			PenParser parser = new PenParser();
			if (parser.canParse(reader))
			{
				Pen pen = (Pen) parser.parseParameter(reader);
				mifArc.setPen(pen);
			}
			else // It is optional
			{
				reader.pushBackLine();
			}
		}

		return mifArc;
	}
}
