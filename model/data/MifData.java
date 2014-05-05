package org.philhosoft.mif.model.data;

/** MIF data that can be visited. */
public interface MifData
{
	<IN, OUT> OUT accept(Visitor<IN, OUT> visitor, IN in) throws Exception;

	interface Visitor<IN, OUT>
	{
		OUT visit(Arc data, IN in) throws Exception;
		OUT visit(Ellipse data, IN in) throws Exception;
		OUT visit(Line data, IN in) throws Exception;
		OUT visit(None data, IN in) throws Exception;
		OUT visit(Point data, IN in) throws Exception;
		OUT visit(Polyline data, IN in) throws Exception;
		OUT visit(Rectangle data, IN in) throws Exception;
		OUT visit(Region data, IN in) throws Exception;
		OUT visit(RoundedRectangle data, IN in) throws Exception;
		OUT visit(Text data, IN in) throws Exception;
	}
}
