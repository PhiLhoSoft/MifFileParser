package org.philhosoft.mif.parser.data;


import org.philhosoft.mif.model.data.Line;
import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.model.parameter.Pen;
import org.philhosoft.mif.parser.ParsingContext;
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
	public MifData parseData(ParsingContext context)
	{
		parseCoordinates(context);

		Line line = new Line(coordinates1, coordinates2);
		if (context.readNextLine())
		{
			PenParser parser = new PenParser();
			if (parser.canParse(context))
			{
				Pen pen = parser.parseParameter(context);
				line.setPen(pen);
			}
			else // It is optional
			{
				context.pushBackLine();
			}
		}

		return line;
	}
}
