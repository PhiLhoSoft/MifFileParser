package org.philhosoft.mif.parser.data;


import org.philhosoft.mif.model.data.Arc;
import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.model.parameter.CoordinatePair;
import org.philhosoft.mif.model.parameter.Pen;
import org.philhosoft.mif.parser.ParsingContext;
import org.philhosoft.mif.parser.parameter.CoordinatePairParser;
import org.philhosoft.mif.parser.parameter.PenParser;


/*
 ARC x1 y1 x2 y2
 a b
 [ PEN (width, pattern, color) ]
 */
public class ArcParser extends FourCoordinatesDataParser implements MifDataParser
{
	public static final String KEYWORD = "ARC";

	private CoordinatePairParser pairParser = new CoordinatePairParser();

	@Override
	public String getKeyword()
	{
		return KEYWORD;
	}

	@Override
	public MifData parseData(ParsingContext context)
	{
		parseCoordinates(context);

		Arc arc = new Arc(coordinates1, coordinates2);
		// Abusing a bit this parser...
		if (context.readNextLine() && pairParser.canParse(context))
		{
			CoordinatePair angles = pairParser.parseParameter(context);
			arc.setStartAngle(angles.getX());
			arc.setEndAngle(angles.getY());
		}
		else
		{
			context.addError("Arc syntax error, missing start and end angles");
			return arc;
		}

		if (context.readNextLine())
		{
			PenParser parser = new PenParser();
			if (parser.canParse(context))
			{
				Pen pen = parser.parseParameter(context);
				arc.setPen(pen);
			}
			else // It is optional
			{
				context.pushBackLine();
			}
		}

		return arc;
	}
}
