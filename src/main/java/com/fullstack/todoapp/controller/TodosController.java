package com.fullstack.todoapp.controller;

import com.fullstack.todoapp.model.Todo;
import com.fullstack.todoapp.repository.TodoRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@RequestMapping("/todos")
@RestController
public class TodosController {

    private final TodoRepository todoRepository;

    @Autowired
    public TodosController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo addTodo(@RequestBody @NotNull Todo todo) {
        if (ObjectUtils.isEmpty(todo.getTitle()) || ObjectUtils.isEmpty(todo.getDescription())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return (Todo) todoRepository.save(todo);
    }

    @GetMapping("/{id}")
    public Optional<Todo> getTodo(@RequestBody Long id) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);

        if (optionalTodo.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return todoRepository.findById(id);
    }

    @PostMapping("/{id}")
    public Todo updateTodo(@RequestBody Long id, @RequestBody Boolean todoStatus) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if (!optionalTodo.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Todo todo = optionalTodo.get();
        todo.setStatus(todoStatus);

        return todoRepository.save(todo);
    }


    @DeleteMapping("/{id}")
    public void removeTodo(@RequestBody Long id) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if (optionalTodo.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        todoRepository.deleteById(id);
    }

    @GetMapping("/welcoming")
    public String getWelcoming() {
        return "Hello developer!";
    }

    @GetMapping
    public Iterable<Todo> getAllTodos() {
        return todoRepository.findAll();
    }
}
