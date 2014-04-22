package org.philhosoft.mif.model.data;

import java.util.List;

import org.philhosoft.mif.model.parameter.MifBrush;
import org.philhosoft.mif.model.parameter.MifCoordinatePair;
import org.philhosoft.mif.model.parameter.MifPen;

public class MifRegion implements MifData
{
	private List<List<MifCoordinatePair>> polygons;
	private MifPen pen;
	private MifBrush brush;
	private MifCoordinatePair center;

	public List<List<MifCoordinatePair>> getPolygons()
	{
		return polygons;
	}
	public void setPolygons(List<List<MifCoordinatePair>> polygons)
	{
		this.polygons = polygons;
	}
	public MifPen getPen()
	{
		return pen;
	}
	public void setPen(MifPen pen)
	{
		this.pen = pen;
	}
	public MifBrush getBrush()
	{
		return brush;
	}
	public void setBrush(MifBrush brush)
	{
		this.brush = brush;
	}
	public MifCoordinatePair getCenter()
	{
		return center;
	}
	public void setCenter(MifCoordinatePair center)
	{
		this.center = center;
	}

	@Override
	public <IN, OUT> OUT accept(Visitor<IN, OUT> visitor, IN in)
	{
		return visitor.visit(this, in);
	}
}

