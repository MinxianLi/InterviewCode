package com.mason.interview;

import java.util.Stack;

public class Editor {
	/**
	 * message that we want to manipulate
	 */
	private StringBuilder message;

	/**
	 * Keep track of action what we have done on the message
	 */
	private Stack<Operation> undoStack;

	/**
	 * Keep track of undo action what we have done on the mssage
	 */
	private Stack<Operation> redoStack;

	/**
	 * 
	 * @param message
	 * @param undoStack
	 * @param redoStack
	 */
	public Editor(StringBuilder message, Stack<Operation> undoStack, Stack<Operation> redoStack) {
		this.message = message;
		this.undoStack = undoStack;
		this.redoStack = redoStack;
	}

	/**
	 * 
	 * @param text:
	 *            text that we want to insert to message
	 * @param index:
	 *            start index of message that we want to insert a text
	 * @return: updated message after inserting action
	 */
	public StringBuilder insertText(String text, int index) {
		Operation operation = new Operation(0, index, text, false);

		if (!operation.undo) {
			this.undoStack.push(operation);
		}

		return this.message.insert(index, text);
	}

	/**
	 * 
	 * @param text:
	 *            text that we want to append to message
	 * @return: updated message after appending action
	 */
	public StringBuilder appendText(String text) {
		Operation operation = new Operation(1, this.message.length(), text, false);

		if (!operation.undo) {
			this.undoStack.push(operation);
		}

		return this.message.append(text);
	}

	/**
	 * 
	 * @param startIndex:
	 *            start index of a substring that we want to delete in the
	 *            message
	 * @param endIndex:
	 *            end index of a substring that we want to delete in the message
	 * @return: updated message after deleting action
	 */
	public StringBuilder deleteText(int startIndex, int endIndex) {
		Operation operation = new Operation(2, startIndex, this.message.substring(startIndex, endIndex), false);
		StringBuilder updatedMessage = this.message.delete(startIndex, endIndex);

		if (!operation.undo) {
			this.undoStack.push(operation);
		}

		return updatedMessage;
	}

	/**
	 * Undo a action on message
	 */
	public void undo() {
		if (this.undoStack.size() < 1) {
			return;
		}

		Operation action = this.undoStack.pop();

		switch (action.opType) {
		case 0:
			this.message = this.deleteText(action.index, action.str.length() + action.index);
			this.undoStack.pop();
			Operation insertOperation = new Operation(0, action.index, action.str, false);
			if (!insertOperation.undo) {
				this.redoStack.push(insertOperation);
			}
			break;

		case 1:
			this.message = this.deleteText(action.index, action.str.length() + action.index);
			this.undoStack.pop();
			Operation appendOperation = new Operation(1, action.index, action.str, false);
			if (!appendOperation.undo) {
				this.redoStack.push(appendOperation);
			}
			break;

		case 2:
			this.message = this.appendText(action.str);
			this.undoStack.pop();
			Operation deleteOperation = new Operation(2, action.index, action.str, false);
			if (!deleteOperation.undo) {
				this.redoStack.push(deleteOperation);
			}
			break;

		default:
			break;
		}
	}

	/**
	 * Redo a action on message
	 */
	public void redo() {
		if (this.redoStack.size() < 1) {
			return;
		}

		Operation action = this.redoStack.pop();

		switch (action.opType) {
		case 0:
			this.message = this.message.insert(action.index, action.str);
			break;

		case 1:
			this.message = this.message.append(action.str);
			break;

		case 2:
			this.message = this.message.delete(action.index, action.str.length() + action.index);
			break;

		default:
			break;
		}
	}

	/**
	 * 
	 * @return: return current message
	 */
	public StringBuilder getMessage() {
		return this.message;
	}
}
