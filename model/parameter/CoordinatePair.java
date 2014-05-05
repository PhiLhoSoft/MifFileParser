package org.philhosoft.mif.model.parameter;

/** All locations in MIF data are pairs of floating-point x and y coordinates. */
public class CoordinatePair implements MifDataParameter
{
	private double x;
	private double y;

	public CoordinatePair(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public double getX()
	{
		return x;
	}
	public double getY()
	{
		return y;
	}

	public void setX(double x)
	{
		this.x = x;
	}
	public void setY(double y)
	{
		this.y = y;
	}

	@Override
	public <IN, OUT> OUT accept(Visitor<IN, OUT> visitor, IN in) throws Exception
	{
		return visitor.visit(this, in);
	}
}

