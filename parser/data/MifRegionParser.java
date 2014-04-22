package org.philhosoft.mif.parser.data;


import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.model.data.MifRegion;
import org.philhosoft.mif.model.parameter.MifBrush;
import org.philhosoft.mif.model.parameter.MifCoordinatePair;
import org.philhosoft.mif.model.parameter.MifPen;
import org.philhosoft.mif.parser.MifDefaultParser;
import org.philhosoft.mif.parser.MifReader;
import org.philhosoft.mif.parser.parameter.MifBrushParser;
import org.philhosoft.mif.parser.parameter.MifCenterParser;
import org.philhosoft.mif.parser.parameter.MifCoordinatePairParser;
import org.philhosoft.mif.parser.parameter.MifPenParser;


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
public class MifRegionParser extends MifDefaultParser implements MifDataParser
{
	public static final String KEYWORD = "REGION";

	private MifCoordinatePairParser coordinatesParser = new MifCoordinatePairParser();
	private MifPenParser penParser = new MifPenParser();
	private MifBrushParser brushParser = new MifBrushParser();
	private MifCenterParser centerParser = new MifCenterParser();

	@Override
	public String getKeyword()
	{
		return KEYWORD;
	}

	@Override
	public MifData parseData(MifReader reader)
	{
		MifRegion mifRegion = new MifRegion();

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

	private void readPolygon(MifRegion mifRegion, MifReader reader)
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
			MifCoordinatePair coordinates = (MifCoordinatePair) coordinatesParser.parseParameter(reader);
			mifRegion.addCoordinates(coordinates);
		}
	}

	private boolean parseOption(MifRegion mifRegion, MifReader reader)
	{
		if (penParser.canParse(reader))
		{
			MifPen pen = (MifPen) penParser.parseParameter(reader);
			mifRegion.setPen(pen);
			return true;
		}
		if (brushParser.canParse(reader))
		{
			MifBrush brush = (MifBrush) brushParser.parseParameter(reader);
			mifRegion.setBrush(brush);
			return true;
		}
		if (centerParser.canParse(reader))
		{
			MifCoordinatePair center = (MifCoordinatePair) centerParser.parseParameter(reader);
			mifRegion.setCenter(center);
			return true;
		}
		reader.pushBackLine();
		return false;
	}
}
