package org.philhosoft.mif.model;

import java.util.ArrayList;
import java.util.List;

import org.philhosoft.mif.model.data.MifData;

/**
 * A MIF file: header and data.<br>
 * See http://reference.mapinfo.com/software/spatial_server/english/1_0/onprem/apiguide/LIM/source/UserDefinedSpatialDatabases/mapinfomifmidformat.html
 */
public class MifFileContent
{
	private String version;
	private String charset;
	private String delimiter;
	private List<MifColumn> columns = new ArrayList<MifColumn>();
	private List<MifData> mifData = new ArrayList<MifData>();

	public String getVersion()
	{
		return version;
	}
	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getCharset()
	{
		return charset;
	}
	public void setCharset(String charset)
	{
		this.charset = charset;
	}

	public String getDelimiter()
	{
		return delimiter;
	}
	public void setDelimiter(String delimiter)
	{
		this.delimiter = delimiter;
	}

	public List<MifColumn> getColumns()
	{
		return columns;
	}
	public void addColumn(MifColumn column)
	{
		columns.add(column);
	}

	public List<MifData> getMifData()
	{
		return mifData;
	}
	public void add(MifData data)
	{
		mifData.add(data);
	}
}

