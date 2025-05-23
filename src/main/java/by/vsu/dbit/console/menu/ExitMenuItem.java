package by.vsu.dbit.console.menu;

import by.vsu.dbit.console.MenuItem;
import by.vsu.dbit.repository.NoteRepository;

public class ExitMenuItem extends MenuItem {
	public ExitMenuItem(NoteRepository noteRepository) {
		super("Exit", noteRepository);
	}

	@Override
	public boolean execute() {
		System.out.print("Are you sure you want to exit? (yes/no): ");
		return !"yes".equalsIgnoreCase(getConsole().nextLine());
	}
}
