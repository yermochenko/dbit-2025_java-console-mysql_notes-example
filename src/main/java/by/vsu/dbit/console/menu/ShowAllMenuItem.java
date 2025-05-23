package by.vsu.dbit.console.menu;

import by.vsu.dbit.console.MenuItem;
import by.vsu.dbit.domain.Note;
import by.vsu.dbit.repository.NoteRepository;

import java.sql.SQLException;
import java.util.List;

public class ShowAllMenuItem extends MenuItem {
	public ShowAllMenuItem(NoteRepository noteRepository) {
		super("Show all notes", noteRepository);
	}

	@Override
	public boolean execute() throws SQLException {
		List<Note> notes = getNoteRepository().readAll();
		if(!notes.isEmpty()) {
			System.out.println("*** LIST OF NOTES: ***");
			for(Note note : notes) {
				System.out.printf("[%1$c] %2$02d: %3$td.%3$tm.%3$tY, %4$s\n", note.isDone() ? 'v' : ' ', note.getId(), note.getDate(), note.getTitle());
			}
		} else {
			System.out.println("There are no any notes");
		}
		return true;
	}
}
