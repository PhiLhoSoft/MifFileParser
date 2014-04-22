package org.philhosoft.mif.model.parameter;

/** Symbol used to display a point. */
public class MifSymbol implements MifDataParameter
{
	private int shape;
	private int color;
	private int size;

	public MifSymbol(int shape, int color, int size)
	{
		this.shape = shape;
		this.color = color;
		this.size = size;
	}

	public int getShape()
	{
		return shape;
	}
	public void setShape(int shape)
	{
		this.shape = shape;
	}
	public int getColor()
	{
		return color;
	}
	public void setColor(int color)
	{
		this.color = color;
	}
	public int getSize()
	{
		return size;
	}
	public void setSize(int size)
	{
		this.size = size;
	}
}

