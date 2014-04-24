package org.philhosoft.mif.parser.data;


import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.model.data.Point;
import org.philhosoft.mif.model.parameter.CoordinatePair;
import org.philhosoft.mif.model.parameter.Symbol;
import org.philhosoft.mif.parser.DefaultParser;
import org.philhosoft.mif.parser.ParsingContext;
import org.philhosoft.mif.parser.parameter.CoordinatePairParser;
import org.philhosoft.mif.parser.parameter.SymbolParser;


/*
 POINT x y
 [ SYMBOL (shape, color, size) ]
 */
public class PointParser extends DefaultParser implements MifDataParser
{
	public static final String KEYWORD = "POINT";

	@Override
	public String getKeyword()
	{
		return KEYWORD;
	}

	@Override
	public MifData parseData(ParsingContext context)
	{
		String line = context.getCurrentLine();
		if (line == null)
			throw new IllegalStateException();
		String parameter = line.substring(getKeyword().length() + 1);
		String[] coordinates = parameter.split(" ");
		if (coordinates.length != 2)
		{
			context.addError("Invalid point, must have 2 coordinates");
			return null;
		}

		CoordinatePair coordinatePair = CoordinatePairParser.parseCoordinates(coordinates[0], coordinates[1], context);

		Point mifPoint = new Point(coordinatePair);
		if (context.readNextLine())
		{
			line = context.getCurrentLine();
			SymbolParser parser = new SymbolParser();
			if (parser.canParse(context))
			{
				Symbol symbol = (Symbol) parser.parseParameter(context);
				mifPoint.setSymbol(symbol);
			}
			else
			{
				context.pushBackLine();
			}
		}

		return mifPoint;
	}
}
