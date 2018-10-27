package com.example.todoApi.resource;

import com.example.todoApi.model.Todo;
import com.example.todoApi.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/todos")
public class TodoResource {
    private final TodoService todoService;

    @Autowired
    public TodoResource(TodoService todoService) {
        this.todoService = todoService;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Todo insertNewTodo(@RequestBody Todo todo) {
        return todoService.createNewTodo(todo);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "{todoId}"
    )
    public Todo getTodo(@PathVariable("todoId") UUID todoId) {
        return todoService.getTodo(todoId);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "{todoId}"
    )
    public Todo updateTodo(@PathVariable("todoId") UUID todoId, @RequestBody Map<String, Object> payload) {
        return todoService.updateTodo(todoId, payload);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "{todoId}"
    )
    public void deleteTodo(@PathVariable("todoId") UUID todoId) {
        todoService.deleteTodo(todoId);
    }
}
