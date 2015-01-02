package org.philhosoft.mif.parser;


import java.util.ArrayList;
import java.util.List;

import org.philhosoft.mif.parser.Message.Type;


public class MessageCollector
{
	private List<Message> messages = new ArrayList<>();

	public boolean hasErrors()
	{
		for (Message m : messages)
		{
			if (m.getType() == Type.ERROR) return true;
		}
		return false;
	}

	public List<Message> getMessages()
	{
		return messages;
	}

	public void add(Message message)
	{
		messages.add(message);
	}

	@Override
	public String toString()
	{
		StringBuilder messageDump = new StringBuilder();
		for (Message message : messages)
		{
			messageDump.append(message).append('\n');
		}
		return messageDump.toString();
	}
}
