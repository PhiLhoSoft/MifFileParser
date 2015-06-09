package org.philhosoft.mif.parser.parameter;


import org.philhosoft.mif.parser.ParsingContext;


public class ParenthesizedParameterParser
{
	private ParsingContext context;
	private String[] parameters;

	public ParenthesizedParameterParser(String parameterExpression, ParsingContext context)
	{
		this.context = context;
		if (!parameterExpression.startsWith("(") || !parameterExpression.endsWith(")"))
		{
			context.addError("Invalid parameter syntax: no parentheses");
			return;
		}
		parameters = parameterExpression.substring(1, parameterExpression.length() - 1).split(",\\s*");
	}

	public int getParameterNumber()
	{
		return parameters.length;
	}
	public String getParameter(int i)
	{
		return parameters[i];
	}
	public int getIntParameter(int i)
	{
		try
		{
			return Integer.parseInt(parameters[i]);
		}
		catch (NumberFormatException e)
		{
			context.addError("Invalid parameter #" + i);
		}
		return 0;
	}
}
