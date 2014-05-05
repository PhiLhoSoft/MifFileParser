package org.philhosoft.mif.parser.parameter;


import org.philhosoft.mif.model.parameter.CoordinatePair;
import org.philhosoft.mif.parser.DefaultParser;
import org.philhosoft.mif.parser.ParsingContext;


/* CENTER x y */
public class CenterParser extends DefaultParser implements ParameterParser
{
	public static final String KEYWORD = "CENTER";

	@Override
	public String getKeyword()
	{
		return KEYWORD;
	}

	@Override
	public CoordinatePair parseParameter(ParsingContext context)
	{
		String line = context.getCurrentLine();
		if (line == null)
			throw new IllegalStateException();

		String parameter = line.substring(getKeyword().length() + 1);
		String[] coordinates = parameter.split("\\s+");
		if (coordinates.length != 2)
		{
			context.addError("Invalid center, must have 2 coordinates");
			return null;
		}

		return CoordinatePairParser.parseCoordinates(coordinates[0], coordinates[1], context);
	}
}
