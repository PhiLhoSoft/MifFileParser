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

	/** 0 to 7 pixels. 0 is not visible (must have pattern 1),.
	 * If in the 11-2047 range, width is in points.
	 */
	public int getWidth()
	{
		return width;
	}
	/** 1 to 118. 1 is invisible. Can go up to 255 if interleaved. */
	public int getPattern()
	{
		return pattern;
	}
	/** 24-bit RGB color value. */
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
