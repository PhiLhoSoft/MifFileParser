package org.philhosoft.mif.parser.data;


import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.model.data.Point;
import org.philhosoft.mif.model.parameter.CoordinatePair;
import org.philhosoft.mif.model.parameter.Symbol;
import org.philhosoft.mif.parser.DefaultParser;
import org.philhosoft.mif.parser.MifReader;
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
	public MifData parseData(MifReader reader)
	{
		String line = reader.getCurrentLine();
		if (line == null)
			throw new IllegalStateException();
		String parameter = line.substring(getKeyword().length() + 1);
		String[] coordinates = parameter.split(" ");
		if (coordinates.length != 2)
		{
			reader.addError("Invalid point, must have 2 coordinates");
			return null;
		}

		CoordinatePair coordinatePair = CoordinatePairParser.parseCoordinates(coordinates[0], coordinates[1], reader);

		Point mifPoint = new Point(coordinatePair);
		if (reader.readNextLine())
		{
			line = reader.getCurrentLine();
			SymbolParser parser = new SymbolParser();
			if (parser.canParse(reader))
			{
				Symbol symbol = (Symbol) parser.parseParameter(reader);
				mifPoint.setSymbol(symbol);
			}
			else
			{
				reader.pushBackLine();
			}
		}

		return mifPoint;
	}
}
