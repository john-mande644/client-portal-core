/**
 * vmf/vmf1_inputtoresult.java
 *
 * This file was generated by MapForce 2015r3.
 *
 * YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
 * OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
 *
 * Refer to the MapForce Documentation for further details.
 * http://www.altova.com/mapforce
 */
package com.owd.mapforce.dswater.vmf;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.altova.mapforce.*;
import com.altova.types.*;
import com.altova.xml.*;
import com.altova.db.*;
import com.altova.text.tablelike.*;
import com.altova.text.*;
import com.altova.text.edi.*;
import java.util.*;

public class vmf1_inputtoresult extends com.altova.TraceProvider 
{
private final static Logger log =  LogManager.getLogger();

	
	static class Main implements IEnumerable
	{
		java.lang.String var1_input;
	
		public Main(java.lang.String var1_input)
		{
			this.var1_input = var1_input;
		}

		public IEnumerator enumerator() {return new MFEmptySequence().enumerator();}
				
	}





	public static IEnumerable create(java.lang.String var1_input)
	{
		if (hash == null) init();
		Object o = hash.get(var1_input);
		if (o != null)
			return new MFSingletonSequence(o);
		else
			return new MFEmptySequence();

	}
	
	private static java.util.HashMap hash = null;

	private synchronized static void init()
	{
		hash = new java.util.HashMap();
		hash.put(com.altova.CoreTypes.box("DS-P206030"), com.altova.CoreTypes.box("632565000043"));
		hash.put(com.altova.CoreTypes.box("DS-P206031"), com.altova.CoreTypes.box("632565000128"));
		hash.put(com.altova.CoreTypes.box("DS-P206029"), com.altova.CoreTypes.box("632565000630"));
	}

	public static com.altova.mapforce.IEnumerable eval(java.lang.String var1_input) throws java.lang.Exception
	{

		return create(var1_input);
	
	}

}
