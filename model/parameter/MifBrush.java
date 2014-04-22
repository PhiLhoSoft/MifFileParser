package org.philhosoft.mif.model.parameter;

/** The brush used to fill surfaces. */
public class MifBrush implements MifDataParameter
{
	private int pattern;
	private int foreColor;
	private int backColor;

	public MifBrush(int pattern, int foreColor, int backColor)
	{
		this.pattern = pattern;
		this.foreColor = foreColor;
		this.backColor = backColor;
	}

	public int getPattern()
	{
		return pattern;
	}
	public void setPattern(int pattern)
	{
		this.pattern = pattern;
	}
	public int getForeColor()
	{
		return foreColor;
	}
	public void setForeColor(int foreColor)
	{
		this.foreColor = foreColor;
	}
	public int getBackColor()
	{
		return backColor;
	}
	public void setBackColor(int backColor)
	{
		this.backColor = backColor;
	}
}
