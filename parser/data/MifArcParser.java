package org.philhosoft.mif.parser.data;


import org.philhosoft.mif.model.data.MifArc;
import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.model.parameter.MifPen;
import org.philhosoft.mif.parser.MifReader;
import org.philhosoft.mif.parser.parameter.MifPenParser;


/*
 ARC x1 y1 x2 y2
 a b
 [ PEN (width, pattern, color) ]
 */
public class MifArcParser extends MifFourCoordinatesDataParser implements MifDataParser
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

		MifArc mifArc = new MifArc(coordinates1, coordinates2);
		if (reader.readNextLine())
		{
			MifPenParser parser = new MifPenParser();
			if (parser.canParse(reader))
			{
				MifPen pen = (MifPen) parser.parseParameter(reader);
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
