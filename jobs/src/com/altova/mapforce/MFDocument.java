/**
 * MFDocument.java
 *
 * This file was generated by MapForce 2010.
 *
 * YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
 * OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
 *
 * Refer to the MapForce Documentation for further details.
 * http://www.altova.com/mapforce
 */

package com.altova.mapforce;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.namespace.QName;

public class MFDocument implements IMFNode 
{
private final static Logger log =  LogManager.getLogger();
	private IEnumerable children;
	private String filename;
	
	public MFDocument(String filename, IEnumerable children)
	{
		this.filename = filename;
		this.children = children;
	}
	
	public String getLocalName() 
	{
		return "";
	}

	public String getNamespaceURI() 
	{
		return "";
	}
	
	public String getPrefix()
	{
		return "";
	}

    public String getNodeName()
    {
        return "";
    }

	public int getNodeKind() 
	{
		return MFNodeKind_Document;
	}

	public QName getQName() 
	{
		return null;
	}

	public QName qnameValue() 
	{
		return null;
	}

	public IEnumerable select(int mfQueryKind, Object query) 
	{
		switch (mfQueryKind)
		{
			case MFQueryKind_All:
				return children;
			
			default:
				throw new UnsupportedOperationException("Unsupported query type.");
		}
	}

	public String value() throws Exception 
	{
		return "";
	}
	
	public String getFilename()
	{
		return filename;
	}
	
	public Object typedValue() throws Exception
	{
		return null;
	}
}
