package org.philhosoft.mif.parser.parameter;


import org.philhosoft.mif.model.parameter.CoordinatePair;
import org.philhosoft.mif.parser.ParsingContext;


public class CoordinatePairParser implements ParameterParser
{
	@Override
	public String getKeyword()
	{
		return "";
	}

	@Override
	public boolean canParse(ParsingContext context)
	{
		return context.getCurrentLine().contains(" ") || context.getCurrentLine().contains("\t");
	}

	/**
	 * Reads two coordinates standing alone on their line.
	 */
	@Override
	public CoordinatePair parseParameter(ParsingContext context)
	{
		String line = context.getCurrentLine();
		if (line == null)
			throw new IllegalStateException(); // Should not happen

		String[] coordinates = line.split("\\s+");
		if (coordinates.length != 2)
		{
			context.addError("Invalid coordinate line, must have 2 coordinates");
			return null;
		}
		return parseCoordinates(coordinates[0], coordinates[1], context);
	}

	/**
	 * Transforms two coordinates given as string to a coordinate pair.
	 *
	 * @param coordinateX  X coordinate
	 * @param coordinateY  Y coordinate
	 * @param context  the context used to record errors, if any
	 * @return a coordinate pair
	 */
	public static CoordinatePair parseCoordinates(String coordinateX, String coordinateY, ParsingContext context)
	{
		double x = 0, y = 0;
		try
		{
			x = Double.parseDouble(coordinateX);
		}
		catch (NumberFormatException e)
		{
			context.addError("Invalid X coordinate");
		}
		try
		{
			y = Double.parseDouble(coordinateY);
		}
		catch (NumberFormatException e)
		{
			context.addError("Invalid Y coordinate");
		}
		return new CoordinatePair(x, y);
	}
}
