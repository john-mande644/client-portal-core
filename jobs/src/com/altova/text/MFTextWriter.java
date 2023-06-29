////////////////////////////////////////////////////////////////////////
//
// MFTextWriter.java
//
// This file was generated by MapForce 2015r3.
//
// YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
// OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
//
// Refer to the MapForce Documentation for further details.
// http://www.altova.com/mapforce
//
////////////////////////////////////////////////////////////////////////

 
package com.altova.text;
 
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.HashMap;

import com.altova.text.tablelike.Table;
import com.altova.text.tablelike.Record;

import com.altova.mapforce.IMFNode;
import com.altova.mapforce.IEnumerable;
import com.altova.mapforce.IEnumerator;

public class MFTextWriter
{
private final static Logger log =  LogManager.getLogger();
	public static void write(IEnumerable what, ITextNode where) throws Exception
	{
		for (IEnumerator en = what.enumerator(); en.moveNext();)
		{
			if (en.current() instanceof IMFNode)
			{
				IMFNode el = (IMFNode)en.current();
				if ((el.getNodeKind() & (IMFNode.MFNodeKind_Element | IMFNode.MFNodeKind_Attribute)) != 0)
				{
					ITextNode child = new TextNode(where, el.getLocalName());
					write(el.select(IMFNode.MFQueryKind_All, null), child);
				}
				else
					write(el.select(IMFNode.MFQueryKind_AllChildren, null), where);
			}
			else
			{
				where.setValue(en.current().toString());
			}
		}
	}
	
	public static void write(IEnumerable what, Table where) throws Exception
	{
		HashMap<String,Integer> hash = new HashMap<String,Integer>();
		for( int i = 0 ; i < where.getTableType().getMembers().length ; ++i )
		{
			com.altova.typeinfo.MemberInfo member = where.getTableType().getMembers()[i];
			hash.put(member.getLocalName(),i);
		}
		
		for (IEnumerator en = what.enumerator(); en.moveNext();)
		{
			if (en.current() instanceof IMFNode)
			{
				IMFNode n  = (IMFNode) en.current();
				Record record = new Record(where.getHeader().size());
				
				IEnumerable children = n.select(IMFNode.MFQueryKind_All, null);
				for (IEnumerator en_chi = children.enumerator(); en_chi.moveNext();)
				{
					if (en_chi.current() instanceof IMFNode)
					{
						IMFNode n_chi = (IMFNode) en_chi.current();
						if (hash.containsKey(n_chi.getLocalName()))
							record.setAt(((Integer) hash.get(n_chi.getLocalName())).intValue(), getValue(n_chi));
					}
				}
				
				where.add(record);
			}
		}
	}
	
	public static String getValue(Object o) throws Exception
	{
		return (o instanceof IMFNode) ? ((IMFNode)o).value() : o.toString();
	}
}
