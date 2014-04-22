package org.philhosoft.mif.model.parameter;

/** All locations in MIF data are pair of floating-point x and y coordinates. */
public class MifCoordinatePair implements MifDataParameter
{
	private double x;
	private double y;

	public MifCoordinatePair(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public double getX()
	{
		return x;
	}
	public void setX(double x)
	{
		this.x = x;
	}
	public double getY()
	{
		return y;
	}
	public void setY(double y)
	{
		this.y = y;
	}
}

