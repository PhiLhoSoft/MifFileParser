package org.philhosoft.mif.parser.data;


import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.model.data.RoundedRectangle;
import org.philhosoft.mif.model.parameter.Brush;
import org.philhosoft.mif.model.parameter.Pen;
import org.philhosoft.mif.parser.ParsingContext;
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
	public MifData parseData(ParsingContext context)
	{
		parseCoordinates(context);

		RoundedRectangle mifRoundedRectangle = new RoundedRectangle(coordinates1, coordinates2);

		context.readNextLine();
		String line = context.getCurrentLine();
		double rounding = 0;
		try
		{
			rounding = Double.parseDouble(line);
		}
		catch (NumberFormatException e)
		{
			context.addError("Invalid rounded rectangle degree of rounding");
		}
		mifRoundedRectangle.setDegreeOfRounding(rounding);

		while (context.readNextLine() && parseOption(mifRoundedRectangle, context))
		{}

		return mifRoundedRectangle;
	}

	private boolean parseOption(RoundedRectangle mifRoundedRectangle, ParsingContext context)
	{
		if (penParser.canParse(context))
		{
			Pen pen = (Pen) penParser.parseParameter(context);
			mifRoundedRectangle.setPen(pen);
			return true;
		}
		if (brushParser.canParse(context))
		{
			Brush brush = (Brush) brushParser.parseParameter(context);
			mifRoundedRectangle.setBrush(brush);
			return true;
		}
		context.pushBackLine();
		return false;
	}
}
