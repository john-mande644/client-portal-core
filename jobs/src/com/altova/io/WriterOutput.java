// WriterOutput.java
// This file contains generated code and will be overwritten when you rerun code generation.

package com.altova.io;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.io.Writer;

public class WriterOutput extends Output
{
private final static Logger log =  LogManager.getLogger();
	private Writer writer;
	
	public WriterOutput(Writer writer)
	{
		super(Output.IO_WRITER);
		this.writer = writer;
	}
	
	public Writer getWriter() {return writer;}
}
