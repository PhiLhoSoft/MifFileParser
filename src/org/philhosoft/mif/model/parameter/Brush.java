package org.philhosoft.mif.model.parameter;


/** The brush used to fill surfaces. */
public class Brush implements MifDataParameter
{
	private int pattern;
	private int foreColor;
	private Integer backColor;

	public Brush(int pattern, int foreColor, Integer backColor)
	{
		this.pattern = pattern;
		this.foreColor = foreColor;
		this.backColor = backColor;
	}

	/** 1 to 71. 1 is "no fill," and 2 is a solid fill.
	 * Pattern numbers 9-11 are reserved.
	 */
	public int getPattern()
	{
		return pattern;
	}
	/** 24-bit RGB color value. */
	public int getForeColor()
	{
		return foreColor;
	}
	/** 24-bit RGB color value. If omitted (null here), it is a transparent fill style. */
	public Integer getBackColor()
	{
		return backColor;
	}

	public void setPattern(int pattern)
	{
		this.pattern = pattern;
	}
	public void setForeColor(int foreColor)
	{
		this.foreColor = foreColor;
	}
	public void setBackColor(Integer backColor)
	{
		this.backColor = backColor;
	}

	@Override
	public <IN, OUT> OUT accept(Visitor<IN, OUT> visitor, IN in) throws Exception
	{
		return visitor.visit(this, in);
	}
}
