package org.philhosoft.mif.model.data;

import org.philhosoft.mif.model.parameter.CoordinatePair;
import org.philhosoft.mif.model.parameter.Pen;

public class Line implements MifData
{
	private CoordinatePair start;
	private CoordinatePair end;
	private Pen pen;

	public Line(CoordinatePair coordinates1, CoordinatePair coordinates2)
	{
		start = coordinates1;
		end = coordinates2;
	}

	public CoordinatePair getStart()
	{
		return start;
	}
	public CoordinatePair getEnd()
	{
		return end;
	}

	public Pen getPen()
	{
		return pen;
	}
	public void setPen(Pen pen)
	{
		this.pen = pen;
	}

	@Override
	public <IN, OUT> OUT accept(Visitor<IN, OUT> visitor, IN in)
	{
		return visitor.visit(this, in);
	}
}
