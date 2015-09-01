package org.philhosoft.mif.parser;



public abstract class DefaultParser implements MifParser
{
	@Override
	public boolean canParse(ParsingContext context)
	{
		String line = context.getCurrentLine();
		if (line == null)
			return false;
		return line.toUpperCase().startsWith(getKeyword());
	}

	/**
	 * Returns the parameter following the keyword, if any.
	 * If no parameter is found in the current line, returns the trimmed content of the next line
	 * because in some Mif files, keywords stand on their own line.
	 * Call this only if a mandatory parameter must follow the keyword.
	 */
	public String readParameter(ParsingContext context)
	{
		String line = context.getCurrentLine();
		if (line == null)
			throw new IllegalStateException();

		String parameter = "";
		if (line.length() < getKeyword().length() + 2)
		{
			// No parameters after the keyword, perhaps it is on the next line
			if (context.readNextLine())
			{
				line = context.getCurrentLine();
				if (line == null)
				{
					context.addError("No parameters for " + getKeyword());
				}
				else
				{
					parameter = line;
				}
			}
			else
			{
				context.addError("No parameters for " + getKeyword());
			}
		}
		else
		{
			parameter = line.substring(getKeyword().length() + 1).trim();
		}

		return parameter;
	}
}
