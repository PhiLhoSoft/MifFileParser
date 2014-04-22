package org.philhosoft.mif.model.data;

import org.philhosoft.mif.model.parameter.MifBrush;
import org.philhosoft.mif.model.parameter.MifCoordinatePair;
import org.philhosoft.mif.model.parameter.MifPen;

public class MifRoundedRectangle implements MifData
{
	private MifCoordinatePair corner1;
	private MifCoordinatePair corner2;
	private double degreeOfRounding;
	private MifPen pen;
	private MifBrush brush;

	public MifRoundedRectangle(MifCoordinatePair coordinates1, MifCoordinatePair coordinates2)
	{
		corner1 = coordinates1;
		corner2 = coordinates2;
	}

	public MifCoordinatePair getCorner1()
	{
		return corner1;
	}
	public void setCorner1(MifCoordinatePair corner1)
	{
		this.corner1 = corner1;
	}
	public MifCoordinatePair getCorner2()
	{
		return corner2;
	}
	public void setCorner2(MifCoordinatePair corner2)
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
	public MifPen getPen()
	{
		return pen;
	}
	public void setPen(MifPen pen)
	{
		this.pen = pen;
	}
	public MifBrush getBrush()
	{
		return brush;
	}
	public void setBrush(MifBrush brush)
	{
		this.brush = brush;
	}

	@Override
	public <IN, OUT> OUT accept(Visitor<IN, OUT> visitor, IN in)
	{
		return visitor.visit(this, in);
	}
}
