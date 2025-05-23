package by.vsu.dbit.console.menu;

import by.vsu.dbit.console.MenuItem;
import by.vsu.dbit.domain.Note;
import by.vsu.dbit.repository.NoteRepository;

import java.sql.SQLException;
import java.util.Optional;

public class DeleteMenuItem extends MenuItem {
	public DeleteMenuItem(NoteRepository noteRepository) {
		super("Delete note", noteRepository);
	}

	@Override
	public boolean execute() throws SQLException {
		System.out.print("Enter note ID > ");
		int id;
		try {
			id = Integer.parseInt(getConsole().nextLine());
		} catch(NumberFormatException e) {
			System.out.println("Invalid ID");
			return true;
		}
		Optional<Note> noteOpt = getNoteRepository().read(id);
		if(noteOpt.isPresent()) {
			getNoteRepository().delete(id);
			System.out.println("Note deleted");
		} else {
			System.out.println("Note not found");
		}
		return true;
	}
}
