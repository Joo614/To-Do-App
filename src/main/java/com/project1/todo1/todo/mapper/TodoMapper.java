package com.project1.todo1.todo.mapper;

import com.project1.todo1.todo.dto.TodoDto;
import com.project1.todo1.todo.entity.TodoEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    TodoEntity todoPostToTodo(TodoDto.Post requestBody);

    TodoEntity todoPatchToTodo(TodoDto.Patch requestBody);

    TodoDto.Response todoToTodoResponse(TodoEntity todo);

    List<TodoDto.Response> todosToTodoResponses(List<TodoEntity> todos);
}
