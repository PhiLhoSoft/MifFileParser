package org.philhosoft.mif;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.philhosoft.mif.export.ExportToJson;
import org.philhosoft.mif.export.ExportToMif;
import org.philhosoft.mif.model.MifFileContent;
import org.philhosoft.mif.parser.MessageCollector;
import org.philhosoft.mif.parser.MifFileContentParser;
import org.philhosoft.mif.parser.MifReader;
import org.philhosoft.mif.parser.ParsingContext;

public class MifFileParser
{
	public static final String DEFAULT_CHARTSET = "UTF-8";

	public static void main(String[] args) throws FileNotFoundException, IOException, Exception
	{
		File mifFile = new File("C:/Test/Simple.mif"); // TODO read command line options!
		InputStream is = new FileInputStream(mifFile);
		MifReader reader = new MifReader(is);
		ParsingContext context = new ParsingContext(reader);

		MifFileContentParser parser = new MifFileContentParser();
		MifFileContent fileContent = null;
		try
		{
			fileContent = parser.parseContent(context);
		}
		finally
		{
			is.close();
		}

		MessageCollector collector = context.getMessageCollector();
		// TODO proper management / output of errors! Use a logger...
		if (collector.hasErrors())
		{
			System.err.println("Found errors");
			System.err.println(collector);
			return;
		}

		exportToMif(fileContent);
		String documentName = mifFile.getName();
		exportToJson(fileContent, documentName);

		System.out.println(collector);
		System.out.println("Done");
	}

	private static void exportToMif(MifFileContent fileContent) throws FileNotFoundException, Exception
	{
		ExportToMif exporter = new ExportToMif(fileContent);

		Charset charset;
		try
		{
			charset = Charset.forName(fileContent.getCharset() != null ? fileContent.getCharset() : DEFAULT_CHARTSET);
		}
		catch (RuntimeException e)
		{
			charset = Charset.forName(DEFAULT_CHARTSET);
		}

		OutputStream output = new FileOutputStream(new File("C:/Test/SimpleParsed.mif"));
		exporter.export(output, charset);
	}

	private static void exportToJson(MifFileContent fileContent, String documentName) throws FileNotFoundException, Exception
	{
		ExportToJson exporter = new ExportToJson(fileContent, documentName);

		Charset charset;
		try
		{
			charset = Charset.forName(fileContent.getCharset() != null ? fileContent.getCharset() : DEFAULT_CHARTSET);
		}
		catch (RuntimeException e)
		{
			charset = Charset.forName(DEFAULT_CHARTSET);
		}

		OutputStream output = new FileOutputStream(new File("C:/Test/SimpleParsed.json"));
		exporter.export(output, charset);
	}
}
