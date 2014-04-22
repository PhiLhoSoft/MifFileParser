package org.philhosoft.mif.model.data;


public class MifNone implements MifData
{
	@Override
	public <IN, OUT> OUT accept(Visitor<IN, OUT> visitor, IN in)
	{
		return visitor.visit(this, in);
	}
}

