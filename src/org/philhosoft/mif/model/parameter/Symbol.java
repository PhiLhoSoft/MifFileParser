package org.philhosoft.mif.model.parameter;


/** Symbol used to display a point. */
public class Symbol implements MifDataParameter
{
	private int shape;
	private int color;
	private int size;
	private String fontName;
	private int fontStyle;
	private int rotation;

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

	public int getShape()
	{
		return shape;
	}
	public int getColor()
	{
		return color;
	}
	public int getSize()
	{
		return size;
	}
	public String getFontName()
	{
		return fontName;
	}
	public int getFontStyle()
	{
		return fontStyle;
	}
	public int getRotation()
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
	public void setRotation(int rotation)
	{
		this.rotation = rotation;
	}

	@Override
	public <IN, OUT> OUT accept(Visitor<IN, OUT> visitor, IN in) throws Exception
	{
		return visitor.visit(this, in);
	}
}

