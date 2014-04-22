package org.philhosoft.mif.parser.data;

import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.model.data.MifRectangle;
import org.philhosoft.mif.model.parameter.MifBrush;
import org.philhosoft.mif.model.parameter.MifPen;
import org.philhosoft.mif.parser.MifReader;
import org.philhosoft.mif.parser.parameter.MifBrushParser;
import org.philhosoft.mif.parser.parameter.MifPenParser;

/*
RECT x1 y1 x2 y2
[ PEN (width, pattern, color) ]
[ BRUSH (pattern, forecolor, backcolor) ]
*/
public class MifRectangleParser extends MifFourCoordinatesDataParser implements MifDataParser
{
	public static final String KEYWORD = "RECT";

	private MifPenParser penParser = new MifPenParser();
	private MifBrushParser brushParser = new MifBrushParser();

	@Override
	public String getKeyword()
	{
		return KEYWORD;
	}

	@Override
	public MifData parseData(MifReader reader)
	{
		parseCoordinates(reader);

		MifRectangle mifRectangle = new MifRectangle(coordinates1, coordinates2);
		while (reader.readNextLine() && parseOption(mifRectangle, reader))
		{}

		return mifRectangle;
	}

	private boolean parseOption(MifRectangle mifRectangle, MifReader reader)
	{
		if (penParser.canParse(reader))
		{
			MifPen pen = (MifPen) penParser.parseParameter(reader);
			mifRectangle.setPen(pen);
			return true;
		}
		if (brushParser.canParse(reader))
		{
			MifBrush brush = (MifBrush) brushParser.parseParameter(reader);
			mifRectangle.setBrush(brush);
			return true;
		}
		reader.pushBackLine();
		return false;
	}
}
