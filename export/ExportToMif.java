package org.philhosoft.mif.export;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.philhosoft.mif.model.MifFileContent;
import org.philhosoft.mif.model.data.Arc;
import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.model.data.Ellipse;
import org.philhosoft.mif.model.data.Line;
import org.philhosoft.mif.model.data.None;
import org.philhosoft.mif.model.data.Point;
import org.philhosoft.mif.model.data.Polyline;
import org.philhosoft.mif.model.data.Rectangle;
import org.philhosoft.mif.model.data.Region;
import org.philhosoft.mif.model.data.RoundedRectangle;
import org.philhosoft.mif.model.data.Text;
import org.philhosoft.mif.parser.data.ArcParser;
import org.philhosoft.mif.parser.data.EllipseParser;
import org.philhosoft.mif.parser.data.LineParser;
import org.philhosoft.mif.parser.data.PointParser;
import org.philhosoft.mif.parser.data.PolylineParser;
import org.philhosoft.mif.parser.data.RectangleParser;
import org.philhosoft.mif.parser.data.RegionParser;
import org.philhosoft.mif.parser.data.RoundedRectangleParser;
import org.philhosoft.mif.parser.data.TextParser;

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
	public Boolean visit(Arc data, ExportToMif exporter)
	{
		try
		{
			exporter.write(ArcParser.KEYWORD);
		}
		catch (IOException e)
		{
			return false;
		}
		return true;
	}

	@Override
	public Boolean visit(Ellipse data, ExportToMif exporter)
	{
		try
		{
			exporter.write(EllipseParser.KEYWORD);
		}
		catch (IOException e)
		{
			return false;
		}
		return true;
	}

	@Override
	public Boolean visit(Line data, ExportToMif exporter)
	{
		try
		{
			exporter.write(LineParser.KEYWORD);
		}
		catch (IOException e)
		{
			return false;
		}
		return true;
	}

	@Override
	public Boolean visit(None data, ExportToMif exporter)
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
	public Boolean visit(Point data, ExportToMif exporter)
	{
		try
		{
			exporter.write(PointParser.KEYWORD);
		}
		catch (IOException e)
		{
			return false;
		}
		return true;
	}

	@Override
	public Boolean visit(Polyline data, ExportToMif exporter)
	{
		try
		{
			exporter.write(PolylineParser.KEYWORD);
		}
		catch (IOException e)
		{
			return false;
		}
		return true;
	}

	@Override
	public Boolean visit(Rectangle data, ExportToMif exporter)
	{
		try
		{
			exporter.write(RectangleParser.KEYWORD);
		}
		catch (IOException e)
		{
			return false;
		}
		return true;
	}

	@Override
	public Boolean visit(Region data, ExportToMif exporter)
	{
		try
		{
			exporter.write(RegionParser.KEYWORD);
		}
		catch (IOException e)
		{
			return false;
		}
		return true;
	}

	@Override
	public Boolean visit(RoundedRectangle data, ExportToMif exporter)
	{
		try
		{
			exporter.write(RoundedRectangleParser.KEYWORD);
		}
		catch (IOException e)
		{
			return false;
		}
		return true;
	}

	@Override
	public Boolean visit(Text data, ExportToMif exporter)
	{
		try
		{
			exporter.write(TextParser.KEYWORD);
		}
		catch (IOException e)
		{
			return false;
		}
		return true;
	}
}
