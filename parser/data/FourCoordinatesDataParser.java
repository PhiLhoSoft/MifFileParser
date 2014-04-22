package org.philhosoft.mif.parser.data;


import org.philhosoft.mif.model.parameter.CoordinatePair;
import org.philhosoft.mif.parser.DefaultParser;
import org.philhosoft.mif.parser.MifReader;
import org.philhosoft.mif.parser.parameter.CoordinatePairParser;


public abstract class FourCoordinatesDataParser extends DefaultParser
{
	protected CoordinatePair coordinates1;
	protected CoordinatePair coordinates2;

	public void parseCoordinates(MifReader reader)
	{
		String line = reader.getCurrentLine();
		if (line == null)
			throw new IllegalStateException("Expected line at " + reader.getCurrentLineNumber() + " for " + getKeyword());
		String parameter = line.substring(getKeyword().length() + 1);
		String[] coordinates = parameter.split("\\s+");
		if (coordinates.length != 4)
		{
			reader.addError("Invalid " + getKeyword() + ", must have 4 coordinates");
			return;
		}

		coordinates1 = CoordinatePairParser.parseCoordinates(coordinates[0], coordinates[1], reader);
		coordinates2 = CoordinatePairParser.parseCoordinates(coordinates[2], coordinates[3], reader);
	}
}
