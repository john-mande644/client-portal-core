// TextDateFormatter.java
// This file contains generated code and will be overwritten when you rerun code generation.

package com.altova.text;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.altova.types.DateTime;

public class TextDateFormatter extends TextFormatter
{
private final static Logger log =  LogManager.getLogger();
	public String format(DateTime dt)
	{
		return dt.toDateString(true);
	}
}
