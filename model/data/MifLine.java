package org.philhosoft.mif.model.data;

import org.philhosoft.mif.model.parameter.MifCoordinatePair;
import org.philhosoft.mif.model.parameter.MifPen;

public class MifLine implements MifData
{
	private MifCoordinatePair start;
	private MifCoordinatePair end;
	private MifPen pen;

	public MifLine(MifCoordinatePair coordinates1, MifCoordinatePair coordinates2)
	{
		start = coordinates1;
		end = coordinates2;
	}

	public MifCoordinatePair getStart()
	{
		return start;
	}
	public MifCoordinatePair getEnd()
	{
		return end;
	}

	public MifPen getPen()
	{
		return pen;
	}
	public void setPen(MifPen pen)
	{
		this.pen = pen;
	}

	@Override
	public <IN, OUT> OUT accept(Visitor<IN, OUT> visitor, IN in)
	{
		return visitor.visit(this, in);
	}
}
