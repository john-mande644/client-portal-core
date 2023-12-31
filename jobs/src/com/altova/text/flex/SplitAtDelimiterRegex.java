////////////////////////////////////////////////////////////////////////
//
// SplitAtDelimiterRegex.java
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

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.regex.*;

public class SplitAtDelimiterRegex extends Splitter {
private final static Logger log =  LogManager.getLogger();
	protected String pattern;
	protected boolean matchcase;
	protected String separatorforwriting;
	
	public SplitAtDelimiterRegex( String pattern, boolean matchcase, String separatorforwriting )
	{
		this.pattern = pattern;
		this.matchcase = matchcase;
		this.separatorforwriting = separatorforwriting;
	}
	
	// splits input range to result containing the head section and range the tail section
	// if no match occured, the head section contains all and the tail is empty (result.end == range.end)
	public Range split( Range range )
	{
		Range result = new Range(range);
		
		if ( pattern.length() == 0 )
		{
			range.start = range.end;
			return result;
		}
		
		result.end = range.start;

		int flag = 0;
		if ( !matchcase )
			flag |= Pattern.CASE_INSENSITIVE;
		Pattern re = Pattern.compile( pattern, flag );
		Matcher m = re.matcher( range.toString() );

		if ( m.find() )
		{
			result.end = m.start() + range.start;
			range.start = m.end() + range.start;
		}
		else
		{
			// full range in the head section
			result.end = range.end;
			range.start = result.end;
		}

		return result;
	}
	
	public void appendDelimiter( Appender output )
	{
		output.appendText( separatorforwriting );
	}
}
