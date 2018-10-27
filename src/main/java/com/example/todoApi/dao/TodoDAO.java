package com.example.todoApi.dao;

import com.example.todoApi.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository("TodoTable")
public class TodoDAO implements EntityDAO<Todo> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TodoDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public class TodoRowMapper implements RowMapper<Todo> {
        @Override
        public Todo mapRow(ResultSet resultSet, int i) throws SQLException {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String description = resultSet.getString("description");
            boolean done = resultSet.getBoolean("done");
            Date created_at = resultSet.getDate("created_at");
            Date updated_at = resultSet.getDate("updated_at");

            return new Todo(id, description, done, created_at, updated_at);
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public final class ResourceNotFoundException extends RuntimeException {}


    @Override
    public int insertNew(Todo newRecord) {
        String sql = "INSERT INTO todos (id, description) values (?, ?)";
        return jdbcTemplate.update(sql, newRecord.getId(), newRecord.getDescription());
    }

    @Override
    public Todo select(UUID id) {
        try {
            String sql = "SELECT * FROM todos WHERE id = ?";
            RowMapper<Todo> todoRowMapper = new TodoRowMapper();
            return jdbcTemplate.queryForObject(sql, todoRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException();
        }
    }

    @Override
    public List<Todo> selectAll() {
        String sql = "SELECT * FROM todos";
        RowMapper<Todo> todoRowMapper = new TodoRowMapper();
        return jdbcTemplate.query(sql, todoRowMapper);
    }

    @Override
    public int update(Todo modifiedRecord) {
        UUID id = modifiedRecord.getId();
        String description = modifiedRecord.getDescription();
        boolean done = modifiedRecord.isDone();
        modifiedRecord.setUpdated_at(new Date());
        Date updated_at = modifiedRecord.getUpdated_at();

        String sql = "UPDATE todos SET description = ?, done = ?, updated_at = ? WHERE id = ?";
        return jdbcTemplate.update(sql, description, done, updated_at, id);
    }

    @Override
    public int delete(UUID id) {
        String sql = "DELETE FROM todos WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
