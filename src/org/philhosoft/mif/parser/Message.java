package org.philhosoft.mif.parser;

public class Message
{
	private final Type type;
	private final int lineNumber;
	private final String message;

	public enum Type { INFO, WARNING, ERROR };

	public Message(Type type, int lineNumber, String message)
	{
		this.type = type;
		this.lineNumber = lineNumber;
		this.message = message;
	}

	public Type getType()
	{
		return type;
	}

	public int getLineNumber()
	{
		return lineNumber;
	}

	public String getMessage()
	{
		return message;
	}

	@Override
	public String toString()
	{
		return type + " [" + lineNumber + "] " + message;
	}
}

