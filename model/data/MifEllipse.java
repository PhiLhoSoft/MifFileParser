package org.philhosoft.mif.model.data;

import org.philhosoft.mif.model.parameter.MifBrush;
import org.philhosoft.mif.model.parameter.MifCoordinatePair;
import org.philhosoft.mif.model.parameter.MifPen;

public class MifEllipse implements MifData
{
	private MifCoordinatePair corner1;
	private MifCoordinatePair corner2;
	private MifPen pen;
	private MifBrush brush;

	public MifEllipse(MifCoordinatePair coordinate1, MifCoordinatePair coordinate2)
	{
		corner1 = coordinate1;
		corner2 = coordinate2;
	}

	public MifCoordinatePair getCorner1()
	{
		return corner1;
	}
	public MifCoordinatePair getCorner2()
	{
		return corner2;
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
