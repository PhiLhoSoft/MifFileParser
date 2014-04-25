package org.philhosoft.mif.parser.data;


import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.model.data.Region;
import org.philhosoft.mif.model.parameter.Brush;
import org.philhosoft.mif.model.parameter.CoordinatePair;
import org.philhosoft.mif.model.parameter.Pen;
import org.philhosoft.mif.parser.DefaultParser;
import org.philhosoft.mif.parser.ParsingContext;
import org.philhosoft.mif.parser.parameter.BrushParser;
import org.philhosoft.mif.parser.parameter.CenterParser;
import org.philhosoft.mif.parser.parameter.CoordinatePairParser;
import org.philhosoft.mif.parser.parameter.PenParser;


/*
 REGION numpolygons
   numpts1
 x1 y1
 x2 y2
 :
 [  numpts2
 x1 y1
 x2 y2 ]
 :
 [ PEN (width, pattern, color) ]
 [ BRUSH (pattern, forecolor, backcolor) ]
 [ CENTER x y ]
 */
public class RegionParser extends DefaultParser implements MifDataParser
{
	public static final String KEYWORD = "REGION";

	private CoordinatePairParser coordinatesParser = new CoordinatePairParser();
	private PenParser penParser = new PenParser();
	private BrushParser brushParser = new BrushParser();
	private CenterParser centerParser = new CenterParser();

	@Override
	public String getKeyword()
	{
		return KEYWORD;
	}

	@Override
	public MifData parseData(ParsingContext context)
	{
		Region region = new Region();

		// Read data
		String line = context.getCurrentLine();
		if (line == null)
			throw new IllegalStateException();

		String parameter = line.substring(getKeyword().length() + 1);
		int polygonNumber = 0;
		try
		{
			polygonNumber = Integer.valueOf(parameter);
		}
		catch (NumberFormatException e)
		{
			context.addError("Invalid number of polygons for " + getKeyword());
			return region;
		}

		for (int i = 0; i < polygonNumber; i++)
		{
			readPolygon(region, context);
		}

		// Read options
		while (context.readNextLine() && parseOption(region, context))
		{}

		return region;
	}

	private void readPolygon(Region region, ParsingContext context)
	{
		context.readNextLine();
		String line = context.getCurrentLine();
		int coordinateNb = 0;
		try
		{
			coordinateNb = Integer.valueOf(line);
		}
		catch (NumberFormatException e)
		{
			context.addError("Invalid number of coordinates for " + getKeyword());
			return;
		}
		region.addPolygon();
		for (int i = 0; i < coordinateNb; i++)
		{
			context.readNextLine();
			CoordinatePair coordinates = (CoordinatePair) coordinatesParser.parseParameter(context);
			region.addCoordinates(coordinates);
		}
	}

	private boolean parseOption(Region region, ParsingContext context)
	{
		if (penParser.canParse(context))
		{
			Pen pen = penParser.parseParameter(context);
			region.setPen(pen);
			return true;
		}
		if (brushParser.canParse(context))
		{
			Brush brush = brushParser.parseParameter(context);
			region.setBrush(brush);
			return true;
		}
		if (centerParser.canParse(context))
		{
			CoordinatePair center = centerParser.parseParameter(context);
			region.setCenter(center);
			return true;
		}
		context.pushBackLine();
		return false;
	}
}
