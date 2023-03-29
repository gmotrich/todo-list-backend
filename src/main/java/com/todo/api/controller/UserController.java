package com.todo.api.controller;

import com.todo.api.model.Todo;
import com.todo.api.model.User;
import com.todo.api.repository.TodoRepository;
import com.todo.api.repository.UserRepository;
import com.todo.api.request.AddTodoRequest;
import com.todo.api.request.AddUserRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
@CrossOrigin("http://localhost:3000/")
public class UserController {
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    public UserController(UserRepository userRepository, TodoRepository todoRepository) {
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
    @GetMapping
    public List<User> getAllUser()  {
        return userRepository.findAll();
    }
    @PostMapping
    public User addUser(@RequestBody AddUserRequest userRequest){
        User user = new User();
        user.setFirstname(userRequest.getFirstname());
        user.setLastname(userRequest.getLastname());
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        User user = userRepository.findById(id).orElseThrow(NoSuchElementException::new);
        userRepository.delete(user);
    }

    @PostMapping("/{userId}/todos")
    public void addTodo(@PathVariable Long userId, @RequestBody AddTodoRequest todoRequest){
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Todo todo  = new Todo();
        todo.setContent(todoRequest.getContent());
        user.getTodoList().add(todo);
        todoRepository.save(todo);
        userRepository.save(user);
    }

    @PostMapping("/todos/{id}")
    public void toggleTodoCompleted(@PathVariable Long id){
        Todo todo = todoRepository.findById(id).orElseThrow(NoSuchElementException::new);
        todo.setCompleted(!todo.getCompleted());
        todoRepository.save(todo);
    }


    @DeleteMapping("/{userId}/todos/{id}")
    public void deleteTodo(@PathVariable Long userId, @PathVariable Long id){
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        Todo todo = todoRepository.findById(id).orElseThrow(NoSuchElementException::new);
        user.getTodoList().remove(todo);
        todoRepository.delete(todo);
    }

    @PutMapping("/{id}")
    public User updateUser (@RequestBody User newUser, @PathVariable Long id){
        return userRepository.findById(id)
                .map(user ->{
                    user.setFirstname(newUser.getFirstname());
                    user.setLastname(newUser.getLastname());
                    return userRepository.save(user);
                }).orElseThrow(NoSuchElementException::new);
    }

    @GetMapping("/{userId}/todos")
    public List<Todo> getTodo(@PathVariable Long userId){
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        return user.getTodoList();
    }
}
