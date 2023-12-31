////////////////////////////////////////////////////////////////////////
//
// Serializer.java
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

package com.altova.text.tablelike.csv;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.io.IOException;

import com.altova.text.tablelike.Record;
import com.altova.text.tablelike.RecordBasedParser;
import com.altova.text.tablelike.Table;

public class Serializer extends com.altova.text.tablelike.Serializer {
private final static Logger log =  LogManager.getLogger();
    private Format m_Format = new Format(0);

    private boolean m_WaitingForHeader = false;

    private void writeFieldEnd() throws IOException {
        super.getStream().write(m_Format.getFieldDelimiter());
    }

    private void writeRecordEnd() throws IOException {
        super.getStream().write(m_Format.getLineEnd());
    }

    private boolean doesContainQuoteNeedingCharacters(String rhs) {
        final char[] lookfor = m_Format.getQuoteNeedingCharacters();
        for (int i = 0; i < lookfor.length; ++i)
            if (0 <= rhs.indexOf(lookfor[i]))
                return true;
        return false;
    }

    private boolean doesStartOrEndWithWhiteSpace(String rhs) {
        return (!rhs.equals(rhs.trim()));
    }

    private String assureCorrectFieldFormat(String rhs) {
        String result = rhs;
        if (null == result)
            result = "";
        if (m_Format.IsAlwaysQuote()
			|| this.doesStartOrEndWithWhiteSpace(result)
            || this.doesContainQuoteNeedingCharacters(result)) {
            result = m_Format.quoteString(result);
		}
        return result;
    }

    private void writeHeaders() throws IOException {
        int maximalindex = super.getTable().getHeader().size();
        for (int i = 0; i < maximalindex; ++i) {
            String name = super.getTable().getHeader().getAt(i).getName();
            name = this.assureCorrectFieldFormat(name);
            super.getStream().write(name);
            if (i < maximalindex - 1)
                this.writeFieldEnd();
        }
        this.writeRecordEnd();
    }

    private void writeRecord(Record record) throws IOException {
        int maximalindex = super.getTable().getHeader().size();
        for (int i = 0; i < maximalindex; ++i) {
            String value = record.getAt(i);
            value = this.assureCorrectFieldFormat(value);
            super.getStream().write(value);
            if (i < maximalindex - 1)
                this.writeFieldEnd();
        }
        this.writeRecordEnd();
    }

    public Format getFormat() {
        return m_Format;
    }

    public Serializer(Table table, int lineEnd) {
        super(table);
		this.m_Format = new Format(lineEnd);
    }

    protected void doSerialize() throws IOException {
        if (m_Format.doAssumeFirstRowAsHeaders())
            this.writeHeaders();
        for (int i = 0; i < super.getTable().size(); ++i)
            this.writeRecord(super.getTable().getAt(i));
    }

    protected RecordBasedParser createParser() {
        m_WaitingForHeader = m_Format.doAssumeFirstRowAsHeaders();
        return new Parser(m_Format);
    }

    protected boolean doStoreRecord(Record record) {
        if (m_WaitingForHeader) {
            m_WaitingForHeader = false;
            return false;
        } else
            return true;
    }

}
