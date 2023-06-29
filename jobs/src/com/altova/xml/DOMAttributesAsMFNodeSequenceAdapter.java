/**
 * DOMAttributesAsMFNodeSequenceAdapter.java
 *
 * This file was generated by MapForce 2015r3.
 *
 * YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
 * OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
 *
 * Refer to the MapForce Documentation for further details.
 * http://www.altova.com/mapforce
 */


package com.altova.xml;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import com.altova.mapforce.IEnumerable;
import com.altova.mapforce.IEnumerator;

public class DOMAttributesAsMFNodeSequenceAdapter implements IEnumerable 
{
private final static Logger log =  LogManager.getLogger();
	public static class Enumerator implements IEnumerator
	{
		int current = -1;
		NamedNodeMap attrs;
		int pos = 0;
		
		public Enumerator(NamedNodeMap attrs)
		{
			this.attrs = attrs;
		}
		
		public Object current()
		{
			if (current == -1) 
				throw new UnsupportedOperationException("No current.");
			
			return new DOMNodeAsMFNodeAdapter(attrs.item(current));
		}
		
		public int position() {return pos;}
		
		public boolean moveNext()
		{
			++current;
			if (current < attrs.getLength())
			{
				pos++;
				return true;
			}
			return false;
		}
		
		public void close() {}
	}
	
	NamedNodeMap attrs;
	
	public DOMAttributesAsMFNodeSequenceAdapter(Node from)
	{
		this.attrs = from.getAttributes();
	}
	
	public IEnumerator enumerator() 
	{
		return new Enumerator(attrs);
	}
}
