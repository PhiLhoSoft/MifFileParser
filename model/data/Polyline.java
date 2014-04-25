package org.philhosoft.mif.model.data;

import java.util.ArrayList;
import java.util.List;

import org.philhosoft.mif.model.parameter.CoordinatePair;
import org.philhosoft.mif.model.parameter.Pen;

public class Polyline implements MifData
{
	private List<List<CoordinatePair>> sections = new ArrayList<>();
	private Pen pen;
	private boolean smooth;

	public void addSection()
	{
		sections.add(new ArrayList<CoordinatePair>());
	}
	public void addCoordinates(CoordinatePair coordinates)
	{
		if (sections.size() == 0)
			throw new IllegalStateException("Must add a section to the region before adding coordinates.");
		List<CoordinatePair> currentSection = sections.get(sections.size() - 1);
		currentSection.add(coordinates);
	}

	public List<List<CoordinatePair>> getSections()
	{
		return sections;
	}
	public void setSections(List<List<CoordinatePair>> sections)
	{
		this.sections = sections;
	}
	public Pen getPen()
	{
		return pen;
	}
	public void setPen(Pen pen)
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

