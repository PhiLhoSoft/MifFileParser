package org.philhosoft.mif.export;


import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

import org.philhosoft.mif.model.MifFileContent;
import org.philhosoft.mif.model.data.Arc;
import org.philhosoft.mif.model.data.Ellipse;
import org.philhosoft.mif.model.data.Line;
import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.model.data.None;
import org.philhosoft.mif.model.data.Point;
import org.philhosoft.mif.model.data.Polyline;
import org.philhosoft.mif.model.data.Rectangle;
import org.philhosoft.mif.model.data.Region;
import org.philhosoft.mif.model.data.RoundedRectangle;
import org.philhosoft.mif.model.data.Text;
import org.philhosoft.mif.model.parameter.Brush;
import org.philhosoft.mif.model.parameter.CoordinatePair;
import org.philhosoft.mif.model.parameter.MifDataParameter;
import org.philhosoft.mif.model.parameter.Pen;
import org.philhosoft.mif.model.parameter.Symbol;
import org.philhosoft.mif.parser.HeaderParser;
import org.philhosoft.mif.parser.data.ArcParser;
import org.philhosoft.mif.parser.data.EllipseParser;
import org.philhosoft.mif.parser.data.LineParser;
import org.philhosoft.mif.parser.data.PointParser;
import org.philhosoft.mif.parser.data.PolylineParser;
import org.philhosoft.mif.parser.data.RectangleParser;
import org.philhosoft.mif.parser.data.RegionParser;
import org.philhosoft.mif.parser.data.RoundedRectangleParser;
import org.philhosoft.mif.parser.data.TextParser;
import org.philhosoft.mif.parser.parameter.BrushParser;
import org.philhosoft.mif.parser.parameter.CenterParser;
import org.philhosoft.mif.parser.parameter.PenParser;
import org.philhosoft.mif.parser.parameter.SymbolParser;


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
		if (fileContent == null) throw new IllegalArgumentException("File content cannot be null");

		this.fileContent = fileContent;
	}

	/**
	 * Exports the file content to the given output stream with the given charset.
	 *
	 * @throws the generic Exception but it is likely to be IOException.
	 */
	public void export(OutputStream output, Charset charset) throws Exception
	{
		if (output == null)
			throw new IllegalArgumentException("You must specify an output stream");
		if (charset == null)
			throw new IllegalArgumentException("You must give a charset");

		this.output = output;
		this.charset = charset;

		write(HeaderParser.HEADER_VERSION, fileContent.getVersion());
		writeNewline();
		write(HeaderParser.HEADER_CHARSET, fileContent.getCharset());
		writeNewline();
		write(HeaderParser.HEADER_DATA);
		writeNewline();

		DataToMifVisitor visitor = new DataToMifVisitor();
		for (MifData data : fileContent.getMifData())
		{
			data.accept(visitor, this);
		}
	}

	public void write(String... lineParts) throws IOException
	{
		boolean first = true;
		for (String linePart : lineParts)
		{
			if (first)
			{
				first = false;
			}
			else
			{
				output.write(' '); // Space separator
			}
			output.write(linePart.getBytes(charset));
		}
	}

	public void writeWithNewline(String keyword, DataParameterToMifVisitor parameterVisitor, MifDataParameter... parameters) throws Exception
	{
		output.write(keyword.getBytes(charset));
		for (MifDataParameter parameter : parameters)
		{
			output.write(' '); // Space separator
			parameter.accept(parameterVisitor, this);
		}
		writeNewline();
	}

	public void writeNewline() throws IOException
	{
		output.write('\n');
	}

	/** Primitive Double To String function, just keeping int values as such (no decimal point). */
	private static String dts(double d)
	{
		if (Math.floor(d) == d) return Integer.toString((int) d);
		return Double.toString(d);
	}

	private static class DataToMifVisitor implements MifData.Visitor<ExportToMif, Void>
	{
		private DataParameterToMifVisitor parameterVisitor = new DataParameterToMifVisitor();

		private void write(Pen pen, ExportToMif exporter) throws Exception
		{
			if (pen != null)
			{
				pen.accept(parameterVisitor, exporter);
			}
		}
		private void write(Brush brush, ExportToMif exporter) throws Exception
		{
			if (brush != null)
			{
				brush.accept(parameterVisitor, exporter);
			}
		}
		private void write(Symbol symbol, ExportToMif exporter) throws Exception
		{
			if (symbol != null)
			{
				symbol.accept(parameterVisitor, exporter);
			}
		}
		private void write(CoordinatePair pair, ExportToMif exporter) throws Exception
		{
			pair.accept(parameterVisitor, exporter);
			exporter.writeNewline();
		}

		@Override
		public Void visit(Arc data, ExportToMif exporter) throws Exception
		{
			exporter.writeWithNewline(ArcParser.KEYWORD, parameterVisitor, data.getCorner1(), data.getCorner2());
			exporter.write(dts(data.getStartAngle()), dts(data.getEndAngle()));
			exporter.writeNewline();
			return null;
		}

		@Override
		public Void visit(Ellipse data, ExportToMif exporter) throws Exception
		{
			exporter.writeWithNewline(EllipseParser.KEYWORD, parameterVisitor, data.getCorner1(), data.getCorner2());
			write(data.getPen(), exporter);
			write(data.getBrush(), exporter);
			return null;
		}

		@Override
		public Void visit(Line data, ExportToMif exporter) throws Exception
		{
			exporter.writeWithNewline(LineParser.KEYWORD, parameterVisitor, data.getStart(), data.getEnd());
			write(data.getPen(), exporter);
			return null;
		}

		@Override
		public Void visit(None data, ExportToMif exporter) throws Exception
		{
			exporter.writeNewline();
			return null;
		}

		@Override
		public Void visit(Point data, ExportToMif exporter) throws Exception
		{
			exporter.writeWithNewline(PointParser.KEYWORD, parameterVisitor, data.getLocation());
			write(data.getSymbol(), exporter);
			return null;
		}

		@Override
		public Void visit(Polyline data, ExportToMif exporter) throws Exception
		{
			exporter.write(PolylineParser.KEYWORD);
			if (data.getSections().size() > 1)
			{
				exporter.write(" ", PolylineParser.PARAMETER_MULTIPLE, dts(data.getSections().size()));
			}
			exporter.writeNewline();
			for (List<CoordinatePair> section : data.getSections())
			{
				exporter.write(" ", dts(section.size()));
				exporter.writeNewline();
				for (CoordinatePair pair : section)
				{
					write(pair, exporter);
				}
			}

			write(data.getPen(), exporter);
			if (data.isSmooth())
			{
				exporter.write(PolylineParser.PARAMETER_SMOOTH);
				exporter.writeNewline();
			}
			return null;
		}

		@Override
		public Void visit(Rectangle data, ExportToMif exporter) throws Exception
		{
			exporter.writeWithNewline(RectangleParser.KEYWORD, parameterVisitor, data.getCorner1(), data.getCorner2());
			write(data.getPen(), exporter);
			write(data.getBrush(), exporter);
			return null;
		}

		@Override
		public Void visit(Region data, ExportToMif exporter) throws Exception
		{
			exporter.write(RegionParser.KEYWORD, dts(data.getPolygons().size()));
			exporter.writeNewline();
			for (List<CoordinatePair> section : data.getPolygons())
			{
				exporter.write(" ", dts(section.size()));
				exporter.writeNewline();
				for (CoordinatePair pair : section)
				{
					write(pair, exporter);
				}
			}

			write(data.getPen(), exporter);
			write(data.getBrush(), exporter);
			CoordinatePair center = data.getCenter();
			if (center != null)
			{
				exporter.writeWithNewline(CenterParser.KEYWORD, parameterVisitor, center);
			}
			return null;
		}

		@Override
		public Void visit(RoundedRectangle data, ExportToMif exporter) throws Exception
		{
			exporter.writeWithNewline(RoundedRectangleParser.KEYWORD, parameterVisitor, data.getCorner1(), data.getCorner2());
			exporter.write(dts(data.getDegreeOfRounding()));
			exporter.writeNewline();
			write(data.getPen(), exporter);
			write(data.getBrush(), exporter);
			return null;
		}

		@Override
		public Void visit(Text data, ExportToMif exporter) throws Exception
		{
			exporter.write(TextParser.KEYWORD, "\"" + data.getText() + "\"");
			exporter.writeNewline();
			exporter.writeWithNewline("", parameterVisitor, data.getCorner1(), data.getCorner2());
			return null;
		}
	}

	private static class DataParameterToMifVisitor implements MifDataParameter.Visitor<ExportToMif, Void>
	{
		/** Write a parameter name and its triplet of integer parameters. */
		private void write(ExportToMif exporter, String keyword, int p1, int p2, int p3) throws IOException
		{
			exporter.write(keyword, " (" + dts(p1) + ", " + dts(p2) + ", " + dts(p3) + ")");
			exporter.writeNewline();
		}

		/** No newline on this one, to allow displaying two pairs on the same line. */
		@Override
		public Void visit(CoordinatePair parameter, ExportToMif exporter) throws Exception
		{
			exporter.write(dts(parameter.getX()), dts(parameter.getY()));
			return null;
		}

		@Override
		public Void visit(Brush parameter, ExportToMif exporter) throws Exception
		{
			write(exporter, BrushParser.KEYWORD, parameter.getPattern(), parameter.getForeColor(), parameter.getBackColor());
			return null;
		}

		@Override
		public Void visit(Pen parameter, ExportToMif exporter) throws Exception
		{
			write(exporter, PenParser.KEYWORD, parameter.getWidth(), parameter.getPattern(), parameter.getColor());
			return null;
		}

		@Override
		public Void visit(Symbol parameter, ExportToMif exporter) throws Exception
		{
			write(exporter, SymbolParser.KEYWORD, parameter.getShape(), parameter.getColor(), parameter.getSize());
			return null;
		}
	}
}
