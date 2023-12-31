////////////////////////////////////////////////////////////////////////
//
// SplitAtDelimiterLineStartsWithRegex.java
//
// This file was generated by MapForce 2015r3.
//
// YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
// OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
//
// Refer to the MapForce Documentation for further details.
// http://www.altova.com/mapforce
//
////////////////////////////////////////////////////////////////////////

package com.altova.text.flex;

public class SplitAtDelimiterLineStartsWithRegex extends SplitAtDelimiterRegex {
	protected boolean consumeFirstLine;

	public SplitAtDelimiterLineStartsWithRegex( String pattern, boolean matchcase ) {
		super( pattern, matchcase, null );
		consumeFirstLine = false;
	}
	
	public Range split( Range range )
	{
		Range result = new Range(range);

		if ( pattern.length() == 0 )
		{
			range.start = range.end;
			return result;
		}

		SplitLines splitAtFirstLine = new SplitLines(1);
		boolean firstLine = true;
		while ( true )
		{
			Range line = splitAtFirstLine.split( range );
			if ( !line.isValid() )
			{
				result.end = line.start;
				break;
			}
			Range head = super.split( line );
			if ( head.end != line.end && head.start == head.end )
			{
				if (!firstLine)
				{
					result.end = head.start;
					break;
				}
			}
			firstLine = false;
		}
		range.start = result.end;
		return result;
	}
	
	public void appendDelimiter( Appender output ) {}
}
