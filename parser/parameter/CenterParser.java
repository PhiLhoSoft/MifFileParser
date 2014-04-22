package org.philhosoft.mif.parser.parameter;


import org.philhosoft.mif.model.parameter.MifDataParameter;
import org.philhosoft.mif.parser.DefaultParser;
import org.philhosoft.mif.parser.MifReader;


public class CenterParser extends DefaultParser implements ParameterParser
{
	public static final String KEYWORD = "CENTER";

	@Override
	public String getKeyword()
	{
		return KEYWORD;
	}

	@Override
	public MifDataParameter parseParameter(MifReader reader)
	{
		String line = reader.getCurrentLine();
		if (line == null)
			throw new IllegalStateException();
		String parameter = line.substring(getKeyword().length() + 1);
		String[] coordinates = parameter.split(" ");
		if (coordinates.length != 2)
		{
			reader.addError("Invalid center, must have 2 coordinates");
			return null;
		}

		return CoordinatePairParser.parseCoordinates(coordinates[0], coordinates[1], reader);
	}
}
