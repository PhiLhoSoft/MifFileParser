package org.philhosoft.mif.parser.data;


import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.model.data.MifPoint;
import org.philhosoft.mif.model.parameter.MifCoordinatePair;
import org.philhosoft.mif.model.parameter.MifSymbol;
import org.philhosoft.mif.parser.MifDefaultParser;
import org.philhosoft.mif.parser.MifReader;
import org.philhosoft.mif.parser.parameter.MifCoordinatePairParser;
import org.philhosoft.mif.parser.parameter.MifSymbolParser;


/*
 POINT x y
 [ SYMBOL (shape, color, size) ]
 */
public class MifPointParser extends MifDefaultParser implements MifDataParser
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

		MifCoordinatePair coordinatePair = MifCoordinatePairParser.parseCoordinates(coordinates[0], coordinates[1], reader);

		MifPoint mifPoint = new MifPoint(coordinatePair);
		if (reader.readNextLine())
		{
			line = reader.getCurrentLine();
			MifSymbolParser parser = new MifSymbolParser();
			if (parser.canParse(reader))
			{
				MifSymbol symbol = (MifSymbol) parser.parseParameter(reader);
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
