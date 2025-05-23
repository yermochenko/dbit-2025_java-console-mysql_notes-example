package by.vsu.dbit.console.menu;

import by.vsu.dbit.console.MenuItem;
import by.vsu.dbit.domain.Note;
import by.vsu.dbit.repository.NoteRepository;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class EditMenuItem extends MenuItem {
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

	public EditMenuItem(NoteRepository noteRepository) {
		super("Edit note", noteRepository);
	}

	@Override
	public boolean execute() throws SQLException {
		System.out.println("Enter information about the note:");
		// Title
		System.out.print("ID > ");
		int id;
		try {
			id = Integer.parseInt(getConsole().nextLine());
		} catch(NumberFormatException e) {
			System.out.println("Invalid ID");
			return true;
		}
		Optional<Note> noteOpt = getNoteRepository().read(id);
		if(noteOpt.isPresent()) {
			Note note = noteOpt.get();
			// Title
			System.out.printf("Current title > %s\n", note.getTitle());
			System.out.println("Enter new title or press Enter to leave unchanged");
			System.out.print("New title > ");
			String title = getConsole().nextLine();
			if(!title.isBlank()) {
				note.setTitle(title);
			}
			// Date
			System.out.printf("Current date > %s\n", DATE_FORMAT.format(note.getDate()));
			System.out.printf("Enter new date (in format %s) or press Enter to leave unchanged", DATE_FORMAT.format(note.getDate()));
			System.out.print("New date > ");
			String date = getConsole().nextLine();
			if(!date.isBlank()) {
				try {
					note.setDate(DATE_FORMAT.parse(date));
				} catch(ParseException e) {
					System.out.println("Date has incorrect format");
					return true;
				}
			}
			// Done
			System.out.printf("Current value of \"done\" field > %b\n", note.isDone());
			System.out.println("Enter new value of \"done\" field or press Enter to leave unchanged");
			System.out.print("New value of \"done\" field > ");
			String done = getConsole().nextLine();
			if(!done.isBlank()) {
				note.setDone(Boolean.parseBoolean(done));
			}
			getNoteRepository().update(note);
			System.out.println("Note updated");
		} else {
			System.out.println("Note not found");
		}
		return true;
	}
}
