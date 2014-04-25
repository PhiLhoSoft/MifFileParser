package org.philhosoft.mif.parser.parameter;


import org.philhosoft.mif.model.parameter.Symbol;
import org.philhosoft.mif.parser.DefaultParser;
import org.philhosoft.mif.parser.ParsingContext;


public class SymbolParser extends DefaultParser implements ParameterParser
{
	public static final String KEYWORD = "SYMBOL";

	@Override
	public String getKeyword()
	{
		return KEYWORD;
	}

	@Override
	public Symbol parseParameter(ParsingContext context)
	{
		String line = context.getCurrentLine();
		if (line == null)
			throw new IllegalStateException();

		String parameter = line.substring(getKeyword().length() + 1);
		ParameterTripletParser triplet = new ParameterTripletParser(parameter, context);

		return new Symbol(triplet.getParameter1(), triplet.getParameter2(), triplet.getParameter3());
	}
}
