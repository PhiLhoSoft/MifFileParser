package org.philhosoft.mif.parser.data;


import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.model.data.MifRoundedRectangle;
import org.philhosoft.mif.model.parameter.MifBrush;
import org.philhosoft.mif.model.parameter.MifPen;
import org.philhosoft.mif.parser.MifReader;
import org.philhosoft.mif.parser.parameter.MifBrushParser;
import org.philhosoft.mif.parser.parameter.MifPenParser;


/*
 ROUNDRECT x1 y1 x2 y2
 a
 [ PEN (width, pattern, color) ]
 [ BRUSH (pattern, forecolor, backcolor) ]
 */
public class MifRoundedRectangleParser extends MifFourCoordinatesDataParser implements MifDataParser
{
	public static final String KEYWORD = "ROUNDRECT";

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

		MifRoundedRectangle mifRoundedRectangle = new MifRoundedRectangle(coordinates1, coordinates2);
		while (reader.readNextLine() && parseOption(mifRoundedRectangle, reader))
		{}

		return mifRoundedRectangle;
	}

	private boolean parseOption(MifRoundedRectangle mifRoundedRectangle, MifReader reader)
	{
		if (penParser.canParse(reader))
		{
			MifPen pen = (MifPen) penParser.parseParameter(reader);
			mifRoundedRectangle.setPen(pen);
			return true;
		}
		if (brushParser.canParse(reader))
		{
			MifBrush brush = (MifBrush) brushParser.parseParameter(reader);
			mifRoundedRectangle.setBrush(brush);
			return true;
		}
		reader.pushBackLine();
		return false;
	}
}
