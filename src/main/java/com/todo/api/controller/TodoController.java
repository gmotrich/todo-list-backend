package com.todo.api.controller;

import com.todo.api.model.Todo;
import com.todo.api.model.request.AddTodoRequest;
import com.todo.api.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/todo")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/{userId}/todos")
    public List<Todo> getTodos(@PathVariable Long userId){
        return todoService.getTodos(userId);
    }

    @GetMapping("/{userId}/todos/sortedTitle")
    public List<Todo> getSortedTodosByTitle(@PathVariable Long userId){
        return todoService.getSortedTodosByTitle(userId);
    }

    @GetMapping("/{userId}/todos/sortedDate")
    public List<Todo> getSortedTodosByDate(@PathVariable Long userId){
        return todoService.getSortedTodosByDate(userId);
    }

    @DeleteMapping("/{userId}/todos/{id}")
    public void deleteTodo(@PathVariable Long userId, @PathVariable Long id){
        todoService.deleteTodo(userId, id);
    }

    @PostMapping("/{userId}/todos")
    public void addTodo(@PathVariable Long userId, @RequestBody AddTodoRequest todoRequest) throws ParseException {
        todoService.addTodo(userId, todoRequest);
    }

    @PostMapping("/todos/{id}")
    public void toggleTodoCompleted(@PathVariable Long id){
        todoService.toggleTodoCompleted(id);
    }

    // TODO: ДОБАВИТЬ ИЗМЕНЕНИЕ ДАТЫ ВЫПОЛНЕНИЯ
}
