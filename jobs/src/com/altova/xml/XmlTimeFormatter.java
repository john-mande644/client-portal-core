// XmlTimeFormatter.java
// This file contains generated code and will be overwritten when you rerun code generation.

package com.altova.xml;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.altova.types.DateTime;

public class XmlTimeFormatter extends XmlFormatter
{
private final static Logger log =  LogManager.getLogger();
	public String format(DateTime dt)
	{
		return dt.toTimeString("0.0######");
	}
}
