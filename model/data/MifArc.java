package org.philhosoft.mif.model.data;

import org.philhosoft.mif.model.parameter.MifCoordinatePair;
import org.philhosoft.mif.model.parameter.MifPen;

public class MifArc implements MifData
{
	private MifCoordinatePair corner1;
	private MifCoordinatePair corner2;
	private double startAngle;
	private double endAngle;
	private MifPen pen;

	public MifArc(MifCoordinatePair coordinates1, MifCoordinatePair coordinates2)
	{
		corner1 = coordinates1;
		corner2 = coordinates2;
	}

	public MifCoordinatePair getCorner1()
	{
		return corner1;
	}
	public MifCoordinatePair getCorner2()
	{
		return corner2;
	}

	public double getStartAngle()
	{
		return startAngle;
	}
	public void setStartAngle(double startAngle)
	{
		this.startAngle = startAngle;
	}
	public double getEndAngle()
	{
		return endAngle;
	}
	public void setEndAngle(double endAngle)
	{
		this.endAngle = endAngle;
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
