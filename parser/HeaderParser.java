package org.philhosoft.mif.parser;

import org.philhosoft.mif.model.MifFileContent;


/*
VERSION n
Charset ”characterSetName”
[ DELIMITER ”<c>” ]
[ UNIQUE n,n... ]
[ INDEX n,n... ]
[ COORDSYS... ]
[ TRANSFORM... ]
COLUMNS n
  <name> <type>
  <name> <type>
  .
  .
DATA
*/
public class HeaderParser
{
	private static final String[] HEADERS = { "DELIMITER", "UNIQUE", "INDEX", "COORDSYS", "TRANSFORM" };

	/**
	 * Parses the header, leaving the context after the DATA line.
	 *
	 * @param context  Mif file context
	 * @return a MifFileContent with the header fields filled
	 */
	public MifFileContent parse(ParsingContext context)
	{
		HeaderLineParser lineParser = new HeaderLineParser();
		MifFileContent fc = new MifFileContent();
		if (!context.readNextLine())
		{
			context.addError("Invalid file (no VERSION)");
			return fc;
		}
		fc.setVersion(lineParser.parse("VERSION", context));
		if (!context.readNextLine())
		{
			context.addError("Invalid file (no CHARSET)");
			return fc;
		}
		fc.setCharset(lineParser.parse("CHARSET", context));

		// Tries to parse each kind of optional header
		int i = 0;
		boolean successful = true;
		while (context.readNextLine() && successful)
		{
			String value = lineParser.parse(HEADERS[i++], context);
			successful = value != null;
		}

		String columnNbParam = lineParser.parse("COLUMNS", context);
		if (columnNbParam != null)
		{
			int columnNb = 0;
			try
			{
				columnNb = Integer.parseInt(columnNbParam);
			}
			catch (NumberFormatException e)
			{
				context.addError("Incorrect COLUMNS parameter");
				return fc;
			}
			for (int c = 0; c < columnNb; c++)
			{
				context.readNextLine(); // Skip these lines for now?
			}
		}
		else
		{
			// According to spec, it should be mandatory, so it should be an error...
			context.addWarning("COLUMNS field not found");
		}

		context.readNextLine();
		String data = context.getCurrentLine();
		if (!data.equals("DATA"))
		{
			context.addError("No DATA line found");
			return fc;
		}

		return fc;
	}
}
