package org.philhosoft.mif.model.data;

import org.philhosoft.mif.model.parameter.CoordinatePair;

public class Text implements MifData
{
	private String text;
	private CoordinatePair corner1;
	private CoordinatePair corner2;
	private Spacing spacing;
	private Justify justify;
	private double angle;

	public enum Spacing { SIMPLE, SPACED, DOUBLE };
	public enum Justify { LEFT, CENTER, RIGHT }

	public Text(String text)
	{
		this.text = text;
	}

	public String getText()
	{
		return text;
	}

	public CoordinatePair getCorner1()
	{
		return corner1;
	}
	public void setCorner1(CoordinatePair corner1)
	{
		this.corner1 = corner1;
	}
	public CoordinatePair getCorner2()
	{
		return corner2;
	}
	public void setCorner2(CoordinatePair corner2)
	{
		this.corner2 = corner2;
	}
	public Spacing getSpacing()
	{
		return spacing;
	}
	public void setSpacing(Spacing spacing)
	{
		this.spacing = spacing;
	}
	public Justify getJustify()
	{
		return justify;
	}
	public void setJustify(Justify justify)
	{
		this.justify = justify;
	}
	public double getAngle()
	{
		return angle;
	}
	public void setAngle(double angle)
	{
		this.angle = angle;
	}

	@Override
	public <IN, OUT> OUT accept(Visitor<IN, OUT> visitor, IN in) throws Exception
	{
		return visitor.visit(this, in);
	}
}

