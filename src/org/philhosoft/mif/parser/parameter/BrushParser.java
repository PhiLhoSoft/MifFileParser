package org.philhosoft.mif.parser.parameter;


import org.philhosoft.mif.model.parameter.Brush;
import org.philhosoft.mif.parser.DefaultParser;
import org.philhosoft.mif.parser.ParsingContext;


/* BRUSH (pattern, forecolor[, backcolor]) */
public class BrushParser extends DefaultParser implements ParameterParser
{
	public static final String KEYWORD = "BRUSH";

	@Override
	public String getKeyword()
	{
		return KEYWORD;
	}

	@Override
	public Brush parseParameter(ParsingContext context)
	{
		String line = context.getCurrentLine();
		if (line == null)
			throw new IllegalStateException();

		String parameter = line.substring(getKeyword().length() + 1);
		ParenthesizedParameterParser triplet = new ParenthesizedParameterParser(parameter, context);
		Brush brush;
		if (triplet.getParameterNumber() == 3)
		{
			brush = new Brush(triplet.getIntParameter(0), triplet.getIntParameter(1), triplet.getIntParameter(2));
		}
		else if (triplet.getParameterNumber() == 2)
		{
			brush = new Brush(triplet.getIntParameter(0), triplet.getIntParameter(1), null);
		}
		else
		{
			context.addError("Wrong number of parameters for " + KEYWORD);
			brush = new Brush(0, 0, 0);
		}

		return brush;
	}
}
