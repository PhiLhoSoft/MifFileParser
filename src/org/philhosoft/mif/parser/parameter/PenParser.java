package org.philhosoft.mif.parser.parameter;


import org.philhosoft.mif.model.parameter.Pen;
import org.philhosoft.mif.parser.DefaultParser;
import org.philhosoft.mif.parser.ParsingContext;


/* PEN (width, pattern, color) */
public class PenParser extends DefaultParser implements ParameterParser
{
	public static final String KEYWORD = "PEN";

	@Override
	public String getKeyword()
	{
		return KEYWORD;
	}

	@Override
	public Pen parseParameter(ParsingContext context)
	{
		String line = context.getCurrentLine();
		if (line == null)
			throw new IllegalStateException(); // Should not happen!

		String parameter = line.substring(getKeyword().length() + 1);
		ParenthesizedParameterParser triplet = new ParenthesizedParameterParser(parameter, context);
		if (triplet.getParameterNumber() != 3)
		{
			context.addError("Wrong number of parameters for " + KEYWORD);
			return new Pen(0, 0, 0);
		}

		return new Pen(triplet.getIntParameter(0), triplet.getIntParameter(1), triplet.getIntParameter(2));
	}
}
