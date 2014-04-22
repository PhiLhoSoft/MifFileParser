package org.philhosoft.mif.parser.data;

import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.model.data.MifPolyline;
import org.philhosoft.mif.parser.MifDefaultParser;
import org.philhosoft.mif.parser.MifReader;

/*
PLINE [ MULTIPLE  numsections ]
  numpts1
x1 y1
x2 y2
 :
[  numpts2
x1 y1
x2 y2  ]
 :
[ PEN (width, pattern, color) ]
[ SMOOTH ]
*/
public class MifPolylineParser extends MifDefaultParser implements MifDataParser
{
	public static final String KEYWORD = "PLINE";

	@Override
	public String getKeyword()
	{
		return KEYWORD;
	}

   @Override
   public MifData parseData(MifReader reader)
   {
      return new MifPolyline();
   }
}
