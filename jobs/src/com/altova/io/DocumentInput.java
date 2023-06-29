// DocumentInput.java
// This file contains generated code and will be overwritten when you rerun code generation.

package com.altova.io;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.w3c.dom.Document;

public class DocumentInput extends Input 
{
private final static Logger log =  LogManager.getLogger();
	private Document document;
	
	public DocumentInput(Document document)
	{
		super(Input.IO_DOM);
		this.document = document;
	}

	public Document getDocument() {return document;}
}
