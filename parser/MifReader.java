package org.philhosoft.mif.parser;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;


public class MifReader
{
	private LineNumberReader fileReader;
	private String currentLine;
	private MessageCollector messages = new MessageCollector();
	private boolean keepCurrentLine;

	public MifReader(InputStream in)
	{
		fileReader = new LineNumberReader(new BufferedReader(new InputStreamReader(in)));
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
		return currentLine != null;
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
		return fileReader.getLineNumber() + 1;
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

	public MessageCollector getMessageCollector()
	{
		return messages;
	}
}
