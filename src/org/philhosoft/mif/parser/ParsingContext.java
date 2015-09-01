package org.philhosoft.mif.parser;



/**
 * Context of the current parsing: contains the file reader and the message collector.
 */
public class ParsingContext
{
	// Avoid flooding. Perhaps allow to configure it.
	private static final int MAX_NUMBER_OF_MESSAGES = 100;

	private final MifReader reader;
	private final MessageCollector messages = new MessageCollector();
	private String currentLine;
	private boolean keepCurrentLine;

	public ParsingContext(MifReader reader)
	{
		this.reader = reader;
	}

	/**
	 * Reads next line from the file.
	 *
	 * @return true if successful (otherwise, current line is null)
	 */
	public boolean readNextLine()
	{
		if (keepCurrentLine)
		{
			keepCurrentLine = false;
		}
		else
		{
			currentLine = reader.readLine(this);
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

	/**
	 * Gets the current line in the reading context.
	 *
	 * @return the trimmed out line (without starting and ending spaces).
	 */
	public String getCurrentLine()
	{
		if (currentLine == null)
			return null;
		return currentLine.trim();
	}

	/**
	 * Adds a message to the message collector.
	 *
	 * @param message
	 */
	public void addMessage(Message message)
	{
		messages.add(message);
		if (messages.getMessages().size() >= MAX_NUMBER_OF_MESSAGES)
		{
			throw new RuntimeException("Reached " + MAX_NUMBER_OF_MESSAGES + " messages, stopping.");
		}
	}

	/**
	 * Adds an error message (with the current line number) to the message collector.
	 *
	 * @param message
	 */
	public void addError(String message)
	{
		Message error = new Message(Message.Type.ERROR, reader.getCurrentLineNumber(), message);
		addMessage(error);
	}

	/**
	 * Adds a warning message (with the current line number) to the message collector.
	 *
	 * @param message
	 */
	public void addWarning(String message)
	{
		Message warning = new Message(Message.Type.WARNING, reader.getCurrentLineNumber(), message);
		addMessage(warning);
	}

	public MessageCollector getMessageCollector()
	{
		return messages;
	}
}
