package org.philhosoft.mif.parser.parameter;


import org.philhosoft.mif.model.parameter.Symbol;
import org.philhosoft.mif.parser.DefaultParser;
import org.philhosoft.mif.parser.ParsingContext;


/* SYMBOL (shape, color, size) */
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
		ParenthesizedParameterParser parameters = new ParenthesizedParameterParser(parameter, context);
		if (parameters.getParameterNumber() == 3)
		{
			return new Symbol(parameters.getIntParameter(0), parameters.getIntParameter(1), parameters.getIntParameter(2));
		}
		else if (parameters.getParameterNumber() == 6)
		{
			return new Symbol(parameters.getIntParameter(0), parameters.getIntParameter(1), parameters.getIntParameter(2),
					parameters.getParameter(3), parameters.getIntParameter(4), parameters.getIntParameter(5));
		}
		else
		{
			context.addError("Wrong number of parameters for " + KEYWORD);
			return new Symbol(0, 0, 0);
		}
	}
}
