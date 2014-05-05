package org.philhosoft.mif.model.data;

import java.util.ArrayList;
import java.util.List;

import org.philhosoft.mif.model.parameter.Brush;
import org.philhosoft.mif.model.parameter.CoordinatePair;
import org.philhosoft.mif.model.parameter.Pen;

public class Region implements MifData
{
	private List<List<CoordinatePair>> polygons = new ArrayList<>();
	private Pen pen;
	private Brush brush;
	private CoordinatePair center;

	public void addPolygon()
	{
		polygons.add(new ArrayList<CoordinatePair>());
	}
	public void addCoordinates(CoordinatePair coordinates)
	{
		if (polygons.size() == 0)
			throw new IllegalStateException("Must add a polygon to the region before adding coordinates.");
		List<CoordinatePair> currentPolygon = polygons.get(polygons.size() - 1);
		currentPolygon.add(coordinates);
	}

	public List<List<CoordinatePair>> getPolygons()
	{
		return polygons;
	}
	public void setPolygons(List<List<CoordinatePair>> polygons)
	{
		this.polygons = polygons;
	}
	public Pen getPen()
	{
		return pen;
	}
	public void setPen(Pen pen)
	{
		this.pen = pen;
	}
	public Brush getBrush()
	{
		return brush;
	}
	public void setBrush(Brush brush)
	{
		this.brush = brush;
	}
	public CoordinatePair getCenter()
	{
		return center;
	}
	public void setCenter(CoordinatePair center)
	{
		this.center = center;
	}

	@Override
	public <IN, OUT> OUT accept(Visitor<IN, OUT> visitor, IN in) throws Exception
	{
		return visitor.visit(this, in);
	}
}

