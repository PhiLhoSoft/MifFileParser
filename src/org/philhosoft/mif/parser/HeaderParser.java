package org.philhosoft.mif.parser;

import org.philhosoft.mif.model.MifFileContent;


/*
VERSION n
Charset "characterSetName"
[ DELIMITER "<c>"]
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
	public static final String HEADER_VERSION = "VERSION";
	public static final String HEADER_CHARSET = "CHARSET";
	public static final String HEADER_COLUMNS = "COLUMNS";
	public static final String HEADER_DATA = "DATA";
	private static final String[] HEADERS = { "DELIMITER", "UNIQUE", "INDEX", "COORDSYS", "TRANSFORM" };

	private KeywordLineParser lineParser = new KeywordLineParser();

	/**
	 * Parses the header, leaving the context after the DATA line.
	 *
	 * @param context  Mif file context
	 * @return a MifFileContent with the header fields filled
	 */
	public MifFileContent parse(ParsingContext context)
	{
		MifFileContent fc = new MifFileContent();
		if (!context.readNextLine())
		{
			context.addError("Invalid file (no " + HEADER_VERSION + ")");
			return fc;
		}
		fc.setVersion(lineParser.parse(HEADER_VERSION, context));
		if (!context.readNextLine())
		{
			context.addError("Invalid file (no " +  HEADER_CHARSET+ ")");
			return fc;
		}
		fc.setCharset(lineParser.parse(HEADER_CHARSET, context));

		// Skips the optional headers, not supported yet
		lineParser.skipKnownKeywordLines(HEADERS, context);

		context.readNextLine();
		String columnNbParam = lineParser.parse(HEADER_COLUMNS, context);
		if (columnNbParam != null)
		{
			int columnNb = 0;
			try
			{
				columnNb = Integer.parseInt(columnNbParam);
			}
			catch (NumberFormatException | NullPointerException e)
			{
				context.addError("Incorrect " + HEADER_COLUMNS + " parameter");
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
			context.addWarning(HEADER_COLUMNS + " field not found");
		}

		context.readNextLine();
		String data = context.getCurrentLine();
		if (data == null || !data.toUpperCase().equals(HEADER_DATA))
		{
			context.addError("No " + HEADER_DATA + " line found");
			return fc;
		}

		return fc;
	}
}
