package org.philhosoft.mif.parser.parameter;


import org.philhosoft.mif.parser.ParsingContext;


public class ParameterTripletParser
{
	private int p1, p2, p3;

	public ParameterTripletParser(String parameterExpression, ParsingContext context)
	{
		if (!parameterExpression.startsWith("(") || !parameterExpression.endsWith(")"))
		{
			context.addError("Invalid parameter syntax: no parentheses");
			return;
		}
		String[] parameters = parameterExpression.substring(1, parameterExpression.length() - 1).split(",\\s*");
		if (parameters.length != 3)
		{
			context.addError("Invalid parameter syntax: wrong number of parameters");
			return;
		}
		try
		{
			p1 = Integer.parseInt(parameters[0]);
		}
		catch (NumberFormatException e)
		{
			context.addError("Invalid first parameter");
		}
		try
		{
			p2 = Integer.parseInt(parameters[1]);
		}
		catch (NumberFormatException e)
		{
			context.addError("Invalid second parameter");
		}
		try
		{
			p3 = Integer.parseInt(parameters[2]);
		}
		catch (NumberFormatException e)
		{
			context.addError("Invalid third parameter");
		}
	}

	public int getParameter1()
	{
		return p1;
	}
	public int getParameter2()
	{
		return p2;
	}
	public int getParameter3()
	{
		return p3;
	}
}
