package org.philhosoft.mif.model.parameter;


/** The pen used to draw lines. */
public class Pen implements MifDataParameter
{
	private int width;
	private int pattern;
	private int color;

	public Pen(int width, int pattern, int color)
	{
		this.width = width;
		this.pattern = pattern;
		this.color = color;
	}

	public int getWidth()
	{
		return width;
	}
	public int getPattern()
	{
		return pattern;
	}
	public int getColor()
	{
		return color;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}
	public void setPattern(int pattern)
	{
		this.pattern = pattern;
	}
	public void setColor(int color)
	{
		this.color = color;
	}

	@Override
	public <IN, OUT> OUT accept(Visitor<IN, OUT> visitor, IN in) throws Exception
	{
		return visitor.visit(this, in);
	}
}
