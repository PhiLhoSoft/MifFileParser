package org.philhosoft.mif.parser.parameter;


import org.philhosoft.mif.model.parameter.Pen;
import org.philhosoft.mif.parser.DefaultParser;
import org.philhosoft.mif.parser.ParsingContext;


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
		ParameterTripletParser triplet = new ParameterTripletParser(parameter, context);

		return new Pen(triplet.getParameter1(), triplet.getParameter2(), triplet.getParameter3());
	}
}
