package by.vsu.dbit.console;

import by.vsu.dbit.repository.NoteRepository;

import java.util.Scanner;

abstract public class MenuItem {
	private final Scanner console = new Scanner(System.in);
	private final String name;
	private final NoteRepository noteRepository;

	public MenuItem(String name, NoteRepository noteRepository) {
		this.name = name;
		this.noteRepository = noteRepository;
	}

	/**
	 * Perform an action for menu item
	 * @return <code>false</code> if after this action program should be stopped
	 */
	abstract public boolean execute();

	public String getName() {
		return name;
	}

	protected Scanner getConsole() {
		return console;
	}

	protected NoteRepository getNoteRepository() {
		return noteRepository;
	}
}
