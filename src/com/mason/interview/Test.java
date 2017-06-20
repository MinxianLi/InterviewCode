package com.mason.interview;

import java.util.Stack;

public class Test {
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Stack<Operation> stack = new Stack<Operation>();
		Stack<Operation> redoStack = new Stack<Operation>();
		StringBuilder stringBuilder = new StringBuilder();
		Editor editor = new Editor(stringBuilder, stack, redoStack);
		System.out.println("First append: " + editor.appendText("a"));
		System.out.println("Second append: " + editor.appendText("b"));
		System.out.println("After deleted a subString b: " + editor.deleteText(1, 2));
		editor.undo();
		System.out.println("After first undo: " + editor.getMessage());

		editor.redo();
		System.out.println("After first redo: " + editor.getMessage());
	}
}
