// DocumentOutput.java
// This file contains generated code and will be overwritten when you rerun code generation.

package com.altova.io;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.w3c.dom.Document;

public class DocumentOutput extends Output 
{
private final static Logger log =  LogManager.getLogger();
	private Document document;
	
	public DocumentOutput(Document document)
	{
		super(Output.IO_DOM);
		this.document = document;
	}
	
	public Document getDocument() {return document;}
}
