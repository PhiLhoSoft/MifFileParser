package org.philhosoft.mif.model.data;

/** MIF data that can be visited. */
public interface MifData
{
	<IN, OUT> OUT accept(Visitor<IN, OUT> visitor, IN in);

	interface Visitor<IN, OUT>
	{
		OUT visit(Arc data, IN in);
		OUT visit(Ellipse data, IN in);
		OUT visit(Line data, IN in);
		OUT visit(None data, IN in);
		OUT visit(Point data, IN in);
		OUT visit(Polyline data, IN in);
		OUT visit(Rectangle data, IN in);
		OUT visit(Region data, IN in);
		OUT visit(RoundedRectangle data, IN in);
		OUT visit(Text data, IN in);
	}
}

