package by.vsu.dbit.console;

import by.vsu.dbit.console.menu.AddMenuItem;
import by.vsu.dbit.console.menu.EditMenuItem;
import by.vsu.dbit.console.menu.ExitMenuItem;
import by.vsu.dbit.console.menu.ShowAllMenuItem;
import by.vsu.dbit.repository.MySqlConnector;
import by.vsu.dbit.repository.NoteRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		try(NoteRepository noteRepository = new NoteRepository(MySqlConnector.connect())) {
			Scanner scanner = new Scanner(System.in);
			List<MenuItem> menu = List.of(
				new ShowAllMenuItem(noteRepository),
				new AddMenuItem(noteRepository),
				new EditMenuItem(noteRepository),
				new ExitMenuItem(noteRepository)
			);
			boolean work = true;
			while(work) {
				System.out.println("MENU:");
				int n = 1;
				for(MenuItem menuItem : menu) {
					System.out.printf("%d) %s\n", n++, menuItem.getName());
				}
				System.out.println();
				System.out.print("Enter menu item number > ");
				try {
					MenuItem menuItem = menu.get(Integer.parseInt(scanner.nextLine()) - 1);
					System.out.println();
					work = menuItem.execute();
					System.out.println();
				} catch(NumberFormatException | IndexOutOfBoundsException e) {
					System.out.println("Invalid menu item number");
				}
			}
			System.out.println("Goodbye");
		} catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace(System.out);
		}
	}
}
