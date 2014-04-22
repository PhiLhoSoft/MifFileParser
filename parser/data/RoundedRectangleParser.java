package org.philhosoft.mif.parser.data;


import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.model.data.RoundedRectangle;
import org.philhosoft.mif.model.parameter.Brush;
import org.philhosoft.mif.model.parameter.Pen;
import org.philhosoft.mif.parser.MifReader;
import org.philhosoft.mif.parser.parameter.BrushParser;
import org.philhosoft.mif.parser.parameter.PenParser;


/*
 ROUNDRECT x1 y1 x2 y2
 a
 [ PEN (width, pattern, color) ]
 [ BRUSH (pattern, forecolor, backcolor) ]
 */
public class RoundedRectangleParser extends FourCoordinatesDataParser implements MifDataParser
{
	public static final String KEYWORD = "ROUNDRECT";

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

		RoundedRectangle mifRoundedRectangle = new RoundedRectangle(coordinates1, coordinates2);

		reader.readNextLine();
		String line = reader.getCurrentLine();
		double rounding = 0;
		try
		{
			rounding = Double.parseDouble(line);
		}
		catch (NumberFormatException e)
		{
			reader.addError("Invalid rounded rectangle degree of rounding");
		}
		mifRoundedRectangle.setDegreeOfRounding(rounding);

		while (reader.readNextLine() && parseOption(mifRoundedRectangle, reader))
		{}

		return mifRoundedRectangle;
	}

	private boolean parseOption(RoundedRectangle mifRoundedRectangle, MifReader reader)
	{
		if (penParser.canParse(reader))
		{
			Pen pen = (Pen) penParser.parseParameter(reader);
			mifRoundedRectangle.setPen(pen);
			return true;
		}
		if (brushParser.canParse(reader))
		{
			Brush brush = (Brush) brushParser.parseParameter(reader);
			mifRoundedRectangle.setBrush(brush);
			return true;
		}
		reader.pushBackLine();
		return false;
	}
}
