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
	public static final String DEFAULT_CHARSET = "UTF-8";

	public static void main(String[] args) throws FileNotFoundException, IOException, Exception
	{
		MifFileParser parser = new MifFileParser();
		if (args.length == 0)
		{
			System.out.println("Usage: java " + parser.getClass().getName() + " fileToParse [I]\n" +
					"If the I argument is given, the y coordinates are inverted in Json export.");
			return;
		}
		parser.process(args[0], args.length > 1 && args[1].equalsIgnoreCase("I"));
	}

	private void process(String fileName, boolean invert) throws FileNotFoundException, IOException, Exception
	{
		System.out.println("Start");
		File mifFile = new File(fileName);
		InputStream is = new FileInputStream(mifFile);
		MifReader reader = new MifReader(is);
		ParsingContext context = new ParsingContext(reader);

		MifFileContentParser parser = new MifFileContentParser();
		MifFileContent fileContent = null;
		try
		{
			fileContent = parser.parseContent(context);
		}
		catch (RuntimeException e)
		{
			System.err.println(e.getMessage());
			// Can still use a partial result, perhaps
			fileContent = parser.getFileContent();
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
			return; // Cannot export if we have errors
		}
		// Show warnings
		System.out.println(collector);

		if (fileContent == null)
		{
			// An exception has been thrown (and caught), don't export
			System.err.println("Had errors (see above)");
		}
		else
		{
			doExports(mifFile, fileContent, invert);
		}

		System.out.println("Done");
	}

	private void doExports(File mifFile, MifFileContent fileContent, boolean invert) throws FileNotFoundException, Exception
	{
		String documentName = mifFile.getName();
		String documentPath = mifFile.getAbsolutePath();
		String mifPath = documentPath.replaceFirst("\\.[mM][iI][fF]$", "_export.mif");
		System.out.println("Export to " + mifPath);
		exportToMif(fileContent, mifPath);
		String jsonPath = documentPath.replaceFirst("\\.[mM][iI][fF]$", "_export.json");
		System.out.println("Export to " + jsonPath);
		exportToJson(fileContent, documentName, jsonPath, invert);
	}

	private static void exportToMif(MifFileContent fileContent, String outputPath) throws FileNotFoundException, Exception
	{
		ExportToMif exporter = new ExportToMif(fileContent);

		Charset charset;
		try
		{
			charset = Charset.forName(fileContent.getCharset() != null ? fileContent.getCharset() : DEFAULT_CHARSET);
		}
		catch (RuntimeException e)
		{
			charset = Charset.forName(DEFAULT_CHARSET);
		}

		OutputStream output = new FileOutputStream(new File(outputPath));
		exporter.export(output, charset);
	}

	private static void exportToJson(MifFileContent fileContent, String documentName, String outputPath, boolean invert) throws FileNotFoundException, Exception
	{
		ExportToJson exporter = new ExportToJson(fileContent, documentName, invert);

		Charset charset;
		try
		{
			charset = Charset.forName(fileContent.getCharset() != null ? fileContent.getCharset() : DEFAULT_CHARSET);
		}
		catch (RuntimeException e)
		{
			charset = Charset.forName(DEFAULT_CHARSET);
		}

		OutputStream output = new FileOutputStream(new File(outputPath));
		exporter.export(output, charset);
	}
}
