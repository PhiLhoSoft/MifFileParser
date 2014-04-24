package org.philhosoft.mif.parser.data;


import org.philhosoft.mif.model.data.Arc;
import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.model.parameter.Pen;
import org.philhosoft.mif.parser.ParsingContext;
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
	public MifData parseData(ParsingContext context)
	{
		parseCoordinates(context);

		Arc mifArc = new Arc(coordinates1, coordinates2);
		if (context.readNextLine())
		{
			PenParser parser = new PenParser();
			if (parser.canParse(context))
			{
				Pen pen = (Pen) parser.parseParameter(context);
				mifArc.setPen(pen);
			}
			else // It is optional
			{
				context.pushBackLine();
			}
		}

		return mifArc;
	}
}
