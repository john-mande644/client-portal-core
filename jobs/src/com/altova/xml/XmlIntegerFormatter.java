// XmlIntegerFormatter.java
// This file contains generated code and will be overwritten when you rerun code generation.

package com.altova.xml;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.altova.CoreTypes;

class XmlIntegerFormatter extends XmlFormatter
{
	public String format(double v)
	{
		return CoreTypes.castToString((long) v);
	}
}
