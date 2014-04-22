package org.philhosoft.mif.export;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.philhosoft.mif.model.MifFileContent;
import org.philhosoft.mif.model.data.MifArc;
import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.model.data.MifEllipse;
import org.philhosoft.mif.model.data.MifLine;
import org.philhosoft.mif.model.data.MifNone;
import org.philhosoft.mif.model.data.MifPoint;
import org.philhosoft.mif.model.data.MifPolyline;
import org.philhosoft.mif.model.data.MifRectangle;
import org.philhosoft.mif.model.data.MifRegion;
import org.philhosoft.mif.model.data.MifRoundedRectangle;
import org.philhosoft.mif.model.data.MifText;
import org.philhosoft.mif.parser.data.MifArcParser;
import org.philhosoft.mif.parser.data.MifEllipseParser;
import org.philhosoft.mif.parser.data.MifLineParser;
import org.philhosoft.mif.parser.data.MifPointParser;
import org.philhosoft.mif.parser.data.MifPolylineParser;
import org.philhosoft.mif.parser.data.MifRectangleParser;
import org.philhosoft.mif.parser.data.MifRegionParser;
import org.philhosoft.mif.parser.data.MifRoundedRectangleParser;
import org.philhosoft.mif.parser.data.MifTextParser;

/**
 * Exports a Mif data structure to the Mif format.
 */
public class ExportToMif
{
	private MifFileContent fileContent;
	private OutputStream output;
	private Charset charset;

	public ExportToMif(MifFileContent fileContent)
	{
		this.fileContent = fileContent;
	}

	public void export(OutputStream output, Charset charset) throws IOException
	{
		this.output = output;
		this.charset = charset;

		// TODO write header

		ToMifVisitor visitor = new ToMifVisitor();
		for (MifData data : fileContent.getMifData())
		{
			if (!data.accept(visitor, this))
			{
				System.err.println("Error on " + data); // TODO real logs...
			}
		}
	}

	public void write(String line) throws IOException
	{
		line += "\n";
		output.write(line.getBytes(charset));
	}
}

class ToMifVisitor implements MifData.Visitor<ExportToMif, Boolean>
{
	@Override
	public Boolean visit(MifArc data, ExportToMif exporter)
	{
		try
		{
			exporter.write(MifArcParser.KEYWORD);
		}
		catch (IOException e)
		{
			return false;
		}
		return true;
	}

	@Override
	public Boolean visit(MifEllipse data, ExportToMif exporter)
	{
		try
		{
			exporter.write(MifEllipseParser.KEYWORD);
		}
		catch (IOException e)
		{
			return false;
		}
		return true;
	}

	@Override
	public Boolean visit(MifLine data, ExportToMif exporter)
	{
		try
		{
			exporter.write(MifLineParser.KEYWORD);
		}
		catch (IOException e)
		{
			return false;
		}
		return true;
	}

	@Override
	public Boolean visit(MifNone data, ExportToMif exporter)
	{
		try
		{
			exporter.write("");
		}
		catch (IOException e)
		{
			return false;
		}
		return true;
	}

	@Override
	public Boolean visit(MifPoint data, ExportToMif exporter)
	{
		try
		{
			exporter.write(MifPointParser.KEYWORD);
		}
		catch (IOException e)
		{
			return false;
		}
		return true;
	}

	@Override
	public Boolean visit(MifPolyline data, ExportToMif exporter)
	{
		try
		{
			exporter.write(MifPolylineParser.KEYWORD);
		}
		catch (IOException e)
		{
			return false;
		}
		return true;
	}

	@Override
	public Boolean visit(MifRectangle data, ExportToMif exporter)
	{
		try
		{
			exporter.write(MifRectangleParser.KEYWORD);
		}
		catch (IOException e)
		{
			return false;
		}
		return true;
	}

	@Override
	public Boolean visit(MifRegion data, ExportToMif exporter)
	{
		try
		{
			exporter.write(MifRegionParser.KEYWORD);
		}
		catch (IOException e)
		{
			return false;
		}
		return true;
	}

	@Override
	public Boolean visit(MifRoundedRectangle data, ExportToMif exporter)
	{
		try
		{
			exporter.write(MifRoundedRectangleParser.KEYWORD);
		}
		catch (IOException e)
		{
			return false;
		}
		return true;
	}

	@Override
	public Boolean visit(MifText data, ExportToMif exporter)
	{
		try
		{
			exporter.write(MifTextParser.KEYWORD);
		}
		catch (IOException e)
		{
			return false;
		}
		return true;
	}
}
