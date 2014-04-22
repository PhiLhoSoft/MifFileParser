package org.philhosoft.mif.parser;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;


public class MifReader
{
	private LineNumberReader fileReader;
	private String currentLine;
	private MessageCollector messages;
	private boolean keepCurrentLine;

	public MifReader(File mifFile) throws FileNotFoundException
	{
		fileReader = new LineNumberReader(new BufferedReader(new InputStreamReader(new FileInputStream(mifFile))));
	}

	public void close()
	{
		if (fileReader != null)
		{
			try
			{
				fileReader.close();
			}
			catch (IOException e)
			{
				messages.add(new MifMessage(MifMessage.Type.ERROR, 0, e.getMessage()));
			}
		}
	}

	/**
	 * Reads next line from the file.
	 *
	 * @return true if successful
	 */
	public boolean readNextLine()
	{
		if (keepCurrentLine)
		{
			keepCurrentLine = false;
			return true;
		}
		try
		{
			currentLine = fileReader.readLine();
		}
		catch (IOException e)
		{
			addError(e.getMessage());
			currentLine = null;
		}
		return currentLine == null;
	}

	/**
	 * If a parser cannot parse the current line, it can push it back, so that readNextLine() does nothing.
	 */
	public void pushBackLine()
	{
		keepCurrentLine = true;
	}

	public String getCurrentLine()
	{
		if (currentLine == null)
			return null;
		return currentLine.trim();
	}

	public int getCurrentLineNumber()
	{
		return fileReader.getLineNumber();
	}

	public void addMessage(MifMessage message)
	{
		messages.add(message);
	}

	public void addError(String message)
	{
		MifMessage error = new MifMessage(MifMessage.Type.ERROR, getCurrentLineNumber(), message);
		addMessage(error);
	}

	public void addWarning(String message)
	{
		MifMessage warning = new MifMessage(MifMessage.Type.WARNING, getCurrentLineNumber(), message);
		addMessage(warning);
	}

	public MessageCollector getErrorCollector()
	{
		return messages;
	}
}
