////////////////////////////////////////////////////////////////////////
//
// ErrorPosition.java
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

package com.altova.text.edi;

public class ErrorPosition {
	private long mLine;
	private long mColumn;
	private long mPosition;
	
	public ErrorPosition( Scanner scanner) {
		mLine = scanner.getLine() - 1;
		mColumn = scanner.getColumn();
		mPosition = scanner.getPosition();
	}
	
	public ErrorPosition( Scanner.State scannerState) {		
		mLine = scannerState.CurrentLine;
		mColumn = scannerState.Current - scannerState.LineStart;
		mPosition = scannerState.Current;
	}
	
	public ErrorPosition( final long line, final long column, final long position) {
		mLine = line;
		mColumn = column;
		mPosition = position;
	}
	
	public long getLine() {
		return mLine + 1;
	}
	public long getColumn() {
		return mColumn;
	}
	public long getPosition() {
		return mPosition;
	}
}
