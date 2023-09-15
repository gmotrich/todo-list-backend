package com.todo.api.service;

import com.todo.api.model.Todo;
import com.todo.api.model.User;
import com.todo.api.repository.TodoRepository;
import com.todo.api.repository.UserRepository;
import com.todo.api.model.request.AddTodoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoService {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    public List<Todo> getTodos(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        return user.getTodoList();
    }

    public List<Todo> getSortedTodosByTitle(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        return user.getTodoList()
                .stream()
                .sorted(Comparator.comparing(Todo::getContent))
                .collect(Collectors.toList());
    }

    public List<Todo> getSortedTodosByDate(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        List<Todo> todos = user.getTodoList();
        todos.sort(Comparator.comparing(Todo::getFinish));
        return todos;
    }

    public void deleteTodo(Long userId, Long id){
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Todo todo = todoRepository.findById(id).orElseThrow(NoSuchElementException::new);
        user.getTodoList().remove(todo);
        todoRepository.delete(todo);
        log.info("Task - {} successfully deleted", todo.getId());
    }

    public void addTodo(Long userId, AddTodoRequest todoRequest) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Todo todo = new Todo();

        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d/MM/uuuu");
        String start = todoRequest.getStart().format(formatters);
        String finish = todoRequest.getFinish().format(formatters);

        todo.setContent(todoRequest.getContent());
        todo.setStart(start);
        todo.setFinish(finish);
        user.getTodoList().add(todo);

        todoRepository.save(todo);
        userRepository.save(user);
        log.info("Task - {} successfully added", todo.getId());
    }

    public void toggleTodoCompleted(Long id){
        Todo todo = todoRepository.findById(id).orElseThrow(NoSuchElementException::new);
        todo.setCompleted(!todo.getCompleted());
        todoRepository.save(todo);
    }
}
