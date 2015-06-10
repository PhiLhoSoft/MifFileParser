package org.philhosoft.mif.model.data;

import org.philhosoft.mif.model.parameter.CoordinatePair;
import org.philhosoft.mif.model.parameter.Pen;

public class Arc implements MifData
{
	private CoordinatePair corner1;
	private CoordinatePair corner2;
	private double startAngle;
	private double endAngle;
	private Pen pen;

	public Arc(CoordinatePair coordinates1, CoordinatePair coordinates2)
	{
		corner1 = coordinates1;
		corner2 = coordinates2;
	}

	public CoordinatePair getCorner1()
	{
		return corner1;
	}
	public CoordinatePair getCorner2()
	{
		return corner2;
	}

	/** Angle in degrees. */
	public double getStartAngle()
	{
		return startAngle;
	}
	/** Angle in degrees. */
	public double getEndAngle()
	{
		return endAngle;
	}
	public Pen getPen()
	{
		return pen;
	}

	public void setStartAngle(double startAngle)
	{
		this.startAngle = startAngle;
	}
	public void setEndAngle(double endAngle)
	{
		this.endAngle = endAngle;
	}
	public void setPen(Pen pen)
	{
		this.pen = pen;
	}

	@Override
	public <IN, OUT> OUT accept(Visitor<IN, OUT> visitor, IN in) throws Exception
	{
		return visitor.visit(this, in);
	}
}
