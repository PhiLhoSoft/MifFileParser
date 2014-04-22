package org.philhosoft.mif.parser.parameter;


import org.philhosoft.mif.model.parameter.MifBrush;
import org.philhosoft.mif.model.parameter.MifDataParameter;
import org.philhosoft.mif.parser.MifDefaultParser;
import org.philhosoft.mif.parser.MifReader;


public class MifBrushParser extends MifDefaultParser implements MifParameterParser
{
	public static final String KEYWORD = "BRUSH";

	@Override
	public String getKeyword()
	{
		return KEYWORD;
	}

	@Override
	public MifDataParameter parseParameter(MifReader reader)
	{
		String line = reader.getCurrentLine();
		if (line == null)
			throw new IllegalStateException();
		String parameter = line.substring(getKeyword().length() + 1);
		ParameterTripletParser triplet = new ParameterTripletParser(parameter, reader);

		return new MifBrush(triplet.getParameter1(), triplet.getParameter2(), triplet.getParameter3());
	}
}
