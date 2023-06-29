// ReaderInput.java
// This file contains generated code and will be overwritten when you rerun code generation.package com.altova.io;

package com.altova.io;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.io.Reader;

public class ReaderInput extends Input
{
private final static Logger log =  LogManager.getLogger();
	private Reader reader;
	
	public ReaderInput(Reader reader)
	{
		super(Input.IO_READER);
		this.reader = reader;
	}
	
	public Reader getReader() {return reader;}
}

