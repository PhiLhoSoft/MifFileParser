package org.philhosoft.mif.parser.parameter;


import org.philhosoft.mif.model.parameter.MifDataParameter;
import org.philhosoft.mif.parser.MifParser;
import org.philhosoft.mif.parser.ParsingContext;


public interface ParameterParser extends MifParser
{
	MifDataParameter parseParameter(ParsingContext context);
}
