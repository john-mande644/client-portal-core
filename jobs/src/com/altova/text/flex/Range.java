////////////////////////////////////////////////////////////////////////
//
// Range.java
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

package com.altova.text.flex;

public class Range {
	public int start = 0;
	public int end = 0;
	private String content;

	public Range(String text, int contentStart, int contentEnd) {
		this.content = text;
		this.start = contentStart;
		this.end = contentEnd;
	}

	public Range(String text) {
		this.content = text;
		start = 0;
		end = text.length();
	}

	public Range(Range r) {
		content = r.getContent();
		start = r.start;
		end = r.end;
	}

	public boolean isValid() {
		return (start < end);
	}

	public String toString() {
		return content.substring(start, end);
	}

	public String getContent() {
		return content;
	}
	
	public int length() {
		return end - start;
	}

	public char charAt(int pos) {
		return content.charAt(pos);
	}

	public boolean startsWith(char ch) {
		return (isValid() && content.charAt(start) == ch);
	}

	public boolean endsWith(char ch) {
		return (isValid() && content.charAt(end - 1) == ch);
	}

	public boolean startsWith(String str) {
		return (length() >= str.length() && content.regionMatches(start, str, 0, str.length()));
	}
	
	public boolean contains(String str) {
		return (length() >= str.length() && toString().indexOf(str) != -1);
	}
	
	public void appendTo(StringBuffer str) {
		str.append(content.substring(start, end));
	}
}
