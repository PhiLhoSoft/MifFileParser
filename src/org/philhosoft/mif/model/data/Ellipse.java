package org.philhosoft.mif.model.data;

import org.philhosoft.mif.model.parameter.Brush;
import org.philhosoft.mif.model.parameter.CoordinatePair;
import org.philhosoft.mif.model.parameter.Pen;

public class Ellipse implements MifData
{
	private CoordinatePair corner1;
	private CoordinatePair corner2;
	private Pen pen;
	private Brush brush;

	public Ellipse(CoordinatePair coordinate1, CoordinatePair coordinate2)
	{
		corner1 = coordinate1;
		corner2 = coordinate2;
	}

	public CoordinatePair getCorner1()
	{
		return corner1;
	}
	public CoordinatePair getCorner2()
	{
		return corner2;
	}

	public Pen getPen()
	{
		return pen;
	}
	public void setPen(Pen pen)
	{
		this.pen = pen;
	}
	public Brush getBrush()
	{
		return brush;
	}
	public void setBrush(Brush brush)
	{
		this.brush = brush;
	}

	@Override
	public <IN, OUT> OUT accept(Visitor<IN, OUT> visitor, IN in) throws Exception
	{
		return visitor.visit(this, in);
	}
}
