package org.philhosoft.mif.model.data;

import org.philhosoft.mif.model.parameter.CoordinatePair;
import org.philhosoft.mif.model.parameter.Symbol;

public class Point implements MifData
{
	private CoordinatePair location;
	private Symbol symbol;

	public Point(CoordinatePair coordinates)
	{
		location = coordinates;
	}

	public CoordinatePair getLocation()
	{
		return location;
	}

	public Symbol getSymbol()
	{
		return symbol;
	}
	public void setSymbol(Symbol symbol)
	{
		this.symbol = symbol;
	}

	@Override
	public <IN, OUT> OUT accept(Visitor<IN, OUT> visitor, IN in) throws Exception
	{
		return visitor.visit(this, in);
	}
}

