package by.vsu.dbit.repository;

import by.vsu.dbit.domain.Note;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NoteRepository implements AutoCloseable {
	public static final String SELECT_ALL_SQL = "SELECT id, title, date, done FROM note";
	public static final String SELECT_ONE_SQL = "SELECT id, title, date, done FROM note WHERE id = ?";
	public static final String INSERT_SQL = "INSERT INTO note (title, date, done) VALUES (?, ?, FALSE)";
	public static final String UPDATE_SQL = "UPDATE note SET title = ?, date = ?, done = ? WHERE id = ?";

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

	public Optional<Note> read(Integer id) throws SQLException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(SELECT_ONE_SQL);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			if(resultSet.next()) {
				Note note = new Note();
				note.setId(resultSet.getInt("id"));
				note.setTitle(resultSet.getString("title"));
				note.setDate(new java.util.Date(resultSet.getDate("date").getTime()));
				note.setDone(resultSet.getBoolean("done"));
				return Optional.of(note);
			} else {
				return Optional.empty();
			}
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

	public void update(Note note) throws SQLException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(UPDATE_SQL);
			statement.setString(1, note.getTitle());
			statement.setDate(2, new java.sql.Date(note.getDate().getTime()));
			statement.setBoolean(3, note.isDone());
			statement.setInt(4, note.getId());
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
