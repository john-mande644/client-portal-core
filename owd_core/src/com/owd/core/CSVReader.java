package com.owd.core;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;


public class CSVReader extends DelimitedReader {
private final static Logger log =  LogManager.getLogger();


    public CSVReader(BufferedReader reader, boolean firstLine) throws IOException {

        super(',', reader, firstLine);

    }

}
