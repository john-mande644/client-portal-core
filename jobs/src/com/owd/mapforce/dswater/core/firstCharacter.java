/**
 * core/firstCharacter.java
 *
 * This file was generated by MapForce 2015r3.
 *
 * YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
 * OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
 *
 * Refer to the MapForce Documentation for further details.
 * http://www.altova.com/mapforce
 */
package com.owd.mapforce.dswater.core;
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

public class firstCharacter extends com.altova.TraceProvider 
{
private final static Logger log =  LogManager.getLogger();

	
	static class main implements IEnumerable
	{
		java.lang.String var1_value;
		java.lang.String var2_default;
	
		public main(java.lang.String var1_value, java.lang.String var2_default)
		{
			this.var1_value = var1_value;
			this.var2_default = var2_default;
		}

		public IEnumerator enumerator() {return new Enumerator(this);}
		
		public static class Enumerator implements IEnumerator
		{
			int pos = 0;
			int state = 2;
			Object current;
			main closure;
			public Enumerator(main closure) 
			{
				this.closure = closure;
			}
			
			public Object current() {return current;}
			
			public int position() {return pos;}
			
			public boolean moveNext() throws Exception
			{
				while (state != 0)
				{
					switch (state) 
					{
					case 2:	if (moveNext_2()) return true; break;
 					}
				}
				return false;
			}

			private boolean moveNext_2() throws Exception {
				state = 0;				
				current = (java.lang.String)com.altova.functions.Core.first((new seq1_if_equal(closure.var1_value, closure.var2_default)));
				pos++;
				return true;
			}

			
			public void close()
			{
				try
				{
				}
				catch (Exception e)
				{
				}
			}
		}
				
	}
	static class seq1_if_equal implements IEnumerable
	{
		java.lang.String var1_value;
		java.lang.String var2_default;
	
		public seq1_if_equal(java.lang.String var1_value, java.lang.String var2_default)
		{
			this.var1_value = var1_value;
			this.var2_default = var2_default;
		}

		public IEnumerator enumerator() {return new Enumerator(this);}
		
		public static class Enumerator implements IEnumerator
		{
			int pos = 0;
			int state = 1;
			Object current;
			seq1_if_equal closure;
			double var3_cast;
			public Enumerator(seq1_if_equal closure) 
			{
				this.closure = closure;
			}
			
			public Object current() {return current;}
			
			public int position() {return pos;}
			
			public boolean moveNext() throws Exception
			{
				while (state != 0)
				{
					switch (state) 
					{
					case 1:	if (moveNext_1()) return true; break;
					case 2:	if (moveNext_2()) return true; break;
 					}
				}
				return false;
			}

			private boolean moveNext_1() throws Exception {
				state = 0;				
				if (!(com.altova.functions.Core.equal(com.altova.CoreTypes.integerToDecimal(com.altova.CoreTypes.longToInteger(com.altova.CoreTypes.intToLong(com.altova.functions.Core.stringLength(closure.var1_value)))), com.altova.CoreTypes.integerToDecimal(new java.math.BigInteger("0"))))) {state = 2; return false; }
				current = closure.var2_default;
				pos++;
				return true;
			}
			private boolean moveNext_2() throws Exception {
				state = 0;				
				var3_cast = com.altova.CoreTypes.integerToDouble(new java.math.BigInteger("1"));
				current = com.altova.functions.Core.substring(closure.var1_value, var3_cast, var3_cast);
				pos++;
				return true;
			}

			
			public void close()
			{
				try
				{
				}
				catch (Exception e)
				{
				}
			}
		}
				
	}



	// instances


	public static IEnumerable create(java.lang.String var1_value, java.lang.String var2_default)
	{
		return new main(
			var1_value,
			var2_default
			);

	}
	

	public static com.altova.mapforce.IEnumerable eval(java.lang.String var1_value, java.lang.String var2_default) throws java.lang.Exception
	{

		return create(var1_value,var2_default);
	
	}

}
