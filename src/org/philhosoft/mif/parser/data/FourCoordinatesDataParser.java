package org.philhosoft.mif.parser.data;


import org.philhosoft.mif.model.parameter.CoordinatePair;
import org.philhosoft.mif.parser.DefaultParser;
import org.philhosoft.mif.parser.ParsingContext;
import org.philhosoft.mif.parser.parameter.CoordinatePairParser;


public abstract class FourCoordinatesDataParser extends DefaultParser
{
	protected CoordinatePair coordinates1;
	protected CoordinatePair coordinates2;

	/**
	 * Parses the current line to extract four coordinates.
	 * Skips the keyword, if not null.
	 */
	public void parseCoordinates(ParsingContext context)
	{
		String keyword = getKeyword();
		String parameter;
		if (keyword != null)
		{
			parameter = readParameter(context);
		}
		else
		{
			parameter = context.getCurrentLine();
			if (parameter == null)
				parameter = "";
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
