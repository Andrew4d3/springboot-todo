package com.example.todoApi.service;

import com.example.todoApi.dao.EntityDAO;
import com.example.todoApi.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class TodoService {
    private final EntityDAO<Todo> todoDao;

    @Autowired
    public TodoService(@Qualifier("TodoTable") EntityDAO<Todo> todoDao) {
        this.todoDao = todoDao;
    }

    public Todo createNewTodo(Todo todo) {
        int isInserted = todoDao.insertNew(todo);
        return isInserted == 1 ? todoDao.select(todo.getId()) : null;
    }

    public Todo getTodo(UUID id) {
        return todoDao.select(id);
    }

    public List<Todo> getAllTodos() {
        return todoDao.selectAll();
    }

    public Todo updateTodo(UUID id, Map<String, Object> todoData) {
        Todo todoToUpdate = todoDao.select(id);

        if (todoData.containsKey("description")) {
            todoToUpdate.setDescription((String) todoData.get("description"));
        }

        if (todoData.containsKey("done")) {
            todoToUpdate.setDone((boolean) todoData.get("done"));
        }

        return todoDao.update(todoToUpdate) == 1 ? todoToUpdate : null;
    }

    public boolean deleteTodo(UUID id) {
        return todoDao.delete(id) == 1 ? true : false;
    }
}
