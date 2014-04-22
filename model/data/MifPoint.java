package org.philhosoft.mif.model.data;

import org.philhosoft.mif.model.parameter.MifCoordinatePair;
import org.philhosoft.mif.model.parameter.MifSymbol;

public class MifPoint implements MifData
{
	private MifCoordinatePair location;
	private MifSymbol symbol;

	public MifPoint(MifCoordinatePair coordinates)
	{
		location = coordinates;
	}

	public MifCoordinatePair getLocation()
	{
		return location;
	}

	public MifSymbol getSymbol()
	{
		return symbol;
	}
	public void setSymbol(MifSymbol symbol)
	{
		this.symbol = symbol;
	}

	@Override
	public <IN, OUT> OUT accept(Visitor<IN, OUT> visitor, IN in)
	{
		return visitor.visit(this, in);
	}
}

