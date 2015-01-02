package org.philhosoft.mif.model.parameter;


/** Interface of Mif data parameters, like PEN or BRUSH. */
public interface MifDataParameter
{
	<IN, OUT> OUT accept(Visitor<IN, OUT> visitor, IN in) throws Exception;

	interface Visitor<IN, OUT>
	{
		OUT visit(CoordinatePair parameter, IN in) throws Exception;
		OUT visit(Brush parameter, IN in) throws Exception;
		OUT visit(Pen parameter, IN in) throws Exception;
		OUT visit(Symbol parameter, IN in) throws Exception;
	}
}
