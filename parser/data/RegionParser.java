package org.philhosoft.mif.parser.data;


import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.model.data.Region;
import org.philhosoft.mif.model.parameter.Brush;
import org.philhosoft.mif.model.parameter.CoordinatePair;
import org.philhosoft.mif.model.parameter.Pen;
import org.philhosoft.mif.parser.DefaultParser;
import org.philhosoft.mif.parser.MifReader;
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
	public MifData parseData(MifReader reader)
	{
		Region mifRegion = new Region();

		// Read data
		String line = reader.getCurrentLine();
		if (line == null)
			throw new IllegalStateException("Expected line at " + reader.getCurrentLineNumber() + " for " + getKeyword());
		String parameter = line.substring(getKeyword().length() + 1);
		int polygonNumber = 0;
		try
		{
			polygonNumber = Integer.valueOf(parameter);
		}
		catch (NumberFormatException e)
		{
			reader.addError("Invalid number of polygons for " + getKeyword());
			return mifRegion;
		}

		for (int i = 0; i < polygonNumber; i++)
		{
			readPolygon(mifRegion, reader);
		}

		// Read options
		while (reader.readNextLine() && parseOption(mifRegion, reader))
		{}

		return mifRegion;
	}

	private void readPolygon(Region mifRegion, MifReader reader)
	{
		reader.readNextLine();
		String line = reader.getCurrentLine();
		int coordinateNb = 0;
		try
		{
			coordinateNb = Integer.valueOf(line);
		}
		catch (NumberFormatException e)
		{
			reader.addError("Invalid number of coordinates for " + getKeyword());
			return;
		}
		mifRegion.addPolygon();
		for (int i = 0; i < coordinateNb; i++)
		{
			reader.readNextLine();
			CoordinatePair coordinates = (CoordinatePair) coordinatesParser.parseParameter(reader);
			mifRegion.addCoordinates(coordinates);
		}
	}

	private boolean parseOption(Region mifRegion, MifReader reader)
	{
		if (penParser.canParse(reader))
		{
			Pen pen = (Pen) penParser.parseParameter(reader);
			mifRegion.setPen(pen);
			return true;
		}
		if (brushParser.canParse(reader))
		{
			Brush brush = (Brush) brushParser.parseParameter(reader);
			mifRegion.setBrush(brush);
			return true;
		}
		if (centerParser.canParse(reader))
		{
			CoordinatePair center = (CoordinatePair) centerParser.parseParameter(reader);
			mifRegion.setCenter(center);
			return true;
		}
		reader.pushBackLine();
		return false;
	}
}
