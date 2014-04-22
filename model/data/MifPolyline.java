package org.philhosoft.mif.model.data;

import java.util.List;

import org.philhosoft.mif.model.parameter.MifCoordinatePair;
import org.philhosoft.mif.model.parameter.MifPen;

public class MifPolyline implements MifData
{
	private List<List<MifCoordinatePair>> sections;
	private MifPen pen;
	private boolean smooth;

	public List<List<MifCoordinatePair>> getSections()
	{
		return sections;
	}
	public void setSections(List<List<MifCoordinatePair>> sections)
	{
		this.sections = sections;
	}
	public MifPen getPen()
	{
		return pen;
	}
	public void setPen(MifPen pen)
	{
		this.pen = pen;
	}
	public boolean isSmooth()
	{
		return smooth;
	}
	public void setSmooth(boolean smooth)
	{
		this.smooth = smooth;
	}

	@Override
	public <IN, OUT> OUT accept(Visitor<IN, OUT> visitor, IN in)
	{
		return visitor.visit(this, in);
	}
}

