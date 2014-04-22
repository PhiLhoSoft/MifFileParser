package org.philhosoft.mif.parser;


import java.util.ArrayList;
import java.util.List;

import org.philhosoft.mif.parser.MifMessage.Type;


public class MessageCollector
{
	private List<MifMessage> messages = new ArrayList<>();

	public boolean hasErrors()
	{
		for (MifMessage m : messages)
		{
			if (m.getType() == Type.ERROR) return true;
		}
		return false;
	}

	public List<MifMessage> getMessages()
	{
		return messages;
	}

	public void add(MifMessage message)
	{
		messages.add(message);
	}

	@Override
	public String toString()
	{
		StringBuilder messageDump = new StringBuilder();
		for (MifMessage message : messages)
		{
			messageDump.append(message).append('\n');
		}
		return messageDump.toString();
	}
}
