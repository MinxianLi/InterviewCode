package com.mason.interview;

public class Operation {
	int opType;
	int index;
	String str;
	boolean undo;

	/**
	 * 
	 * @param opType:
	 *            Action type you have done in the string
	 * @param index:
	 *            Start index that you insert/append/delete in the string
	 * @param str:
	 *            text message
	 * @param undo
	 */
	public Operation(int opType, int index, String str, boolean undo) {
		this.opType = opType;
		this.index = index;
		this.str = str;
		this.undo = undo;
	}
}
