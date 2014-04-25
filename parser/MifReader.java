package org.philhosoft.mif.parser;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;


public class MifReader
{
	private LineNumberReader fileReader;

	public MifReader(InputStream in)
	{
		fileReader = new LineNumberReader(new BufferedReader(new InputStreamReader(in)));
	}

	/**
	 * Reads next line from the file.
	 *
	 * @param errorHandler to record an error, if any
	 * @return the line if successful, null otherwise (error or end of file).
	 *         In case of error, the exception message is added to the context.
	 */
	public String readLine(ParsingContext errorHandler)
	{
		try
		{
			return fileReader.readLine();
		}
		catch (IOException e)
		{
			errorHandler.addError(e.getMessage());
			return null;
		}
	}

	public int getCurrentLineNumber()
	{
		return fileReader.getLineNumber();
	}
}
