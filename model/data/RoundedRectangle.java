package org.philhosoft.mif.model.data;

import org.philhosoft.mif.model.parameter.Brush;
import org.philhosoft.mif.model.parameter.CoordinatePair;
import org.philhosoft.mif.model.parameter.Pen;

public class RoundedRectangle implements MifData
{
	private CoordinatePair corner1;
	private CoordinatePair corner2;
	private double degreeOfRounding;
	private Pen pen;
	private Brush brush;

	public RoundedRectangle(CoordinatePair coordinates1, CoordinatePair coordinates2)
	{
		corner1 = coordinates1;
		corner2 = coordinates2;
	}

	public CoordinatePair getCorner1()
	{
		return corner1;
	}
	public void setCorner1(CoordinatePair corner1)
	{
		this.corner1 = corner1;
	}
	public CoordinatePair getCorner2()
	{
		return corner2;
	}
	public void setCorner2(CoordinatePair corner2)
	{
		this.corner2 = corner2;
	}
	public double getDegreeOfRounding()
	{
		return degreeOfRounding;
	}
	public void setDegreeOfRounding(double degreeOfRounding)
	{
		this.degreeOfRounding = degreeOfRounding;
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
	public <IN, OUT> OUT accept(Visitor<IN, OUT> visitor, IN in)
	{
		return visitor.visit(this, in);
	}
}
