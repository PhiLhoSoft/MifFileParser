package org.philhosoft.mif.model.parameter;


/** Symbol used to display a point. */
public class Symbol implements MifDataParameter
{
	private int shape;
	private int color;
	private int size;

	public Symbol(int shape, int color, int size)
	{
		this.shape = shape;
		this.color = color;
		this.size = size;
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

	@Override
	public <IN, OUT> OUT accept(Visitor<IN, OUT> visitor, IN in) throws Exception
	{
		return visitor.visit(this, in);
	}
}

