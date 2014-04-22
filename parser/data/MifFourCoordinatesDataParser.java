package org.philhosoft.mif.parser.data;


import org.philhosoft.mif.model.parameter.MifCoordinatePair;
import org.philhosoft.mif.parser.MifDefaultParser;
import org.philhosoft.mif.parser.MifReader;
import org.philhosoft.mif.parser.parameter.MifCoordinatePairParser;


public abstract class MifFourCoordinatesDataParser extends MifDefaultParser
{
	protected MifCoordinatePair coordinates1;
	protected MifCoordinatePair coordinates2;

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

		coordinates1 = MifCoordinatePairParser.parseCoordinates(coordinates[0], coordinates[1], reader);
		coordinates2 = MifCoordinatePairParser.parseCoordinates(coordinates[2], coordinates[3], reader);
	}
}
