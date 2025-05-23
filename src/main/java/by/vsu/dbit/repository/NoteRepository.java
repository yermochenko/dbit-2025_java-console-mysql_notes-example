package by.vsu.dbit.repository;

import by.vsu.dbit.domain.Note;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteRepository implements AutoCloseable {
	public static final String SELECT_ALL_SQL = "SELECT id, title, date, done FROM note";
	public static final String INSERT_SQL = "INSERT INTO note (title, date, done) VALUES (?, ?, FALSE)";

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
				note.setDate(new java.util.Date(resultSet.getDate("date").getTime()));
				note.setDone(resultSet.getBoolean("done"));
				notes.add(note);
			}
			return notes;
		} finally {
			try { if(resultSet != null) { resultSet.close(); } } catch (SQLException ignored) {}
			try { if(statement != null) { statement.close(); } } catch (SQLException ignored) {}
		}
	}

	public void create(Note note) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(INSERT_SQL);
			statement.setString(1, note.getTitle());
			statement.setDate(2, new java.sql.Date(note.getDate().getTime()));
			statement.executeUpdate();
		} finally {
			try { if(statement != null) { statement.close(); } } catch (SQLException ignored) {}
		}
	}

	@Override
	public void close() {
		try { connection.close(); } catch (SQLException ignored) {}
	}
}
