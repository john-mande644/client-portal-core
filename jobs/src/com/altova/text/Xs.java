// Xs.java
// This file contains generated code and will be overwritten when you rerun code generation.

package com.altova.text;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.altova.typeinfo.ValueFormatter;

public class Xs
{
private final static Logger log =  LogManager.getLogger();
	public static ValueFormatter StandardFormatter = new TextFormatter();
	public static ValueFormatter TimeFormatter = new TextTimeFormatter();
	public static ValueFormatter DateFormatter = new TextDateFormatter();
	public static ValueFormatter DateTimeFormatter = StandardFormatter;
	public static ValueFormatter HexBinaryFormatter = StandardFormatter;
	public static ValueFormatter IntegerFormatter = new TextIntegerFormatter();
	public static ValueFormatter DecimalFormatter = StandardFormatter;
	public static ValueFormatter AnySimpleTypeFormatter = StandardFormatter;
	public static ValueFormatter DurationFormatter = StandardFormatter;
	public static ValueFormatter DoubleFormatter = StandardFormatter;
}
