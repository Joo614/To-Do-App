package com.project1.todo1.todo.controller;

import com.project1.todo1.todo.dto.TodoDto;
import com.project1.todo1.todo.entity.TodoEntity;
import com.project1.todo1.todo.mapper.TodoMapper;
import com.project1.todo1.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@CrossOrigin(origins = "https://todobackend.com")
@RestController
@RequestMapping("/todos")
@Validated
public class TodoController {
    private final TodoService todoService;
    private final TodoMapper mapper;

    public TodoController(TodoService todoService, TodoMapper mapper) {
        this.todoService = todoService;
        this.mapper = mapper;
    }

    // todo 등록
    @PostMapping
    public ResponseEntity postTodo(@Valid @RequestBody TodoDto.Post requestBody) {
        TodoEntity todo = mapper.todoPostToTodo(requestBody);

        TodoDto.Response response = mapper.todoToTodoResponse(todoService.createTodo(todo));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // todo 1개 조회
    @GetMapping("/{todo-id}")
    public ResponseEntity getTodo(@PathVariable("todo-id") @Positive long todoId) {
        TodoEntity todo = todoService.findTodo(todoId);

        return new ResponseEntity<>(mapper.todoToTodoResponse(todo), HttpStatus.OK);
    }

    // todo 전체 조회
    @GetMapping
    public ResponseEntity getTodos() {
        List<TodoEntity> todos = todoService.findTodos();

        return new ResponseEntity<>(mapper.todosToTodoResponses(todos), HttpStatus.OK);
    }

    // todo 수정
    @PatchMapping("/{todo-id}")
    public ResponseEntity patchTodo(@PathVariable("todo-id") @Positive long todoId,
                                    @Valid @RequestBody TodoDto.Patch requestBody) {
        requestBody.setTodoId(todoId);

        TodoEntity todo = mapper.todoPatchToTodo(requestBody);

        TodoDto.Response response = mapper.todoToTodoResponse(todoService.updateTodo(todo));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // todo 1개 삭제
    @DeleteMapping("/{todo-id}")
    public ResponseEntity deleteTodo(@PathVariable("todo-id") @Positive long todoId) {
        todoService.deleteTodo(todoId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // todo 전체 삭제
    @DeleteMapping
    public ResponseEntity deleteTodos() {
        todoService.deleteTodos();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
