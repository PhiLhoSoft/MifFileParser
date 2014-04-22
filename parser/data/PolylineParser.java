package org.philhosoft.mif.parser.data;

import org.philhosoft.mif.model.data.MifData;
import org.philhosoft.mif.model.data.Polyline;
import org.philhosoft.mif.parser.DefaultParser;
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
public class PolylineParser extends DefaultParser implements MifDataParser
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
      return new Polyline();
   }
}
