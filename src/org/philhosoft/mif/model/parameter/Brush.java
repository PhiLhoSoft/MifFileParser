package org.philhosoft.mif.model.parameter;


/** The brush used to fill surfaces. */
public class Brush implements MifDataParameter
{
	private int pattern;
	private int foreColor;
	private int backColor;

	public Brush(int pattern, int foreColor, int backColor)
	{
		this.pattern = pattern;
		this.foreColor = foreColor;
		this.backColor = backColor;
	}

	public int getPattern()
	{
		return pattern;
	}
	public int getForeColor()
	{
		return foreColor;
	}
	public int getBackColor()
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
	public void setBackColor(int backColor)
	{
		this.backColor = backColor;
	}

	@Override
	public <IN, OUT> OUT accept(Visitor<IN, OUT> visitor, IN in) throws Exception
	{
		return visitor.visit(this, in);
	}
}
