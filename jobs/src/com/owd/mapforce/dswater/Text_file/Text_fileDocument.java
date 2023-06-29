/**
 * Text_fileDocument.java
 *
 * This file was generated by MapForce 2015r3.
 *
 * YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
 * OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
 *
 * Refer to the MapForce Documentation for further details.
 * http://www.altova.com/mapforce
 */

package com.owd.mapforce.dswater.Text_file;



import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.altova.text.tablelike.ColumnSpecification;
import com.altova.text.tablelike.Header;
import com.altova.text.tablelike.ISerializer;
import com.altova.text.tablelike.csv.Table;
import com.altova.text.tablelike.csv.Serializer;

public class Text_fileDocument extends Table
{
private final static Logger log =  LogManager.getLogger();
	protected ISerializer createSerializer()
	{
		Serializer result= new Serializer(this,0);
		result.getFormat().setAssumeFirstRowAsHeaders(false);
		result.getFormat().setFieldDelimiter('\t');
		
		result.getFormat().setQuoteCharacter('\"');
		
		result.getFormat().setRemoveEmpty(false);
		result.getFormat().setAlwaysQuote(0 == 1);
		return result;
	}
	protected void initHeader(Header header)
	{
		
		header.add(new ColumnSpecification("buyerref"));
		header.add(new ColumnSpecification("orderref"));
		header.add(new ColumnSpecification("shipupc"));
		header.add(new ColumnSpecification("shipqty"));
	}
	public Text_fileDocument(com.altova.typeinfo.TypeInfo tableType,int lineend) {
        super( tableType,lineend);
    }
}



