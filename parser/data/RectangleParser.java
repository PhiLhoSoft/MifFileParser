package org.philhosoft.mif.parser.data;

import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.model.data.Rectangle;
import org.philhosoft.mif.model.parameter.Brush;
import org.philhosoft.mif.model.parameter.Pen;
import org.philhosoft.mif.parser.MifReader;
import org.philhosoft.mif.parser.parameter.BrushParser;
import org.philhosoft.mif.parser.parameter.PenParser;

/*
RECT x1 y1 x2 y2
[ PEN (width, pattern, color) ]
[ BRUSH (pattern, forecolor, backcolor) ]
*/
public class RectangleParser extends FourCoordinatesDataParser implements MifDataParser
{
	public static final String KEYWORD = "RECT";

	private PenParser penParser = new PenParser();
	private BrushParser brushParser = new BrushParser();

	@Override
	public String getKeyword()
	{
		return KEYWORD;
	}

	@Override
	public MifData parseData(MifReader reader)
	{
		parseCoordinates(reader);

		Rectangle mifRectangle = new Rectangle(coordinates1, coordinates2);
		while (reader.readNextLine() && parseOption(mifRectangle, reader))
		{}

		return mifRectangle;
	}

	private boolean parseOption(Rectangle mifRectangle, MifReader reader)
	{
		if (penParser.canParse(reader))
		{
			Pen pen = (Pen) penParser.parseParameter(reader);
			mifRectangle.setPen(pen);
			return true;
		}
		if (brushParser.canParse(reader))
		{
			Brush brush = (Brush) brushParser.parseParameter(reader);
			mifRectangle.setBrush(brush);
			return true;
		}
		reader.pushBackLine();
		return false;
	}
}
