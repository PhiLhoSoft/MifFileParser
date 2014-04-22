package org.philhosoft.mif.model.data;

import org.philhosoft.mif.model.parameter.MifCoordinatePair;

public class MifText implements MifData
{
	private String text;
	private MifCoordinatePair corner1;
	private MifCoordinatePair corner2;
	private Spacing spacing;
	private Justify justify;
	private double angle;

	public enum Spacing { SIMPLE, SPACED, DOUBLE };
	public enum Justify { LEFT, CENTER, RIGHT }

	public MifText(String text)
	{
		this.text = text;
	}

	public String getText()
	{
		return text;
	}

	public MifCoordinatePair getCorner1()
	{
		return corner1;
	}
	public void setCorner1(MifCoordinatePair corner1)
	{
		this.corner1 = corner1;
	}
	public MifCoordinatePair getCorner2()
	{
		return corner2;
	}
	public void setCorner2(MifCoordinatePair corner2)
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
	public <IN, OUT> OUT accept(Visitor<IN, OUT> visitor, IN in)
	{
		return visitor.visit(this, in);
	}
}

