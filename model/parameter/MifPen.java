package org.philhosoft.mif.model.parameter;

/** The pen used to draw lines. */
public class MifPen implements MifDataParameter
{
	private int width;
	private int pattern;
	private int color;

	public MifPen(int width, int pattern, int color)
	{
		this.width = width;
		this.pattern = pattern;
		this.color = color;
	}

	public int getWidth()
	{
		return width;
	}
	public void setWidth(int width)
	{
		this.width = width;
	}
	public int getPattern()
	{
		return pattern;
	}
	public void setPattern(int pattern)
	{
		this.pattern = pattern;
	}
	public int getColor()
	{
		return color;
	}
	public void setColor(int color)
	{
		this.color = color;
	}
}

