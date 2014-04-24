package org.philhosoft.mif.parser.parameter;


import org.philhosoft.mif.model.parameter.Brush;
import org.philhosoft.mif.model.parameter.MifDataParameter;
import org.philhosoft.mif.parser.DefaultParser;
import org.philhosoft.mif.parser.ParsingContext;


public class BrushParser extends DefaultParser implements ParameterParser
{
	public static final String KEYWORD = "BRUSH";

	@Override
	public String getKeyword()
	{
		return KEYWORD;
	}

	@Override
	public MifDataParameter parseParameter(ParsingContext context)
	{
		String line = context.getCurrentLine();
		if (line == null)
			throw new IllegalStateException();

		String parameter = line.substring(getKeyword().length() + 1);
		ParameterTripletParser triplet = new ParameterTripletParser(parameter, context);

		return new Brush(triplet.getParameter1(), triplet.getParameter2(), triplet.getParameter3());
	}
}
