// StringOutput.java
// This file contains generated code and will be overwritten when you rerun code generation.package com.altova.io;

package com.altova.io;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.io.StringWriter;

public class StringOutput extends WriterOutput 
{	
private final static Logger log =  LogManager.getLogger();
	public StringOutput()
	{
		super(new StringWriter());
	}
	
	public StringBuffer getString() {return ((StringWriter)getWriter()).getBuffer();}
}
