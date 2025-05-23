package by.vsu.dbit.repository;

import by.vsu.dbit.domain.Note;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoteRepository implements AutoCloseable {
	public static final String SELECT_ALL_SQL = "SELECT id, title, date, done FROM note";

	private final Connection connection;

	public NoteRepository(Connection connection) {
		this.connection = connection;
	}

	public List<Note> readAll() throws SQLException {
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(SELECT_ALL_SQL);
			List<Note> notes = new ArrayList<>();
			while(resultSet.next()) {
				Note note = new Note();
				note.setId(resultSet.getInt("id"));
				note.setTitle(resultSet.getString("title"));
				note.setDate(new Date(resultSet.getDate("date").getTime()));
				note.setDone(resultSet.getBoolean("done"));
				notes.add(note);
			}
			return notes;
		} finally {
			try { if(resultSet != null) { resultSet.close(); } } catch (SQLException ignored) {}
			try { if(statement != null) { statement.close(); } } catch (SQLException ignored) {}
		}
	}

	@Override
	public void close() {
		try { connection.close(); } catch (SQLException ignored) {}
	}
}
