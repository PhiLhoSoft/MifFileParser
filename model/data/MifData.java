package org.philhosoft.mif.model.data;

/** MIF data that can be visited. */
public interface MifData
{
	<IN, OUT> OUT accept(Visitor<IN, OUT> visitor, IN in);

	interface Visitor<IN, OUT>
	{
		OUT visit(MifArc data, IN in);
		OUT visit(MifEllipse data, IN in);
		OUT visit(MifLine data, IN in);
		OUT visit(MifNone data, IN in);
		OUT visit(MifPoint data, IN in);
		OUT visit(MifPolyline data, IN in);
		OUT visit(MifRectangle data, IN in);
		OUT visit(MifRegion data, IN in);
		OUT visit(MifRoundedRectangle data, IN in);
		OUT visit(MifText data, IN in);
	}
}

