/**
 * MFElement.java
 *
 * This file was generated by MapForce 2015r3.
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

public class MFCData implements IMFNode 
{
private final static Logger log =  LogManager.getLogger();
	private String content;
	
	public MFCData(String content)
	{ 
		this.content = content;
	}
	
	public int getNodeKind() { return MFNodeKind_CData; }
	public String getLocalName() { return ""; }
	public String getNamespaceURI() { return ""; }
	public String getPrefix() { return ""; }
    public String getNodeName() { return "CData"; }
	public QName getQName() { return new QName("", ""); }
	public IEnumerable select(int mfQueryKind, Object query) { return new MFSingletonSequence(content); }
	public String value() throws Exception { return content; } 
	public QName qnameValue() { return null; }
	public Object typedValue() throws Exception
	{
		return MFNode.collectTypedValue(select(IMFNode.MFQueryKind_AllChildren, null));
	}
}
