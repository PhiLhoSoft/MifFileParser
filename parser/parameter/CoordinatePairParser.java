package org.philhosoft.mif.parser.parameter;


import org.philhosoft.mif.model.parameter.CoordinatePair;
import org.philhosoft.mif.model.parameter.MifDataParameter;
import org.philhosoft.mif.parser.MifReader;


public class CoordinatePairParser implements ParameterParser
{
	@Override
	public String getKeyword()
	{
		return "";
	}

	@Override
	public boolean canParse(MifReader reader)
	{
		return reader.getCurrentLine().contains(" ");
	}

	/**
	 * Reads two coordinates standing alone on their line.
	 */
	@Override
	public MifDataParameter parseParameter(MifReader reader)
	{
		String line = reader.getCurrentLine();
		if (line == null)
			throw new IllegalStateException();
		String[] coordinates = line.split(" +");
		if (coordinates.length != 2)
		{
			reader.addError("Invalid coordinate line, must have 2 coordinates");
			return null;
		}
		return parseCoordinates(coordinates[0], coordinates[1], reader);
	}

	/**
	 * Transforms two coordinates given as string to a coordinate pair.
	 *
	 * @param coordinateX  X coordinate
	 * @param coordinateY  Y coordinate
	 * @param reader  the reader used to record errors, if any
	 * @return a coordinate pair
	 */
	public static CoordinatePair parseCoordinates(String coordinateX, String coordinateY, MifReader reader)
	{
		double x = 0, y = 0;
		try
		{
			x = Double.parseDouble(coordinateX);
		}
		catch (NumberFormatException e)
		{
			reader.addError("Invalid X coordinate");
		}
		try
		{
			y = Double.parseDouble(coordinateY);
		}
		catch (NumberFormatException e)
		{
			reader.addError("Invalid Y coordinate");
		}
		return new CoordinatePair(x, y);
	}
}
