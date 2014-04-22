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
import org.philhosoft.mif.parser.parameter.MifPenParser;


/*
 REGION numpolygons
 numpts1
 x1 y1
 x2 y2
 :
 [ numpts2
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

		// Read options
		while (reader.readNextLine() && parseOption(mifRegion, reader))
		{}

		return mifRegion;
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
