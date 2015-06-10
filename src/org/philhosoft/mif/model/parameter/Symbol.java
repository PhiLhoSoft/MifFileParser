package org.philhosoft.mif.model.parameter;


/** Symbol used to display a point. */
public class Symbol implements MifDataParameter
{
	private int shape;
	private int color;
	private int size;
	private String fontName;
	private int fontStyle;
	private float rotation;

	public Symbol(int shape, int color, int size)
	{
		this.shape = shape;
		this.color = color;
		this.size = size;
		this.fontName = "";
		this.fontStyle = 0;
		this.rotation = 0;
	}
	public Symbol(int shape, int color, int size, String fontName, int fontStyle, int rotation)
	{
		this.shape = shape;
		this.color = color;
		this.size = size;
		this.fontName = fontName;
		this.fontStyle = fontStyle;
		this.rotation = rotation;
	}

	/** 31 to 67. 31 = blank symbol. */
	public int getShape()
	{
		return shape;
	}
	/** 24-bit RGB color value. */
	public int getColor()
	{
		return color;
	}
	/** 1 to 48. Point size. */
	public int getSize()
	{
		return size;
	}
	/** TrueType font name. */
	public String getFontName()
	{
		return fontName;
	}
	/**
	 * Font style:.
	 * 0   plain text
	 * 1   bold text
	 * 16  black border around symbol
	 * 32  drop shadow
	 * 256 white border around symbol
	 * Can add values to combine them.
	 */
	public int getFontStyle()
	{
		return fontStyle;
	}
	/** Rotation in degrees. */
	public float getRotation()
	{
		return rotation;
	}

	public void setShape(int shape)
	{
		this.shape = shape;
	}
	public void setColor(int color)
	{
		this.color = color;
	}
	public void setSize(int size)
	{
		this.size = size;
	}
	public void setFontName(String fontName)
	{
		this.fontName = fontName;
	}
	public void setFontStyle(int fontStyle)
	{
		this.fontStyle = fontStyle;
	}
	public void setRotation(float rotation)
	{
		this.rotation = rotation;
	}

	@Override
	public <IN, OUT> OUT accept(Visitor<IN, OUT> visitor, IN in) throws Exception
	{
		return visitor.visit(this, in);
	}
}

