package org.philhosoft.mif.parser.data;


import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.model.data.Text;
import org.philhosoft.mif.parser.DefaultParser;
import org.philhosoft.mif.parser.KeywordLineParser;
import org.philhosoft.mif.parser.ParsingContext;


/*
 TEXT "textstring"
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
	private static final String[] OPTIONS = { "FONT", "SPACING", "JUSTIFY", "ANGLE", "LABEL" };

	private TextCoordinatesParser coordinateParser = new TextCoordinatesParser();
	private KeywordLineParser lineParser = new KeywordLineParser();

	@Override
	public String getKeyword()
	{
		return KEYWORD;
	}

	@Override
	public MifData parseData(ParsingContext context)
	{
		String parameter = readParameter(context);
		Text text;
		if (!parameter.startsWith("\"") || !parameter.endsWith("\""))
		{
			context.addError("Invalid parameter syntax: no quotes");
			text = new Text(parameter); // Still give what is found
		}
		else
		{
			String textParameter = parameter.substring(1, parameter.length() - 1);
			text = new Text(textParameter);
		}

		if (context.readNextLine())
		{
			coordinateParser.parseCoordinates(context);
			text.setCorner1(coordinateParser.coordinates1);
			text.setCorner2(coordinateParser.coordinates2);
		}
		else
		{
			context.addError("Invalid text: no coordinates");
		}

		// Currently just skip / ignore the options
		lineParser.skipKnownKeywordLines(OPTIONS, context);

		return text;
	}

	/** Allows to parse four coordinates on a line without keyword. */
	private class TextCoordinatesParser extends FourCoordinatesDataParser
	{
		@Override
		public String getKeyword()
		{
			return null;
		}
	}
}
