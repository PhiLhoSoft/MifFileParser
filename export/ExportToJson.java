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
import org.philhosoft.mif.parser.data.LineParser;
import org.philhosoft.mif.parser.data.PolylineParser;
import org.philhosoft.mif.parser.data.RectangleParser;
import org.philhosoft.mif.parser.data.RegionParser;
import org.philhosoft.mif.parser.data.RoundedRectangleParser;


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

		DataDispatchVisitor dispatchVisitor = new DataDispatchVisitor();

		// Dispatch information between regions (areas of interest) and cosmetic (lines to improve maps)
		for (MifData data : fileContent.getMifData())
		{
			if (data.accept(dispatchVisitor, null))
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
		write("  \"paths\":\n  [\n");

		{
			int last = paths.size();
			int count = 0;
			for (MifData data : paths)
			{
				data.accept(jsonVisitor, this);
				if (count++ < last - 1)
				{
					write(",\n");
				}
			}
		}

		write("\n  ]");

		if (!mapLines.isEmpty())
		{
			write(",\n");
			write("  \"mapLines\":\n  [\n");

			int last = mapLines.size();
			int count = 0;
			for (MifData data : mapLines)
			{
				data.accept(jsonVisitor, this);
				if (count++ < last - 1)
				{
					write(",\n");
				}
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
		write("      \"path\": \"M " + path + (close ? "Z" : "") + "\"\n");
		write("    }");
	}

	/** Primitive Double To String function, just keeping int values as such (no decimal point). */
	private static String dts(double d)
	{
		if (Math.floor(d) == d) return Integer.toString((int) d);
		return Double.toString(d);
	}

	/**
	 * Returns true if the class must be exported in "paths", false if it must go to "mapLines" (cosmetic).
	 */
	private static class DataDispatchVisitor implements MifData.Visitor<Void, Boolean>
	{
		@Override
		public Boolean visit(Arc data, Void in) throws Exception
		{
			return false;
		}

		@Override
		public Boolean visit(Ellipse data, Void in) throws Exception
		{
			return false;
		}

		@Override
		public Boolean visit(Line data, Void in) throws Exception
		{
			return false;
		}

		@Override
		public Boolean visit(None data, Void in) throws Exception
		{
			return false;
		}

		@Override
		public Boolean visit(Point data, Void in) throws Exception
		{
			return false;
		}

		@Override
		public Boolean visit(Polyline data, Void in) throws Exception
		{
			return true;
		}

		@Override
		public Boolean visit(Rectangle data, Void in) throws Exception
		{
			return false;
		}

		@Override
		public Boolean visit(Region data, Void in) throws Exception
		{
			return true;
		}

		@Override
		public Boolean visit(RoundedRectangle data, Void in) throws Exception
		{
			return false;
		}

		@Override
		public Boolean visit(Text data, Void in) throws Exception
		{
			return false;
		}
	}

	private static class DataToJsonVisitor implements MifData.Visitor<ExportToJson, Void>
	{
		private int counter; // Ensure uniqueness of ids

		private void writePolygon(String id, List<CoordinatePair> polygon, boolean close, ExportToJson exporter) throws IOException
		{
			StringBuilder path = new StringBuilder();
			for (CoordinatePair cp : polygon)
			{
				path.append(dts(cp.getX())).append(',').append(dts(cp.getY())).append(' ');
			}
			exporter.writePolygon(id, path.toString(), close);
//			exporter.writeNewline();
		}

		private String makeId(String prefix)
		{
			return prefix + "-" + ++counter;
		}

		@Override
		public Void visit(Arc data, ExportToJson exporter) throws Exception
		{
			return null;
		}

		@Override
		public Void visit(Ellipse data, ExportToJson exporter) throws Exception
		{
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
			return null;
		}

		@Override
		public Void visit(Point data, ExportToJson exporter) throws Exception
		{
			return null;
		}

		@Override
		public Void visit(Polyline data, ExportToJson exporter) throws Exception
		{
			for (List<CoordinatePair> section : data.getSections())
			{
				writePolygon(makeId(PolylineParser.KEYWORD), section, false, exporter); // Not closed
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
			for (List<CoordinatePair> polygon : data.getPolygons())
			{
				writePolygon(makeId(RegionParser.KEYWORD), polygon, true, exporter); // Closed
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
			return null;
		}
	}
}
