package org.philhosoft.mif.export;


import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
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
import org.philhosoft.mif.model.parameter.CoordinatePair;
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
 * Exports a Mif data structure to the Json format used to feed a HighMaps map.
 */
public class ExportToJson
{
	private MifFileContent fileContent;
	private OutputStream output;
	private Charset charset;
	private String documentName;

	public ExportToJson(MifFileContent fileContent, String documentName)
	{
		this.documentName = documentName;
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

		writeJsonFile();
	}

	private void writeJsonFile() throws IOException, Exception
	{
		List<MifData> paths = new ArrayList<>();
		List<MifData> mapLines = new ArrayList<>();

		// Dispatch information between regions (areas of interest) and cosmetic (lines to improve maps)
		for (MifData data : fileContent.getMifData())
		{
			if (data instanceof Region) // Could use another visitor, but that's the only data we want there
			{
				paths.add(data);
			}
			else
			{
				mapLines.add(data);
			}
		}

		DataToJsonVisitor jsonVisitor = new DataToJsonVisitor();

		write("{\n");
		write("  \"name\":", "\"" + documentName + "\",\n");
		if (!paths.isEmpty())
		{
			write("  \"paths\":\n  [\n");

			boolean first = true;
			for (MifData data : paths)
			{
				if (!first)
				{
					write(",\n");
				}
				data.accept(jsonVisitor, this);
				first = false;
			}

			write("\n  ]");
		}

		if (!mapLines.isEmpty())
		{
			if (!paths.isEmpty())
			{
				write(",\n");
			}
			write("  \"mapLines\":\n  [\n");

			boolean first = true;
			for (MifData data : mapLines)
			{
				if (!first)
				{
					write(",\n");
				}
				data.accept(jsonVisitor, this);
				first = false;
			}

			write("\n  ]");
		}

		write("\n}\n");
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

	public void write(CoordinatePair... coordinates) throws Exception
	{
		for (CoordinatePair coordinatePair : coordinates)
		{
			output.write(' '); // Space separator
			write(dts(coordinatePair.getX()), dts(coordinatePair.getY()));
		}
	}

	public void writeWithNewline(String keyword, CoordinatePair... coordinates) throws Exception
	{
		output.write(keyword.getBytes(charset));
		write(coordinates);
		writeNewline();
	}

	public void writeNewline() throws IOException
	{
		output.write('\n');
	}

	private void writePolygon(String id, String path, boolean close) throws IOException
	{
		write("    {\n");
		write("      \"id\":", "\"" + id + "\",\n");
		write("      \"path\": \"M" + path + (close ? " Z" : "") + "\"\n");
		write("    }");
	}

	/** Primitive Double To String function, just keeping int values as such (no decimal point). */
	private static String dts(double d)
	{
		if (Math.floor(d) == d) return Integer.toString((int) d);
		return Double.toString(d);
	}

	private static class DataToJsonVisitor implements MifData.Visitor<ExportToJson, Void>
	{
		private int counter; // Ensure uniqueness of ids

		private void writePolygon(String id, List<CoordinatePair> polygon, boolean close, ExportToJson exporter) throws IOException
		{
			StringBuilder path = new StringBuilder();
			for (CoordinatePair cp : polygon)
			{
				path.append(' ').append(dts(cp.getX())).append(',').append(dts(cp.getY()));
			}
			exporter.writePolygon(id, path.toString(), close);
		}
		private void writeEmpty(String id, CoordinatePair point, ExportToJson exporter) throws IOException
		{
			if (point == null)
			{
				exporter.writePolygon(id, " 0,0", false);
			}
			else
			{
				exporter.writePolygon(id, " " + dts(point.getX()) + "," + dts(point.getY()), false);
			}
		}

		private String makeId(String prefix)
		{
			return prefix + "-" + ++counter;
		}

		@Override
		public Void visit(Arc data, ExportToJson exporter) throws Exception
		{
			writeEmpty(makeId(ArcParser.KEYWORD), data.getCorner1(), exporter); // Not supported yet
			return null;
		}

		@Override
		public Void visit(Ellipse data, ExportToJson exporter) throws Exception
		{
			writeEmpty(makeId(EllipseParser.KEYWORD), data.getCorner1(), exporter); // Not supported yet
			return null;
		}

		@Override
		public Void visit(Line data, ExportToJson exporter) throws Exception
		{
			List<CoordinatePair> line = new ArrayList<>();
			line.add(data.getStart());
			line.add(data.getEnd());
			writePolygon(makeId(LineParser.KEYWORD), line, false, exporter); // Not closed
			return null;
		}

		@Override
		public Void visit(None data, ExportToJson exporter) throws Exception
		{
			writeEmpty(makeId("None"), null, exporter); // Not supported
			return null;
		}

		@Override
		public Void visit(Point data, ExportToJson exporter) throws Exception
		{
			writeEmpty(makeId(PointParser.KEYWORD), data.getLocation(), exporter); // Not supported yet
			return null;
		}

		@Override
		public Void visit(Polyline data, ExportToJson exporter) throws Exception
		{
			int last = data.getSections().size();
			int count = 0;
			for (List<CoordinatePair> section : data.getSections())
			{
				writePolygon(makeId(PolylineParser.KEYWORD), section, false, exporter); // Not closed
				if (count++ < last - 1)
				{
					exporter.write(",\n");
				}
			}
			return null;
		}

		@Override
		public Void visit(Rectangle data, ExportToJson exporter) throws Exception
		{
			CoordinatePair corner1 = data.getCorner1();
			CoordinatePair corner2 = data.getCorner2();
			List<CoordinatePair> rectangle = new ArrayList<>();
			rectangle.add(corner1);
			rectangle.add(new CoordinatePair(corner2.getX(), corner1.getY()));
			rectangle.add(corner2);
			writePolygon(makeId(RectangleParser.KEYWORD), rectangle, true, exporter); // Closed
			return null;
		}

		@Override
		public Void visit(Region data, ExportToJson exporter) throws Exception
		{
			int last = data.getPolygons().size();
			int count = 0;
			for (List<CoordinatePair> polygon : data.getPolygons())
			{
				writePolygon(makeId(RegionParser.KEYWORD), polygon, true, exporter); // Closed
				if (count++ < last - 1)
				{
					exporter.write(",\n");
				}
			}
			return null;
		}

		@Override
		public Void visit(RoundedRectangle data, ExportToJson exporter) throws Exception
		{
			CoordinatePair corner1 = data.getCorner1();
			CoordinatePair corner2 = data.getCorner2();
			List<CoordinatePair> rectangle = new ArrayList<>();
			rectangle.add(corner1);
			rectangle.add(new CoordinatePair(corner2.getX(), corner1.getY()));
			rectangle.add(corner2);
			writePolygon(makeId(RoundedRectangleParser.KEYWORD), rectangle, true, exporter); // Closed
			return null;
		}

		@Override
		public Void visit(Text data, ExportToJson exporter) throws Exception
		{
			writeEmpty(makeId(TextParser.KEYWORD), data.getCorner1(), exporter); // Not supported yet
			return null;
		}
	}
}
