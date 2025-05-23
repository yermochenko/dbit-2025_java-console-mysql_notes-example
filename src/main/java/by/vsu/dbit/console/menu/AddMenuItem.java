package by.vsu.dbit.console.menu;

import by.vsu.dbit.console.MenuItem;
import by.vsu.dbit.domain.Note;
import by.vsu.dbit.repository.NoteRepository;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddMenuItem extends MenuItem {
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

	public AddMenuItem(NoteRepository noteRepository) {
		super("Add new note", noteRepository);
	}

	@Override
	public boolean execute() throws SQLException {
		Note note = new Note();
		System.out.println("Enter information about the note:");
		// Title
		System.out.print("Title > ");
		String title = getConsole().nextLine();
		if(title.isBlank()) {
			System.out.println("Title is required");
			return true;
		}
		note.setTitle(title);
		// Date
		System.out.printf("Date (in format %s) > ", DATE_FORMAT.toPattern());
		String date = getConsole().nextLine();
		if(!date.isBlank()) {
			try {
				note.setDate(DATE_FORMAT.parse(date));
			} catch(ParseException e) {
				System.out.println("Date has incorrect format");
				return true;
			}
		}
		getNoteRepository().create(note);
		return true;
	}
}
