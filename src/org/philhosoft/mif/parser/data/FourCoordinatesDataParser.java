package org.philhosoft.mif.parser.data;


import org.philhosoft.mif.model.parameter.CoordinatePair;
import org.philhosoft.mif.parser.DefaultParser;
import org.philhosoft.mif.parser.ParsingContext;
import org.philhosoft.mif.parser.parameter.CoordinatePairParser;


public abstract class FourCoordinatesDataParser extends DefaultParser
{
	protected CoordinatePair coordinates1;
	protected CoordinatePair coordinates2;

	public void parseCoordinates(ParsingContext context)
	{
		String line = context.getCurrentLine();
		if (line == null)
			throw new IllegalStateException(); // Should not happen!

		String keyword = getKeyword();
		String parameter;
		if (keyword == null)
		{
			parameter = line; // Parameter stands on its own line
		}
		else
		{
			parameter = line.substring(keyword.length() + 1);
		}
		String[] coordinates = parameter.split("\\s+");
		if (coordinates.length != 4)
		{
			context.addError("Invalid " + (keyword == null ? "parameter" : keyword) + ", must have 4 coordinates");
			return;
		}

		coordinates1 = CoordinatePairParser.parseCoordinates(coordinates[0], coordinates[1], context);
		coordinates2 = CoordinatePairParser.parseCoordinates(coordinates[2], coordinates[3], context);
	}
}
