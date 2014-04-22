package org.philhosoft.mif.parser.data;


import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.model.data.Text;
import org.philhosoft.mif.parser.DefaultParser;
import org.philhosoft.mif.parser.MifReader;


/*
 TEXT ”textstring”
 x1 y1 x2 y2
 [ FONT... ]
 [ Spacing {1.0 | 1.5 | 2.0} ]
 [ Justify {Left | Center | Right} ]
 [ Angle text_angle]
 [ Label Line {simple | arrow} x y ]
 */
public class TextParser extends DefaultParser implements MifDataParser
{
	public static final String KEYWORD = "TEXT";

	@Override
	public String getKeyword()
	{
		return KEYWORD;
	}

	@Override
	public MifData parseData(MifReader reader)
	{
		return new Text(""); // TODO
	}
}
