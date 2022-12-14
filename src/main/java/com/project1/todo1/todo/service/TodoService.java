package com.project1.todo1.todo.service;

import com.project1.todo1.todo.entity.TodoEntity;
import com.project1.todo1.function.exception.BusinessLogicException;
import com.project1.todo1.function.exception.ExceptionCode;
import com.project1.todo1.todo.repository.TodoRepository;
import com.project1.todo1.function.utils.CustomBeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final CustomBeanUtils<TodoEntity> beanUtils;

    public TodoService(TodoRepository todoRepository, CustomBeanUtils<TodoEntity> beanUtils) {
        this.todoRepository = todoRepository;
        this.beanUtils = beanUtils;
    }

    // todo 등록
    public TodoEntity createTodo(TodoEntity todo) {
        return todoRepository.save(todo);
    }

    // todo 1개 조회
    public TodoEntity findTodo(long todoId) { // read
        return findVerifiedTodoById(todoId);
    }

    // todo 전체 조회
    public List<TodoEntity> findTodos() {
        return todoRepository.findAll();
    }

    // todo 수정 / 완료 표시
    public TodoEntity updateTodo(TodoEntity todo) {
        TodoEntity findTodo = findVerifiedTodoById(todo.getTodoId());

        TodoEntity updatingMember = beanUtils.copyNonNullProperties(todo, findTodo);

        return todoRepository.save(updatingMember);
    }

    // todo 1개 삭제
    public void deleteTodo(long todoId) {
        TodoEntity findTodo = findVerifiedTodoById(todoId);
        todoRepository.delete(findTodo);
    }

    // todo 전체 삭제
    public void deleteTodos() { // 전체 삭제
        todoRepository.deleteAll();
    }

    public TodoEntity findVerifiedTodoById(long todoId) {
        Optional<TodoEntity> optionalTodo = todoRepository.findById(todoId);

        return optionalTodo.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.TODO_NOT_FOUND));
    }
}